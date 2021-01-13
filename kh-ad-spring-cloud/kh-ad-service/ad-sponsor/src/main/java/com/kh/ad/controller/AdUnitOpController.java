package com.kh.ad.controller;

import com.alibaba.fastjson.JSON;
import com.kh.ad.exception.AdException;
import com.kh.ad.service.IAdUnitService;
import com.kh.ad.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author han.ke
 */
@Slf4j
@RestController
public class AdUnitOpController {
    private final IAdUnitService adUnitService;

    public AdUnitOpController(IAdUnitService adUnitService) {
        this.adUnitService = adUnitService;
    }

    @PostMapping("/create/adUnit")
    public CommonResponse<AdUnitResponse> createUnit(
            @RequestBody AdUnitRequest request) throws AdException {
        log.info("ad-sponsor: createUnit -> {}",
                JSON.toJSONString(request));
        return new CommonResponse<>(0, "create Unit success", adUnitService.createUnit(request));
    }

    @PostMapping("/create/unitKeyword")
    public CommonResponse<AdUnitKeywordResponse> createUnitKeyword(
            @RequestBody AdUnitKeywordRequest request) throws AdException {
        log.info("ad-sponsor: createUnitKeyword -> {}",
                JSON.toJSONString(request));
        return new CommonResponse<>(0, "create UnitKeyword success", adUnitService.createUnitKeyword(request));
    }

    @PostMapping("/create/unitIt")
    public CommonResponse<AdUnitItResponse> createUnitIt(
            @RequestBody AdUnitItRequest request) throws AdException {
        log.info("ad-sponsor: createUnitIt -> {}",
                JSON.toJSONString(request));
        return new CommonResponse<>(0, "create UnitIt success", adUnitService.createUnitIt(request));
    }

    @PostMapping("/create/unitDistrict")
    public CommonResponse<AdUnitDistrictResponse> createUnitDistrict(
            @RequestBody AdUnitDistrictRequest request) throws AdException {
        log.info("ad-sponsor: createUnitDistrict -> {}",
                JSON.toJSONString(request));
        return new CommonResponse<>(0, "create UnitDistrict success", adUnitService.createUnitDistrict(request));
    }

    @PostMapping("/create/creativeUnit")
    public CommonResponse<CreativeUnitResponse> createCreativeUnit(
            @RequestBody CreativeUnitRequest request) throws AdException {
        log.info("ad-sponsor: createCreativeUnit -> {}",
                JSON.toJSONString(request));
        return new CommonResponse<>(0, "create CreativeUnit success", adUnitService.createCreativeUnit(request));
    }
}
