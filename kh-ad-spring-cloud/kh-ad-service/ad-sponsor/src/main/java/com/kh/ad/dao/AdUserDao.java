package com.kh.ad.dao;

import com.kh.ad.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author han.ke
 */
public interface AdUserDao extends JpaRepository<AdUser, Long> {

    /**
     * 根据用户名查找用户记录
     *
     * @param username
     * @return
     */
    AdUser findByUsername(String username);
}
