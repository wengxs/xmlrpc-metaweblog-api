package com.wengxs.service;

import com.wengxs.entity.Admin;

/**
 * Created by Zorg
 * 2020-02-21 12:38
 */
public interface AdminService {

    Admin find(Long id);

    Admin findByUsername(String username);

    Admin save(Admin admin);

    boolean isValid(String username, String password);

}
