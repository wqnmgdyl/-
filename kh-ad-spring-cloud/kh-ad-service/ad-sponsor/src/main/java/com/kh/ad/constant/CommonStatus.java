package com.kh.ad.constant;

import lombok.Getter;

/**
 * @author han.ke
 */
@Getter
public enum CommonStatus {
    VALTD(1, "有效状态"),
    INVALTD(0, "无效状态");

    private Integer status;
    private String desc;

    CommonStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
