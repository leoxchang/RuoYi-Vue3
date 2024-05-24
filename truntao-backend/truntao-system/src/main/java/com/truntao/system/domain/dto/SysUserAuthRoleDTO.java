package com.truntao.system.domain.dto;

import com.truntao.common.core.domain.dto.SysRoleDTO;
import com.truntao.common.core.domain.dto.SysUserDTO;
import lombok.Data;

import java.util.List;

/**
 * @author zhangxinlei
 * @date 2023-10-08
 */
@Data
public class SysUserAuthRoleDTO {

    private SysUserDTO user;

    private List<SysRoleDTO> roles;
}
