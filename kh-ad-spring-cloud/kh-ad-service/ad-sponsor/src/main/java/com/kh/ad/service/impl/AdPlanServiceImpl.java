package com.kh.ad.service.impl;

import com.kh.ad.constant.CommonStatus;
import com.kh.ad.constant.Constants;
import com.kh.ad.dao.AdPlanDao;
import com.kh.ad.dao.AdUserDao;
import com.kh.ad.entity.AdPlan;
import com.kh.ad.entity.AdUser;
import com.kh.ad.exception.AdException;
import com.kh.ad.service.IAdPlanService;
import com.kh.ad.utils.CommonUtils;
import com.kh.ad.vo.AdPlanGetRequest;
import com.kh.ad.vo.AdPlanRequest;
import com.kh.ad.vo.AdPlanResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author han.ke
 */
@Service
public class AdPlanServiceImpl implements IAdPlanService {

    private final AdUserDao userDao;

    private final AdPlanDao adPlanDao;

    public AdPlanServiceImpl(AdUserDao userDao, AdPlanDao adPlanDao) {
        this.userDao = userDao;
        this.adPlanDao = adPlanDao;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        //确保关联的User是存在的
        Optional<AdUser> adUser = userDao.findById(request.getUserId());
        if (!adUser.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        AdPlan oldPlan = adPlanDao.findByUserIdAndPlanName(request.getUserId(), request.getPlanName());
        if (oldPlan != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAM_ERROR);
        }

        AdPlan newAdPlan = adPlanDao.save(new AdPlan(request.getUserId(), request.getPlanName(),
                CommonUtils.parseStringDate(request.getStartDate()),
                CommonUtils.parseStringDate(request.getEndDate())));

        return new AdPlanResponse(newAdPlan.getId(), newAdPlan.getPlanName());
    }

    @Override
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException {
        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        return adPlanDao.findAllByIdInAAndUserId(request.getIds(), request.getUserId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException {
        if (!request.updateValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdPlan plan = adPlanDao.findByIdAndUserId(request.getId(), request.getUserId());

        if (plan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        if (request.getPlanName() != null) {
            plan.setPlanName(request.getPlanName());
        }

        if (request.getStartDate() != null) {
            plan.setStartDate(CommonUtils.parseStringDate(request.getStartDate()));
        }

        if (request.getEndDate() != null) {
            plan.setEndDate(CommonUtils.parseStringDate(request.getEndDate()));
        }

        plan.setUpdateTime(new Date());
        plan = adPlanDao.save(plan);
        return new AdPlanResponse(plan.getId(), plan.getPlanName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAdPlan(AdPlanRequest request) throws AdException {
        if (!request.deleteValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdPlan plan = adPlanDao.findByIdAndUserId(request.getId(), request.getUserId());
        if (plan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        plan.setPlanStatus(CommonStatus.INVALTD.getStatus());
        plan.setUpdateTime(new Date());
        adPlanDao.save(plan);
    }
}
