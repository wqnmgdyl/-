package com.kh.ad.service.impl;

import com.kh.ad.constant.Constants;
import com.kh.ad.dao.AdUserDao;
import com.kh.ad.entity.AdUser;
import com.kh.ad.exception.AdException;
import com.kh.ad.service.IUserService;
import com.kh.ad.utils.CommonUtils;
import com.kh.ad.vo.CreateUserRequest;
import com.kh.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author han.ke
 */
@Slf4j
@Service
public class IUserServiceImpl implements IUserService {

    private final AdUserDao adUserDao;

    public IUserServiceImpl(AdUserDao adUserDao) {
        this.adUserDao = adUserDao;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {
        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdUser oldUser = adUserDao.findByUsername(request.getUsername());
        if (oldUser != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }

        AdUser newUser = adUserDao.save(new AdUser(request.getUsername(),
                CommonUtils.md5(request.getUsername())));

        return new CreateUserResponse(newUser.getId(), newUser.getUsername(), newUser.getToken(),
                newUser.getCreateTime(), newUser.getUpdateTime());
    }
}
