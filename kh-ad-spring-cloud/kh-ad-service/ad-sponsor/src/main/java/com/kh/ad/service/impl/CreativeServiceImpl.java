package com.kh.ad.service.impl;

import com.kh.ad.constant.Constants;
import com.kh.ad.dao.AdUserDao;
import com.kh.ad.dao.CreativeDao;
import com.kh.ad.entity.AdUser;
import com.kh.ad.entity.Creative;
import com.kh.ad.exception.AdException;
import com.kh.ad.service.ICreativeService;
import com.kh.ad.vo.CreativeRequest;
import com.kh.ad.vo.CreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author han.ke
 */
@Service
public class CreativeServiceImpl implements ICreativeService {

    private final CreativeDao creativeDao;
    private final AdUserDao userDao;

    public CreativeServiceImpl(CreativeDao creativeDao, AdUserDao userDao) {
        this.creativeDao = creativeDao;
        this.userDao = userDao;
    }

    @Override
    public CreativeResponse createCreative(CreativeRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        Optional<AdUser> user = userDao.findById(request.getUserId());
        if (!user.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        Creative oldCreative = creativeDao.findByNameAndUserId(request.getName(), request.getUserId());
        if (oldCreative != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_CREATIVE_ERROR);
        }

        Creative newCreative = creativeDao.save(request.convertToEntity());

        return new CreativeResponse(newCreative.getId(), newCreative.getName());
    }
}
