package com.kh.ad.mysql.listener;

import com.kh.ad.mysql.dto.BinlogRowData;

/**
 * @author han.ke
 */
public interface IListener {

    /**
     * 注册监听器
     */
    void register();

    /**
     * 实现增量索引的更新
     * @param eventData
     */
    void onEvent(BinlogRowData eventData);
}
