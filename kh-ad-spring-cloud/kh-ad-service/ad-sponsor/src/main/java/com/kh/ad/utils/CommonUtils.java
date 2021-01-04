package com.kh.ad.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author han.ke
 */
public class CommonUtils {
    public static String md5(String value) {
        return DigestUtils.md5Hex(value).toUpperCase();
    }
}
