package com.kh.ad.sender;

import com.kh.ad.mysql.dto.MySqlRowData;

/**
 * @author han.ke
 */
public interface ISender {
    /**
     * 投递数据
     *
     * @param rowData
     */
    void sender(MySqlRowData rowData);
}
