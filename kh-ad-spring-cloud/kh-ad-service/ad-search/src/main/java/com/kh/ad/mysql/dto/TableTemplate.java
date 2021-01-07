package com.kh.ad.mysql.dto;

import com.kh.ad.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author han.ke
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableTemplate {
    private String tableName;
    private String level;

    /**
     * List<String>对应到多个列
     */
    private Map<OpType, List<String>> opTypeFieldSetMap = new HashMap<>();
    /**
     * 字段索引 -> 字段名
     */
    private Map<Integer, String> posMap = new HashMap<>();
}
