package com.kh.ad.service;

import com.kh.ad.exception.AdException;
import com.kh.ad.vo.CreateUserRequest;
import com.kh.ad.vo.CreateUserResponse;

/**
 * @author han.ke
 */
public interface IUserService {

    /**
     * 创建用户
     * @param request
     * @return
     * @throws AdException
     */
    CreateUserResponse createUser(CreateUserRequest request) throws AdException;
}
