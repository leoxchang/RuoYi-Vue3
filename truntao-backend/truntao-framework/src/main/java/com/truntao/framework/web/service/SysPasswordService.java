package com.truntao.framework.web.service;

import java.util.concurrent.TimeUnit;

import com.truntao.common.core.domain.dto.SysUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.truntao.common.constant.CacheConstants;
import com.truntao.common.core.redis.RedisCache;
import com.truntao.common.exception.user.UserPasswordNotMatchException;
import com.truntao.common.exception.user.UserPasswordRetryLimitExceedException;
import com.truntao.common.utils.SecurityUtils;
import com.truntao.framework.security.context.AuthenticationContextHolder;

/**
 * 登录密码方法
 *
 * @author truntao
 */
@Component
public class SysPasswordService {

    private final RedisCache redisCache;

    @Value(value = "${user.password.maxRetryCount}")
    private int maxRetryCount;

    @Value(value = "${user.password.lockTime}")
    private int lockTime;

    @Autowired
    public SysPasswordService(RedisCache redisCache){
        this.redisCache = redisCache;
    }

    /**
     * 登录账户密码错误次数缓存键名
     *
     * @param username 用户名
     * @return 缓存键key
     */
    private String getCacheKey(String username) {
        return CacheConstants.PWD_ERR_CNT_KEY + username;
    }

    public void validate(SysUserDTO user) {
        Authentication usernamePasswordAuthenticationToken = AuthenticationContextHolder.getContext();
        String username = usernamePasswordAuthenticationToken.getName();
        String password = usernamePasswordAuthenticationToken.getCredentials().toString();

        Integer retryCount = redisCache.getCacheObject(getCacheKey(username));

        if (retryCount == null) {
            retryCount = 0;
        }

        if (retryCount >= maxRetryCount) {
            throw new UserPasswordRetryLimitExceedException(maxRetryCount, lockTime);
        }

        if (!matches(user, password)) {
            retryCount = retryCount + 1;
            redisCache.setCacheObject(getCacheKey(username), retryCount, lockTime, TimeUnit.MINUTES);
            throw new UserPasswordNotMatchException();
        } else {
            clearLoginRecordCache(username);
        }
    }

    public boolean matches(SysUserDTO user, String rawPassword) {
        return SecurityUtils.matchesPassword(rawPassword, user.getPassword());
    }

    public void clearLoginRecordCache(String loginName) {
        if (Boolean.TRUE.equals(redisCache.hasKey(getCacheKey(loginName)))) {
            redisCache.deleteObject(getCacheKey(loginName));
        }
    }
}
