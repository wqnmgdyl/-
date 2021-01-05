package com.kh.ad.service;

import com.kh.ad.entity.AdUnit;
import com.kh.ad.exception.AdException;
import com.kh.ad.vo.*;

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

    /**
     * 创建推广单元关键字
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException;

    /**
     * 创建推广单元兴趣标签
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException;

    /**
     * 创建推广单元地域限制
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException;
}
