package com.truntao.framework.config;

import com.truntao.framework.config.properties.PermitAllUrlProperties;
import com.truntao.framework.security.filter.JwtAuthenticationTokenFilter;
import com.truntao.framework.security.handle.AuthenticationEntryPointImpl;
import com.truntao.framework.security.handle.LogoutSuccessHandlerImpl;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * spring security配置
 *
 * @author truntao
 */
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
public class SecurityConfig {

    /**
     * 认证失败处理类
     */
    @Resource
    private AuthenticationEntryPointImpl unauthorizedHandler;

    /**
     * 退出处理类
     */
    @Resource
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * token认证过滤器
     */
    @Resource
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    /**
     * 跨域过滤器
     */
    @Resource
    private CorsFilter corsFilter;

    /**
     * 允许匿名访问的地址
     */
    @Resource
    private PermitAllUrlProperties permitAllUrl;


    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // 注解标记允许匿名访问的url
        httpSecurity.authorizeHttpRequests(authorizeHttpRequestsCustomizer -> {
            permitAllUrl.getUrls().forEach(url -> authorizeHttpRequestsCustomizer.requestMatchers(url).permitAll());
        });


        httpSecurity
                // CSRF禁用，因为不使用session
                .csrf(AbstractHttpConfigurer::disable)
                .headers(httpSecurityHeadersConfigurer -> {
                    // 禁用HTTP响应标头
                    httpSecurityHeadersConfigurer.cacheControl(HeadersConfigurer.CacheControlConfig::disable)
                            .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                            .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
                })
                // 认证失败处理类
                .exceptionHandling(exceptionHandlingCustomizer -> {
                    exceptionHandlingCustomizer.authenticationEntryPoint(unauthorizedHandler);
                })
                // 基于token，所以不需要session
                .sessionManagement(sessionManagementCustomizer -> {
                    sessionManagementCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                // 过滤请求
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> {
                    // 对于登录login 注册register 验证码captchaImage 允许匿名访问
                    authorizeHttpRequestsCustomizer.requestMatchers("/login", "/register", "/captchaImage").permitAll()
                            // 静态资源，可匿名访问
                            .requestMatchers(HttpMethod.GET, "/", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js",
                                    "/profile/**").permitAll()
                            .requestMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/*/api-docs"
                                    , "/*/api-docs/swagger-config", "/druid/**").permitAll()
                            .anyRequest().authenticated();
                });
        // 添加Logout filter
        httpSecurity.logout(logoutCustomizer -> {
            logoutCustomizer.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
        });
        // 添加JWT filter
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 添加CORS filter
        httpSecurity.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
        httpSecurity.addFilterBefore(corsFilter, LogoutFilter.class);

        return httpSecurity.build();
    }

    /**
     * 强散列哈希加密实现
     */
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 身份认证接口
     */
    @Bean
    public AuthenticationManager configure(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
