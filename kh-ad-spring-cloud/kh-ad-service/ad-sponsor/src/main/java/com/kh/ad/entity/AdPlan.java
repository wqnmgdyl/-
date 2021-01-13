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
@TableName(value = "ad_plan")
public class AdPlan {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String planName;

    private Integer planStatus;

    private Date startDate;

    private Date endDate;

    private Date createTime;

    private Date updateTime;

    public AdPlan(Long userId, String planName, Date startDate, Date endDate) {
        this.userId = userId;
        this.planName = planName;
        this.planStatus = CommonStatus.VALTD.getStatus();
        this.startDate = startDate;
        this.endDate = endDate;
        this.createTime = new Date();
        this.updateTime = createTime;
    }
}
