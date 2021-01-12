package com.kh.ad.service;

import com.kh.ad.Application;
import com.kh.ad.exception.AdException;
import com.kh.ad.vo.AdPlanGetRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

/**
 * @author han.ke
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AdPlanServiceTest {
    @Autowired
    private IAdPlanService planService;

    @Test
    public void testGetAdPlan() throws AdException {
        System.out.println(planService.getAdPlanByIds(
                new AdPlanGetRequest(15L, Collections.singletonList(10L))
        ));
    }
}
