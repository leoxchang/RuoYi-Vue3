package com.truntao.common.filter;

import com.truntao.common.enums.HttpMethod;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 防止XSS攻击的过滤器
 *
 * @author truntao
 */
public class XssFilter implements Filter {
    /**
     * 排除链接
     */
    protected static final List<String> excludes = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) {
        String tempExcludes = filterConfig.getInitParameter("excludes");
        if (StringUtils.isNotEmpty(tempExcludes)) {
            String[] url = tempExcludes.split(",");
            excludes.addAll(Arrays.asList(url));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (handleExcludeURL(req)) {
            chain.doFilter(request, response);
            return;
        }
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
        chain.doFilter(xssRequest, response);
    }

    private boolean handleExcludeURL(HttpServletRequest request) {
        String url = request.getServletPath();
        String method = request.getMethod();
        // GET DELETE 不过滤
        if (method == null || HttpMethod.GET.matches(method) || HttpMethod.DELETE.matches(method)) {
            return true;
        }
        return matches(url);
    }

    /**
     * 查找指定字符串是否匹配指定字符串列表中的任意一个字符串
     *
     * @param str 指定字符串
     * @return 是否匹配
     */
    private boolean matches(String str) {
        if (StringUtils.isEmpty(str) || CollectionUtils.isEmpty(excludes)) {
            return false;
        }
        for (String pattern : excludes) {
            if (isMatch(pattern, str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断url是否与规则配置:
     * ? 表示单个字符;
     * * 表示一层路径内的任意字符串，不可跨层级;
     * ** 表示任意层路径;
     *
     * @param pattern 匹配规则
     * @param url     需要匹配的url
     * @return boolean
     */
    private boolean isMatch(String pattern, String url) {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(pattern, url);
    }
}