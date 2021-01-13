package com.kh.ad.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName(value = "ad_user")
public class AdUser {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String token;

    private Integer userStatus;

    private Date createTime;

    private Date updateTime;

    public AdUser(String username, String token) {
        this.username = username;
        this.token = token;
        this.userStatus = CommonStatus.VALTD.getStatus();
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }
}
