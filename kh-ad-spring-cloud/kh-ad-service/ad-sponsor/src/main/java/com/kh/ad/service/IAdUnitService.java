package com.kh.ad.service;

import com.kh.ad.exception.AdException;
import com.kh.ad.vo.AdUnitRequest;
import com.kh.ad.vo.AdUnitResponse;

/**
 * @author han.ke
 */
public interface IAdUnitService {
    /**
     * 创建推广单元
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitResponse createUnit(AdUnitRequest request) throws AdException;
}
