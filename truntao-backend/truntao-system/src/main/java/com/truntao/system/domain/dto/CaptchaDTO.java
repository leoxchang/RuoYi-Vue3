package com.truntao.system.domain.dto;

import lombok.Data;

/**
 * @author zhangxinlei
 * @date 2024-03-15
 */
@Data
public class CaptchaDTO {

    private boolean captchaEnabled;

    private String uuid;

    private String img;
}
