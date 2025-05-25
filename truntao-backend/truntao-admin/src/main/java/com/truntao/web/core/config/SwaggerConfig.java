package com.truntao.web.core.config;


import com.truntao.common.config.TruntaoConfig;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger2的接口配置
 *
 * @author truntao
 */
@Configuration
@OpenAPIDefinition(security = @SecurityRequirement(name = "Authorization"))
@SecurityScheme(type = SecuritySchemeType.APIKEY,name = "Authorization",scheme = "bearer",in = SecuritySchemeIn.HEADER)
public class SwaggerConfig {
    /**
     * 系统基础配置
     */
    @Resource
    private TruntaoConfig truntaoConfig;

    /**
     * 创建API
     */
    @Bean
    public OpenAPI createRestApi() {
        return new OpenAPI().info(apiInfo());
    }

    /**
     * 添加摘要信息
     */
    private Info apiInfo() {
        Contact contact = new Contact();
        contact.setName(truntaoConfig.getName());
        return new Info()
                // 设置标题
                .title("标题：后台管理系统_接口文档")
                // 描述
                .description("描述：......")
                // 作者信息
                .contact(contact)
                // 版本
                .version("版本号:" + truntaoConfig.getVersion());
    }
}
