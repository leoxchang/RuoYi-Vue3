package com.truntao.system.domain.dto;

import com.truntao.common.core.domain.dto.SysUserDTO;
import lombok.Data;

/**
 * profile dto
 *
 * @author zhangxinlei
 * @date 2023-09-06
 */
@Data
public class ProfileDTO {

    private SysUserDTO user;

    private String roleGroup;

    private String postGroup;
}
