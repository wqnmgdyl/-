package com.kh.ad.dao;

import com.kh.ad.entity.AdPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author han.ke
 */
public interface AdPlanDao extends JpaRepository<AdPlan, Long> {
    /**
     * 根据id和userId查询
     *
     * @param id
     * @param userId
     * @return
     */
    AdPlan findByIdAndUserId(Long id, Long userId);

    /**
     * 根据id列表和userId查询所有
     *
     * @param ids
     * @param userId
     * @return
     */
    List<AdPlan> findAllByIdInAndUserId(List<Long> ids, Long userId);

    /**
     * 根据userId和planName查询
     *
     * @param userId
     * @param planName
     * @return
     */
    AdPlan findByUserIdAndPlanName(Long userId, String planName);

    /**
     * 根据plan状态查询所有
     *
     * @param status
     * @return
     */
    List<AdPlan> findAllByPlanStatus(Integer status);
}
