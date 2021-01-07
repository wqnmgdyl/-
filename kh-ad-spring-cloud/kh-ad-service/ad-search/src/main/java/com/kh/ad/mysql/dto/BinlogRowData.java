package com.kh.ad.mysql.dto;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 解析Binlog，完成event到BinlogRowData的映射
 *
 * @author han.ke
 */
@Data
public class BinlogRowData {
    private TableTemplate table;
    private EventType eventType;

    /**
     * key操作的列的名字,value操作的列的值
     */
    private List<Map<String,String>> after;
    private List<Map<String,String>> before;
}
