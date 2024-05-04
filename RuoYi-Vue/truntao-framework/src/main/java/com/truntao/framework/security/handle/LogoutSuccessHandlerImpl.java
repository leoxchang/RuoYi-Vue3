package com.truntao.framework.security.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.truntao.common.core.domain.R;
import com.truntao.common.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import com.alibaba.fastjson2.JSON;
import com.truntao.common.constant.Constants;
import com.truntao.common.core.domain.model.LoginUser;
import com.truntao.common.utils.ServletUtils;
import com.truntao.framework.manager.AsyncManager;
import com.truntao.framework.manager.factory.AsyncFactory;
import com.truntao.framework.web.service.TokenService;

import java.util.Objects;

/**
 * 自定义退出处理类 返回成功
 *
 * @author truntao
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    private final TokenService tokenService;
    @Autowired
    public LogoutSuccessHandlerImpl(TokenService tokenService){
        this.tokenService = tokenService;
    }

    /**
     * 退出处理
     *
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (Objects.nonNull(loginUser)) {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(userName, Constants.LOGOUT, MessageUtils.message(
                    "user.logout.success")));
        }
        ServletUtils.renderString(response, JSON.toJSONString(R.ok(MessageUtils.message("user.logout" +
                ".success"))));
    }
}
