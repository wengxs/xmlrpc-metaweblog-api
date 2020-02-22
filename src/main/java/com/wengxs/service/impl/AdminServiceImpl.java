package com.wengxs.service.impl;

import com.wengxs.dao.AdminDao;
import com.wengxs.entity.Admin;
import com.wengxs.service.AdminService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Zorg
 * 2020-02-21 12:40
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Override
    public Admin find(Long id) {
        return adminDao.getOne(id);
    }

    @Override
    public Admin findByUsername(String username) {
        return adminDao.findByUsername(username);
    }

    @Override
    public Admin save(Admin admin) {
        return adminDao.save(admin);
    }

    @Override
    public boolean isValid(String username, String password) {
        Admin admin = adminDao.findByUsername(username);
        return admin != null && admin.getPassword().equals(DigestUtils.md5Hex(password));
    }

}
