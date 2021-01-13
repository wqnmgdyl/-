package com.kh.ad.controller;

import com.alibaba.fastjson.JSON;
import com.kh.ad.exception.AdException;
import com.kh.ad.service.IUserService;
import com.kh.ad.vo.CommonResponse;
import com.kh.ad.vo.CreateUserRequest;
import com.kh.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author han.ke
 */
@Slf4j
@RestController
public class UserOpController {
    private final IUserService userService;

    public UserOpController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create/user")
    public CommonResponse<CreateUserResponse> createUser(
            @RequestBody CreateUserRequest request) throws AdException {
        log.info("ad-sponsor: createUser -> {]",
                JSON.toJSONString(request));
        return new CommonResponse<>(0, "create user success", userService.createUser(request));
    }
}
