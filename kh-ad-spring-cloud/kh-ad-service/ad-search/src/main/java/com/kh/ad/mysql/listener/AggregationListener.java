package com.kh.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.kh.ad.mysql.TemplateHolder;
import com.kh.ad.mysql.dto.BinlogRowData;
import com.kh.ad.mysql.dto.TableTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author han.ke
 */
@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    private String dbName;
    private String tableName;

    private Map<String, IListener> listenerMap = new HashMap<>();

    private final TemplateHolder templateHolder;

    public AggregationListener(TemplateHolder templateHolder) {
        this.templateHolder = templateHolder;
    }

    private String getKey(String dbName, String tableName) {
        return dbName + ":" + tableName;
    }

    public void register(String _dbName, String _tableName, IListener iListener) {
        log.info("register : {}-{}",_dbName,_tableName);
        this.listenerMap.put(getKey(_dbName,_tableName),iListener);
    }

    @Override
    public void onEvent(Event event) {
        EventType type = event.getHeader().getEventType();
        log.debug("event type: {}",type);
        if(type == EventType.TABLE_MAP) {
            TableMapEventData data = event.getData();
            this.tableName = data.getTable();
            this.dbName = data.getDatabase();
            return;
        }

        if(type != EventType.EXT_UPDATE_ROWS
                && type != EventType.EXT_WRITE_ROWS
                && type != EventType.EXT_DELETE_ROWS) {
            return;
        }

        //表明和库名是否已经完成填充
        if(StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tableName)) {
            log.error("no meta data event");
            return;
        }

        //找出对应表有兴趣的监听器
        String key = getKey(this.dbName,this.tableName);
        IListener iListener = this.listenerMap.get(key);
        if(null == iListener) {
            log.debug("skip {}", key);
            return;
        }

        log.info("trigger event: {}",type.name());

        try {
            BinlogRowData rowData = buildRowData(event.getData());
            if(rowData == null) {
                return;
            }
            rowData.setEventType(type);
            iListener.onEvent(rowData);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            this.dbName = "";
            this.tableName = "";
        }
    }

    private List<Serializable[]> getAfterValues(EventData eventData) {
        if(eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) eventData).getRows();
        }
        if(eventData instanceof  UpdateRowsEventData) {
            return ((UpdateRowsEventData) eventData).getRows().stream().
                    map(Map.Entry :: getValue).collect(Collectors.toList());
        }
        if(eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) eventData).getRows();
        }
        return Collections.emptyList();
    }

    private BinlogRowData buildRowData(EventData eventData) {
        TableTemplate table = templateHolder.getTable(tableName);

        if(null == table) {
            log.warn("table {} not found", tableName);
        }

        List<Map<String,String>> afterMapList = new ArrayList<>();

        for (Serializable[] after : getAfterValues(eventData)) {
            Map<String, String> afterMap = new HashMap<>();
            int colLen = after.length;
            for(int ix = 0; ix < colLen; ++ix) {
                //去除当前位置对应的列名
                String colName = table.getPosMap().get(ix);
                //如果没有则说明不关心这个列
                if(null == colName) {
                    log.debug("ignore position: []", ix);
                    continue;
                }
                String colValue = after[ix].toString();
                afterMap.put(colName, colValue);
            }
            afterMapList.add(afterMap);
        }
        BinlogRowData rowData = new BinlogRowData();
        rowData.setAfter(afterMapList);
        rowData.setTable(table);

        return rowData;
    }
}
