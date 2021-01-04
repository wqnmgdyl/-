package com.kh.ad.dao;

import com.kh.ad.entity.AdUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author han.ke
 */
public interface AdUnitDao extends JpaRepository<AdUnit, Long> {

    /**
     * 根据planId和UnitName查询
     *
     * @param planId
     * @param unitName
     * @return
     */
    AdUnit findByPlanIdAndUnitName(Long planId, String unitName);

    /**
     * 根据unit状态查询所有
     *
     * @param unitStatus
     * @return
     */
    List<AdUnit> findAllByUnitStatus(Integer unitStatus);
}
