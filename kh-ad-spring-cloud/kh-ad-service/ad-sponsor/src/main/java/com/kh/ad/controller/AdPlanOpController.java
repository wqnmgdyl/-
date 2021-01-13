package com.kh.ad.controller;

import com.alibaba.fastjson.JSON;
import com.kh.ad.entity.AdPlan;
import com.kh.ad.exception.AdException;
import com.kh.ad.service.IAdPlanService;
import com.kh.ad.vo.AdPlanGetRequest;
import com.kh.ad.vo.AdPlanRequest;
import com.kh.ad.vo.AdPlanResponse;
import com.kh.ad.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author han.ke
 */
@Slf4j
@RestController
public class AdPlanOpController {
    private final IAdPlanService adPlanService;

    public AdPlanOpController(IAdPlanService adPlanService) {
        this.adPlanService = adPlanService;
    }

    @PostMapping("/create/adPlan")
    public CommonResponse<AdPlanResponse> createAdPlan(
            @RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: createAdPlan -> {}",
                JSON.toJSONString(request));
        AdPlanResponse adPlan = adPlanService.createAdPlan(request);
        return new CommonResponse<>(0, "create AdPlan success", adPlan);
    }

    @PostMapping("/get/adPlan")
    public CommonResponse<List<AdPlan>> getAdPlanByIds(
            @RequestBody AdPlanGetRequest request) throws AdException {
        log.info("ad-sponsor: getAdPlanByIds -> {}",
                JSON.toJSONString(request));
        List<AdPlan> adPlans = adPlanService.getAdPlanByIds(request);
        return new CommonResponse<>(0, "success", adPlans);
    }

    @PutMapping("/update/adPlan")
    public CommonResponse<AdPlanResponse> updateAdPlan(
            @RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: updateAdPlan -> {}",
                JSON.toJSONString(request));
        return new CommonResponse<>(0, "update AdPlan success", adPlanService.updateAdPlan(request));
    }

    @DeleteMapping("delete/adPlan")
    public CommonResponse<AdPlanResponse> deleteAdPlan(
            @RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: deleteAdPlan -> {}",
                JSON.toJSONString(request));
        adPlanService.deleteAdPlan(request);
        return new CommonResponse<>(0, "delete AdPlan success", null);
    }
}
