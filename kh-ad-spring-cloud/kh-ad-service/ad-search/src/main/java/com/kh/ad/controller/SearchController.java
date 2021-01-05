package com.kh.ad.controller;

import com.alibaba.fastjson.JSON;
import com.kh.ad.annotation.IgnoreResponseAdvice;
import com.kh.ad.client.SponsorClient;
import com.kh.ad.client.vo.AdPlan;
import com.kh.ad.client.vo.AdPlanGetRequest;
import com.kh.ad.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author han.ke
 */
@Slf4j
@RestController
public class SearchController {

    private final RestTemplate restTemplate;
    private final SponsorClient sponsorClient;

    public SearchController(RestTemplate restTemplate, SponsorClient sponsorClient) {
        this.restTemplate = restTemplate;
        this.sponsorClient = sponsorClient;
    }

    @IgnoreResponseAdvice
    @PostMapping("/getAdPlans")
    public CommonResponse<List<AdPlan>> getAdPlans(
            @RequestBody AdPlanGetRequest request) {
        log.info("ad-search: getAdPlans -> {}");
        return sponsorClient.getAdPlans(request);
    }

    @SuppressWarnings("all")
    @IgnoreResponseAdvice
    @PostMapping("/getAdPlansByRibbon")
    public CommonResponse<List<AdPlan>> getAdPlansByRibbon(
            @RequestBody AdPlanGetRequest request) {
        log.info("ad-search: getAdPlanByRibbon -> {}",
                JSON.toJSONString(request));
        return restTemplate.postForEntity("http://eureka-client-ad-sponsor/ad-sponsor/get/adPlan",
                request, CommonResponse.class).getBody();
    }
}
