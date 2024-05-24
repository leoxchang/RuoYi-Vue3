package com.truntao.framework.security.handle;

import java.io.Serial;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.text.CharSequenceUtil;
import com.truntao.common.core.domain.R;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson2.JSON;
import com.truntao.common.constant.HttpStatus;
import com.truntao.common.utils.ServletUtils;

/**
 * 认证失败处理类 返回未授权
 *
 * @author truntao
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    @Serial
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        int code = HttpStatus.UNAUTHORIZED;
        String msg = CharSequenceUtil.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        ServletUtils.renderString(response, JSON.toJSONString(R.fail(code, msg)));
    }
}
