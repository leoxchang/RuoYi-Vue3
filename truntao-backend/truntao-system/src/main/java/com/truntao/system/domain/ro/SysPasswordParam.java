package com.truntao.system.domain.ro;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author zhangxinlei
 * @date 2024-12-19
 */
@Data
public class SysPasswordParam {
    @NotBlank(message = "就密码不能为空")
    private String oldPassword;
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
