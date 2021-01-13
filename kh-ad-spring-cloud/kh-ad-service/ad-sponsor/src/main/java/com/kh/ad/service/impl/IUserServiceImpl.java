package com.kh.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        Map<String, Object> map = new HashMap<>();
        map.put("username", request.getUsername());
        List<AdUser> oldUser = adUserDao.selectByMap(map);
        if (oldUser.size() >= 1) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }

        int insert = adUserDao.insert(new AdUser(request.getUsername(),
                CommonUtils.md5(request.getUsername())));

        if (insert <= 0) {
            throw new AdException(Constants.ErrorMsg.INSERT_ERROR);
        }

        AdUser newUser = adUserDao.selectByMap(map).get(0);

        return new CreateUserResponse(newUser.getId(), newUser.getUsername(), newUser.getToken(),
                newUser.getCreateTime(), newUser.getUpdateTime());
    }
}
