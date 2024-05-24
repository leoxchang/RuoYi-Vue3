package com.truntao.framework.security.context;

import org.springframework.security.core.Authentication;

/**
 * 身份验证信息
 *
 * @author truntao
 */
public class AuthenticationContextHolder {

    private static final ThreadLocal<Authentication> contextHolder = new ThreadLocal<>();

    private AuthenticationContextHolder(){
    }

    public static Authentication getContext() {
        return contextHolder.get();
    }

    public static void setContext(Authentication context) {
        contextHolder.set(context);
    }

    public static void clearContext() {
        contextHolder.remove();
    }
}
