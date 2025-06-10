package com.truntao.system.domain.dto;

import com.truntao.common.core.domain.dto.SysUserDTO;
import lombok.Data;

import java.util.Set;

/**
 * @author zhangxinlei
 * @date 2023-08-30
 */
@Data
public class LoginUserInfoDTO {

    private Set<String> permissions;

    private Set<String> roles;

    private SysUserDTO user;

    private boolean isDefaultModifyPwd;
}
