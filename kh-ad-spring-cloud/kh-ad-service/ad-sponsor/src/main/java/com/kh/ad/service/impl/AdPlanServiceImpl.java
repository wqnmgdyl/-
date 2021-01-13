package com.kh.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import org.apache.tomcat.util.bcel.Const;
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
        AdUser adUser = userDao.selectById(request.getUserId());
        if (adUser == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        QueryWrapper<AdPlan> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", request.getUserId()).
                eq("plan_name", request.getPlanName());
        AdPlan oldPlan = adPlanDao.selectOne(wrapper);
        if (oldPlan != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }

        int insert = adPlanDao.insert(new AdPlan(request.getUserId(), request.getPlanName(),
                CommonUtils.parseStringDate(request.getStartDate()),
                CommonUtils.parseStringDate(request.getEndDate())));

        if (insert <= 0) {
            throw new AdException(Constants.ErrorMsg.INSERT_ERROR);
        }

        AdPlan newAdPlan = adPlanDao.selectOne(wrapper);

        return new AdPlanResponse(newAdPlan.getId(), newAdPlan.getPlanName());
    }

    @Override
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException {
        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        QueryWrapper<AdPlan> wrapper = new QueryWrapper<>();
        wrapper.in("id", request.getIds()).
                eq("user_id", request.getUserId());
        List<AdPlan> adPlans = adPlanDao.selectList(wrapper);
        return adPlans;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException {
        if (!request.updateValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        QueryWrapper<AdPlan> wrapper = new QueryWrapper<>();
        wrapper.eq("id", request.getId()).
                eq("user_id", request.getUserId());
        AdPlan plan = adPlanDao.selectOne(wrapper);

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
        int update = adPlanDao.updateById(plan);
        return new AdPlanResponse(plan.getId(), plan.getPlanName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAdPlan(AdPlanRequest request) throws AdException {
        if (!request.deleteValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        QueryWrapper<AdPlan> wrapper = new QueryWrapper<>();
        wrapper.eq("id", request.getId()).
                eq("user_id", request.getUserId());
        AdPlan plan = adPlanDao.selectOne(wrapper);
        if (plan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        plan.setPlanStatus(CommonStatus.INVALTD.getStatus());
        plan.setUpdateTime(new Date());
        adPlanDao.updateById(plan);
    }
}
