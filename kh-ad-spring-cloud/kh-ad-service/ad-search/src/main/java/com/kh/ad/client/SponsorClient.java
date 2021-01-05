package com.kh.ad.client;

import com.kh.ad.client.vo.AdPlan;
import com.kh.ad.client.vo.AdPlanGetRequest;
import com.kh.ad.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author han.ke
 */
@FeignClient(value = "eureka-client-ad-sponsor", fallback = SponsorClientHystrix.class)
public interface SponsorClient {

    @RequestMapping(value = "/ad-sponsor/get/adPlam", method = RequestMethod.POST)
    CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request);
}
