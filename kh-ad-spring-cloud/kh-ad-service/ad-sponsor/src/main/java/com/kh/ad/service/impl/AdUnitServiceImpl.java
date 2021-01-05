package com.kh.ad.service.impl;

import com.kh.ad.constant.Constants;
import com.kh.ad.dao.AdPlanDao;
import com.kh.ad.dao.AdUnitDao;
import com.kh.ad.dao.CreativeDao;
import com.kh.ad.dao.unit_condition.AdUnitDistrictDao;
import com.kh.ad.dao.unit_condition.AdUnitItDao;
import com.kh.ad.dao.unit_condition.AdUnitKeywordDao;
import com.kh.ad.dao.unit_condition.CreativeUnitDao;
import com.kh.ad.entity.AdPlan;
import com.kh.ad.entity.AdUnit;
import com.kh.ad.entity.unit_condition.AdUnitDistrict;
import com.kh.ad.entity.unit_condition.AdUnitIt;
import com.kh.ad.entity.unit_condition.AdUnitKeyword;
import com.kh.ad.entity.unit_condition.CreativeUnit;
import com.kh.ad.exception.AdException;
import com.kh.ad.service.IAdUnitService;
import com.kh.ad.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author han.ke
 */
@Service
public class AdUnitServiceImpl implements IAdUnitService {

    private final AdPlanDao planDao;
    private final AdUnitDao unitDao;
    private final AdUnitKeywordDao unitKeywordDao;
    private final AdUnitItDao unitItDao;
    private final AdUnitDistrictDao unitDistrictDao;
    private final CreativeDao creativeDao;
    private final CreativeUnitDao creativeUnitDao;

    public AdUnitServiceImpl(AdPlanDao planDao,
                             AdUnitDao unitDao,
                             AdUnitKeywordDao unitKeywordDao,
                             AdUnitDistrictDao unitDistrictDao,
                             AdUnitItDao unitItDao, CreativeDao creativeDao, CreativeUnitDao creativeUnitDao) {
        this.planDao = planDao;
        this.unitDao = unitDao;
        this.unitKeywordDao = unitKeywordDao;
        this.unitDistrictDao = unitDistrictDao;
        this.unitItDao = unitItDao;
        this.creativeDao = creativeDao;
        this.creativeUnitDao = creativeUnitDao;
    }

    @Override
    public AdUnitResponse createUnit(AdUnitRequest request) throws AdException {

        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        Optional<AdPlan> adPlan = planDao.findById(request.getPlanId());

        if (adPlan.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        AdUnit oldAdUnit = unitDao.findByPlanIdAndUnitName(request.getPlanId(),
                request.getUnitName());
        if (oldAdUnit != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }
        AdUnit newAdUnit = unitDao.save(new AdUnit(request.getPlanId(), request.getUnitName(),
                request.getPositionType(), request.getBudget()));
        return new AdUnitResponse(newAdUnit.getId(), newAdUnit.getUnitName());
    }

    @Override
    public AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException {
        List<Long> unitIds = request.getUnitKeywords().stream()
                .map(AdUnitKeywordRequest.UnitKeyword::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<Long> ids = Collections.emptyList();
        List<AdUnitKeyword> unitKeywords = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getUnitKeywords())) {
            request.getUnitKeywords().forEach(i -> unitKeywords.add(
                    new AdUnitKeyword(i.getUnitId(), i.getKeyword())
            ));
            ids = unitKeywordDao.saveAll(unitKeywords).stream().
                    map(AdUnitKeyword::getId).
                    collect(Collectors.toList());
        }
        return new AdUnitKeywordResponse(ids);
    }

    @Override
    public AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException {
        List<Long> unitIds = request.getUnitIts().stream().
                map(AdUnitItRequest.UnitIt::getUnitId).
                collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<AdUnitIt> unitIts = new ArrayList<>();
        request.getUnitIts().forEach(i -> unitIts.add(
                new AdUnitIt(i.getUnitId(), i.getItTag())
        ));
        List<Long> ids = unitItDao.saveAll(unitIts).stream().
                map(AdUnitIt::getId).
                collect(Collectors.toList());
        ;
        return new AdUnitItResponse(ids);
    }

    @Override
    public AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException {
        List<Long> unitIds = request.getUnitDistricts().stream().
                map(AdUnitDistrictRequest.UnitDistrict::getUnitId).
                collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<AdUnitDistrict> unitDistricts = new ArrayList<>();
        request.getUnitDistricts().forEach(i -> unitDistricts.add(
                new AdUnitDistrict(i.getUnitId(), i.getProvince(), i.getCity())
        ));
        List<Long> ids = unitDistrictDao.saveAll(unitDistricts).stream().
                map(AdUnitDistrict::getId).collect(Collectors.toList());
        return new AdUnitDistrictResponse(ids);
    }

    @Override
    public CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException {
        List<Long> unitIds = request.getUnitItems().stream().
                map(CreativeUnitRequest.CreativeUnitItem::getUnitId).
                collect(Collectors.toList());

        List<Long> creativeIds = request.getUnitItems().stream().
                map(CreativeUnitRequest.CreativeUnitItem::getCreativeId).
                collect(Collectors.toList());

        if (!(isRelatedUnitExist(unitIds) && isRelatedCreativeExist(creativeIds))) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<CreativeUnit> creativeUnits = new ArrayList<>();
        request.getUnitItems().forEach(i -> creativeUnits.add(
                new CreativeUnit(i.getCreativeId(), i.getUnitId())
        ));
        List<Long> ids = creativeUnitDao.saveAll(creativeUnits).stream().
                map(CreativeUnit::getId).
                collect(Collectors.toList());
        return new CreativeUnitResponse(ids);
    }

    private boolean isRelatedUnitExist(List<Long> unitIds) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return false;
        }
        return unitDao.findAllById(unitIds).size() ==
                new HashSet<>(unitIds).size();
    }

    private boolean isRelatedCreativeExist(List<Long> creativeIds) {
        if (CollectionUtils.isEmpty(creativeIds)) {
            return false;
        }

        return creativeDao.findAllById(creativeIds).size() ==
                new HashSet<>(creativeIds).size();
    }
}
