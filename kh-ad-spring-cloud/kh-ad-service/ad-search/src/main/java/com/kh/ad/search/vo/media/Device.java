package com.kh.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author han.ke
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device {

    /**
     * 设备 id
     */
    private String deviceCode;

    /**
     * mac
     */
    private String mac;

    /**
     * ip
     */
    private String ip;

    /**
     * 机器编码
     */
    private String model;

    /**
     * 分辨率尺寸
     */
    private String displaySize;

    /**
     * 屏幕尺寸
     */
    private String screenSize;

    /**
     * 设备序列号
     */
    private String serialName;
}
