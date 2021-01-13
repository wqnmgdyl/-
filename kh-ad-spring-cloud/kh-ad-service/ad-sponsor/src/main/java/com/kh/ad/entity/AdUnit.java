package com.kh.ad.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.kh.ad.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author han.ke
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "ad_unit")
public class AdUnit {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "plan_id")
    private Long planId;

    @TableField(value = "unit_name")
    private String unitName;

    @TableField(value = "unit_status")
    private Integer unitStatus;

    /**
     * 广告位类型（开屏，贴片，中帖...)
     */
    private Integer positionType;

    private Long budget;

    private Date createTime;

    private Date updateTime;

    public AdUnit(Long planId, String unitName, Integer positionType, Long budget) {
        this.planId = planId;
        this.unitName = unitName;
        this.unitStatus = CommonStatus.VALTD.getStatus();
        this.positionType = positionType;
        this.budget = budget;
        this.createTime = new Date();
        this.updateTime = createTime;
    }
}
