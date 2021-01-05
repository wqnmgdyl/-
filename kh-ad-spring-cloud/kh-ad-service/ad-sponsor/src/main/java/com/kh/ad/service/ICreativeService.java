package com.kh.ad.service;

import com.kh.ad.exception.AdException;
import com.kh.ad.vo.CreativeRequest;
import com.kh.ad.vo.CreativeResponse;

/**
 * @author han.ke
 */
public interface ICreativeService {

    /**
     * 创建创意
     *
     * @param request
     * @return
     * @throws AdException
     */
    CreativeResponse createCreative(CreativeRequest request) throws AdException;
}
