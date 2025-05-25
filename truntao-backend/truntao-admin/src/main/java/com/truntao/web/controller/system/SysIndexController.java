package com.truntao.web.controller.system;

import cn.hutool.core.text.CharSequenceUtil;
import com.truntao.common.config.TruntaoConfig;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页
 *
 * @author truntao
 */
@RestController
public class SysIndexController {
    /**
     * 系统基础配置
     */
    @Resource
    private TruntaoConfig truntaoConfig;

    /**
     * 访问首页，提示语
     */
    @RequestMapping("/")
    public String index() {
        return CharSequenceUtil.format("欢迎使用{}后台管理框架，当前版本：v{}，请通过前端地址访问。", truntaoConfig.getName(),
                truntaoConfig.getVersion());
    }
}
