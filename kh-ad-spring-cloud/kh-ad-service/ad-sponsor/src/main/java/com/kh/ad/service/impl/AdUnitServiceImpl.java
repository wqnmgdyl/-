package com.kh.ad.service.impl;

import com.kh.ad.constant.Constants;
import com.kh.ad.dao.AdPlanDao;
import com.kh.ad.dao.AdUnitDao;
import com.kh.ad.entity.AdPlan;
import com.kh.ad.entity.AdUnit;
import com.kh.ad.exception.AdException;
import com.kh.ad.service.IAdUnitService;
import com.kh.ad.vo.AdUnitRequest;
import com.kh.ad.vo.AdUnitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author han.ke
 */
@Service
public class AdUnitServiceImpl implements IAdUnitService {

    private final AdPlanDao planDao;
    private final AdUnitDao unitDao;

    public AdUnitServiceImpl(AdPlanDao planDao, AdUnitDao unitDao) {
        this.planDao = planDao;
        this.unitDao = unitDao;
    }

    @Override
    public AdUnitResponse createUnit(AdUnitRequest request) throws AdException {

        if(!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        Optional<AdPlan> adPlan = planDao.findById(request.getPlanId());

        if(adPlan.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        AdUnit oldAdUnit = unitDao.findByPlanIdAndUnitName(request.getPlanId(),
                request.getUnitName());
        if(oldAdUnit != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }
        AdUnit newAdUnit = unitDao.save(new AdUnit(request.getPlanId(),request.getUnitName(),
                request.getPositionType(),request.getBudget()));
        return new AdUnitResponse(newAdUnit.getId(),newAdUnit.getUnitName());
    }
}
