package com.kh.ad.dao;

import com.kh.ad.entity.Creative;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author han.ke
 */
public interface CreativeDao extends JpaRepository<Creative, Long> {

    /**
     * 根据创意名称和userId查询
     *
     * @param name
     * @param userId
     * @return
     */
    Creative findByNameAndUserId(String name, Long userId);
}
