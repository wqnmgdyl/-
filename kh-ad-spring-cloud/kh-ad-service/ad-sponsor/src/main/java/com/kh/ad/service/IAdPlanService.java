package com.kh.ad.service;

import com.kh.ad.entity.AdPlan;
import com.kh.ad.exception.AdException;
import com.kh.ad.vo.AdPlanGetRequest;
import com.kh.ad.vo.AdPlanRequest;
import com.kh.ad.vo.AdPlanResponse;

import java.util.List;

/**
 * @author han.ke
 */
public interface IAdPlanService {

    /**
     * 创建推广计划
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 获取推广计划
     *
     * @param request
     * @return
     * @throws AdException
     */
    List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException;

    /**
     * 更新推广计划
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 删除推广计划
     *
     * @param request
     * @throws AdException
     */
    void deleteAdPlan(AdPlanRequest request) throws AdException;
}
