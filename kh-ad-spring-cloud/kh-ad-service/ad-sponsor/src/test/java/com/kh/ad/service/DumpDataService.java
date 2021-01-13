package com.kh.ad.service;

import com.alibaba.fastjson.JSON;
import com.kh.ad.Application;
import com.kh.ad.constant.CommonStatus;
import com.kh.ad.dao.AdPlanDao;
import com.kh.ad.dao.AdUnitDao;
import com.kh.ad.dao.CreativeDao;
import com.kh.ad.dao.unit_condition.AdUnitDistrictDao;
import com.kh.ad.dao.unit_condition.AdUnitItDao;
import com.kh.ad.dao.unit_condition.AdUnitKeywordDao;
import com.kh.ad.dao.unit_condition.CreativeUnitDao;
import com.kh.ad.dump.DConstant;
import com.kh.ad.dump.table.*;
import com.kh.ad.entity.AdPlan;
import com.kh.ad.entity.AdUnit;
import com.kh.ad.entity.Creative;
import com.kh.ad.entity.unit_condition.AdUnitDistrict;
import com.kh.ad.entity.unit_condition.AdUnitIt;
import com.kh.ad.entity.unit_condition.AdUnitKeyword;
import com.kh.ad.entity.unit_condition.CreativeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author han.ke
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DumpDataService {

    @Autowired
    private AdPlanDao planDao;
    @Autowired
    private AdUnitDao unitDao;
    @Autowired
    private CreativeDao creativeDao;
    @Autowired
    private CreativeUnitDao creativeUnitDao;
    @Autowired
    private AdUnitDistrictDao districtDao;
    @Autowired
    private AdUnitItDao itDao;
    @Autowired
    private AdUnitKeywordDao keywordDao;

    @Test
    public void dumpAdTableData() {
        dumpAdPlanTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_PLAN));
        dumpAdUnitTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT));
        dumpAdUnitItTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_IT));
        dumpAdUnitKeywordTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_KEYWORD));
        dumpAdUnitDistrictTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_DISTRICT));
        dumpAdCreativeTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE));
        dumpAdCreativeUnitTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE_UNIT));
    }

    private void dumpAdPlanTable(String fileName) {
        Map<String, Object> map = new HashMap<>();
        map.put("plan_status", CommonStatus.VALTD.getStatus());
        List<AdPlan> adPlans = planDao.selectByMap(map);
        if (CollectionUtils.isEmpty(adPlans)) {
            return;
        }
        List<AdPlanTable> planTables = new ArrayList<>();
        adPlans.forEach(p -> planTables.add(
                new AdPlanTable(p.getId(), p.getUserId(), p.getPlanStatus(),
                        p.getStartDate(), p.getEndDate())
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdPlanTable planTable : planTables) {
                writer.write(JSON.toJSONString(planTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dumpAdPlanTable error");
        }
    }

    private void dumpAdUnitTable(String fileName) {
        Map<String, Object> map = new HashMap<>();
        map.put("unit_status", CommonStatus.VALTD.getStatus());
        List<AdUnit> adUnits = unitDao.selectByMap(map);
        if (CollectionUtils.isEmpty(adUnits)) {
            return;
        }
        List<AdUnitTable> unitTables = new ArrayList<>();
        adUnits.forEach(u -> unitTables.add(
                new AdUnitTable(u.getId(), u.getUnitStatus(),
                        u.getPositionType(), u.getPlanId())
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitTable unitTable : unitTables) {
                writer.write(JSON.toJSONString(unitTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dumpAdUnitTable error");
        }
    }

    private void dumpAdCreativeTable(String fileName) {
        List<Creative> creatives = creativeDao.selectList(null);
        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }
        List<AdCreativeTable> creativeTables = new ArrayList<>();
        creatives.forEach(c -> creativeTables.add(
                new AdCreativeTable(c.getId(), c.getName(), c.getType(), c.getMaterialType(),
                        c.getHeight(), c.getWidth(), c.getAuditStatus(), c.getUrl())
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdCreativeTable creativeTable : creativeTables) {
                writer.write(JSON.toJSONString(creativeTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dumpAdCreativeTable error");
        }
    }

    private void dumpAdUnitItTable(String fileName) {
        List<AdUnitIt> unitIts = itDao.selectList(null);
        if (CollectionUtils.isEmpty(unitIts)) {
            return;
        }
        List<AdUnitItTable> unitItTables = new ArrayList<>();
        unitIts.forEach(i -> unitItTables.add(
                new AdUnitItTable(i.getUnitId(), i.getItTag())
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitItTable unitItTable : unitItTables) {
                writer.write(JSON.toJSONString(unitItTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dumpAdUnitItTable error");
        }
    }

    private void dumpAdUnitKeywordTable(String fileName) {
        List<AdUnitKeyword> unitKeywords = keywordDao.selectList(null);
        if (CollectionUtils.isEmpty(unitKeywords)) {
            return;
        }
        List<AdUnitKeywordTable> unitKeywordTables = new ArrayList<>();
        unitKeywords.forEach(k -> unitKeywordTables.add(
                new AdUnitKeywordTable(k.getUnitId(), k.getKeyword())
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitKeywordTable unitKeywordTable : unitKeywordTables) {
                writer.write(JSON.toJSONString(unitKeywordTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dumpAdUnitKeywordTable error");
        }
    }

    private void dumpAdUnitDistrictTable(String fileName) {
        List<AdUnitDistrict> unitDistricts = districtDao.selectList(null);
        if (CollectionUtils.isEmpty(unitDistricts)) {
            return;
        }
        List<AdUnitDistrictTable> unitDistrictTables = new ArrayList<>();
        unitDistricts.forEach(d -> unitDistrictTables.add(
                new AdUnitDistrictTable(d.getUnitId(), d.getProvince(), d.getCity())
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitDistrictTable unitDistrictTable : unitDistrictTables) {
                writer.write(JSON.toJSONString(unitDistrictTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dumpAdUnitDistrictTable error");
        }
    }

    private void dumpAdCreativeUnitTable(String fileName) {
        List<CreativeUnit> creativeUnits = creativeUnitDao.selectList(null);
        if (CollectionUtils.isEmpty(creativeUnits)) {
            return;
        }
        List<AdCreativeUnitTable> creativeUnitTables = new ArrayList<>();
        creativeUnits.forEach(c -> creativeUnitTables.add(
                new AdCreativeUnitTable(c.getCreativeId(), c.getUnitId())
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdCreativeUnitTable creativeUnitTable : creativeUnitTables) {
                writer.write(JSON.toJSONString(creativeUnitTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dumpAdCreativeUnitTable error");
        }
    }
}
