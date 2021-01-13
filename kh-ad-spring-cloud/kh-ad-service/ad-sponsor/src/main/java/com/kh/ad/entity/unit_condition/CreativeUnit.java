package com.kh.ad.entity.unit_condition;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author han.ke
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "creative_unit")
public class CreativeUnit {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long creativeId;

    private Long unitId;

    public CreativeUnit(Long creativeId, Long unitId) {
        this.creativeId = creativeId;
        this.unitId = unitId;
    }
}
