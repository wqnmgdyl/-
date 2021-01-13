package com.kh.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.kh.ad.entity.Creative;
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

        AdPlan adPlan = planDao.selectById(request.getPlanId());

        if (adPlan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        QueryWrapper<AdUnit> wrapper = new QueryWrapper<>();
        wrapper.eq("plan_id", request.getPlanId()).
                eq("unit_name", request.getUnitName());
        AdUnit oldAdUnit = unitDao.selectOne(wrapper);
        if (oldAdUnit != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }
        int insert = unitDao.insert(new AdUnit(request.getPlanId(), request.getUnitName(),
                request.getPositionType(), request.getBudget()));

        if (insert <= 0) {
            throw new AdException(Constants.ErrorMsg.INSERT_ERROR);
        }

        AdUnit newAdUnit = unitDao.selectOne(wrapper);
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
            List<Long> uIds = new ArrayList<>();
            List<String> keyword = new ArrayList<>();
            for (AdUnitKeyword unitKeyword : unitKeywords) {
                uIds.add(unitKeyword.getUnitId());
                keyword.add(unitKeyword.getKeyword());
                int insert = unitKeywordDao.insert(unitKeyword);
                if (insert <= 0) {
                    throw new AdException(Constants.ErrorMsg.INSERT_ERROR);
                }
            }
            QueryWrapper<AdUnitKeyword> wrapper = new QueryWrapper<>();
            wrapper.in("unit_id", uIds).
                    in("keyword", keyword);
            ids = unitKeywordDao.selectList(wrapper).stream().
                    map(AdUnitKeyword::getId).collect(Collectors.toList());
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
        List<Long> uIds = new ArrayList<>();
        List<String> tags = new ArrayList<>();
        for (AdUnitIt unitIt : unitIts) {
            uIds.add(unitIt.getUnitId());
            tags.add(unitIt.getItTag());
            int insert = unitItDao.insert(unitIt);
            if (insert <= 0) {
                throw new AdException(Constants.ErrorMsg.INSERT_ERROR);
            }
        }
        QueryWrapper<AdUnitIt> wrapper = new QueryWrapper<>();
        wrapper.in("unit_id", uIds).
                in("it_tag", tags);
        List<Long> ids = unitItDao.selectList(wrapper).stream().
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
        List<Long> uIds = new ArrayList<>();
        List<String> province = new ArrayList<>();
        List<String> city = new ArrayList<>();
        for (AdUnitDistrict unitDistrict : unitDistricts) {
            uIds.add(unitDistrict.getUnitId());
            province.add(unitDistrict.getProvince());
            city.add(unitDistrict.getCity());
            int insert = unitDistrictDao.insert(unitDistrict);
            if (insert <= 0) {
                throw new AdException(Constants.ErrorMsg.INSERT_ERROR);
            }
        }
        QueryWrapper<AdUnitDistrict> wrapper = new QueryWrapper<>();
        wrapper.in("unit_id", uIds).
                in("province", province).
                in("city", city);
        List<Long> ids = unitDistrictDao.selectList(wrapper).stream().
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
        List<Long> uIds = new ArrayList<>();
        List<Long> cIds = new ArrayList<>();
        for (CreativeUnit creativeUnit : creativeUnits) {
            uIds.add(creativeUnit.getUnitId());
            cIds.add(creativeUnit.getCreativeId());
            int insert = creativeUnitDao.insert(creativeUnit);
            if (insert <= 0) {
                throw new AdException(Constants.ErrorMsg.INSERT_ERROR);
            }
        }
        QueryWrapper<CreativeUnit> wrapper = new QueryWrapper<>();
        wrapper.in("unit_id", uIds).
                in("creative_id", cIds);
        List<Long> ids = creativeUnitDao.selectList(wrapper).stream().
                map(CreativeUnit::getId).
                collect(Collectors.toList());
        return new CreativeUnitResponse(ids);
    }

    private boolean isRelatedUnitExist(List<Long> unitIds) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return false;
        }
        return unitDao.selectBatchIds(unitIds).size() ==
                new HashSet<>(unitIds).size();
    }

    private boolean isRelatedCreativeExist(List<Long> creativeIds) {
        if (CollectionUtils.isEmpty(creativeIds)) {
            return false;
        }
        return creativeDao.selectBatchIds(creativeIds).size() ==
                new HashSet<>(creativeIds).size();
    }
}
