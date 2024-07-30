package com.truntao.system.service.impl;

import org.springframework.stereotype.Service;

/**
 * admin service
 *
 * @author zhangxinlei
 * @date 2024-07-30
 */
@Service
public class AdminService {

    /**
     * 判断用户是否为admin
     * @param userId 用户标识
     * @return 是或者否
     */
    public boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }
}
