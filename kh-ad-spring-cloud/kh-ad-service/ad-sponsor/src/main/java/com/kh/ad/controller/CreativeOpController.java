package com.kh.ad.controller;

import com.alibaba.fastjson.JSON;
import com.kh.ad.exception.AdException;
import com.kh.ad.service.ICreativeService;
import com.kh.ad.vo.CreativeRequest;
import com.kh.ad.vo.CreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author han.ke
 */
@Slf4j
@RestController
public class CreativeOpController {
    private final ICreativeService creativeService;

    public CreativeOpController(ICreativeService creativeService) {
        this.creativeService = creativeService;
    }

    public CreativeResponse createCreative(
            @RequestBody CreativeRequest request) throws AdException {
        log.info("ad-sponsor: createCreative -> {}",
                JSON.toJSONString(request));
        return creativeService.createCreative(request);
    }
}
