package com.kh.ad.handler;

import com.kh.ad.dump.table.AdCreativeTable;
import com.kh.ad.dump.table.AdPlanTable;
import com.kh.ad.index.DataTable;
import com.kh.ad.index.IndexAware;
import com.kh.ad.index.adplan.AdPlanIndex;
import com.kh.ad.index.adplan.AdPlanObject;
import com.kh.ad.index.creative.CreativeIndex;
import com.kh.ad.index.creative.CreativeObject;
import com.kh.ad.mysql.constant.OpType;
import lombok.extern.slf4j.Slf4j;

/**
 * 1.索引之间存在着层级的划分，也就是以来关系的划分
 * 2.加载全量索引其实是增量索引“添加”的一种特殊实现
 * @author han.ke
 */
@Slf4j
public class AdLevelDataHandler {

    public static void handleLevel2(AdPlanTable planTable, OpType type) {
        AdPlanObject planObject = new AdPlanObject(planTable.getId(),
                planTable.getUserId(),
                planTable.getPlanStatus(),
                planTable.getStartDate(),
                planTable.getEndDate());
        handleBinLogEvent(DataTable.of(AdPlanIndex.class),
                planObject.getPlanId(),
                planObject,
                type);
    }

    public static void handleLevel2(AdCreativeTable creativeTable,OpType type) {
        CreativeObject creativeObject = new CreativeObject(creativeTable.getAdId(),
                creativeTable.getName(),
                creativeTable.getType(),
                creativeTable.getMaterialType(),
                creativeTable.getHeight(),
                creativeTable.getWidth(),
                creativeTable.getAuditStatus(),
                creativeTable.getAdUrl());
        handleBinLogEvent(DataTable.of(CreativeIndex.class),
                creativeObject.getAdId(),
                creativeObject,
                type);
    }

    private static <K,V> void handleBinLogEvent(IndexAware<K,V> index,
                                                K key,
                                                V value,
                                                OpType type) {
        switch (type) {
            case ADD:
                index.add(key,value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;
            default:
                break;
        }
    }
}
