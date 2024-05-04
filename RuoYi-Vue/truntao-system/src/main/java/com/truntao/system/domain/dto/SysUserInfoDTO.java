package com.truntao.system.domain.dto;

import com.truntao.common.core.domain.dto.SysRoleDTO;
import com.truntao.common.core.domain.dto.SysUserDTO;
import lombok.Data;

import java.util.List;

/**
 * @author zhangxinlei
 * @date 2023-08-30
 */
@Data
public class SysUserInfoDTO {

    private List<SysRoleDTO> roles;

    private List<SysPostDTO> posts;

    private List<Long> postIds;

    private List<Long> roleIds;

    private SysUserDTO user;
}
