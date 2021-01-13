package com.kh.ad.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName(value = "ad_creative")
public class Creative {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private Integer type;

    /**
     * 物料的类型，比如图片可以是bmp，jpg等等
     */
    private Integer materialType;

    private Integer height;

    private Integer width;

    /**
     * 物料大小
     */
    private Long size;

    /**
     * 持续时长,只有视频不为0
     */
    private Integer duration;

    /**
     * 审核状态
     */
    private Integer auditStatus;

    private Long userId;

    private String url;

    private Date createTime;

    private Date updateTime;
}
