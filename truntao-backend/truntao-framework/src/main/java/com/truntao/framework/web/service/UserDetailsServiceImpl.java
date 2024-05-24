package com.truntao.framework.web.service;

import com.truntao.common.core.domain.dto.SysUserDTO;
import com.truntao.common.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.truntao.common.core.domain.model.LoginUser;
import com.truntao.common.enums.UserStatus;
import com.truntao.common.exception.ServiceException;
import com.truntao.system.service.ISysUserService;

import java.util.Objects;

/**
 * 用户验证处理
 *
 * @author truntao
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final ISysUserService userService;

    private final SysPasswordService passwordService;

    private final SysPermissionService permissionService;

    @Autowired
    public UserDetailsServiceImpl(ISysUserService userService, SysPasswordService passwordService,
                                  SysPermissionService permissionService) {
        this.userService = userService;
        this.passwordService = passwordService;
        this.permissionService = permissionService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserDTO user = userService.selectUserByUserName(username);
        if (Objects.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException(MessageUtils.message("user.not.exists"));
        } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", username);
            throw new ServiceException(MessageUtils.message("user.password.delete"));
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException(MessageUtils.message("user.blocked"));
        }

        passwordService.validate(user);

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUserDTO user) {
        return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
    }
}
