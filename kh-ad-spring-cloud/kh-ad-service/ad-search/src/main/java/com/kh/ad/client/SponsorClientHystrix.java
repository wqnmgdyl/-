package com.kh.ad.client;

import com.kh.ad.client.vo.AdPlan;
import com.kh.ad.client.vo.AdPlanGetRequest;
import com.kh.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author han.ke
 */
@Component
public class SponsorClientHystrix implements SponsorClient {
    @Override
    public CommonResponse<List<AdPlan>> getAdPlans(AdPlanGetRequest request) {
        return new CommonResponse<>(-1, "eureka-client-ad-sponsor error");
    }
}
