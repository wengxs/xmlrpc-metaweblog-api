package com.wengxs.dao;

import com.wengxs.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Zorg
 * 2020-02-21 12:25
 */
public interface AdminDao extends JpaRepository<Admin, Long> {

    Admin findByUsername(String username);

}
