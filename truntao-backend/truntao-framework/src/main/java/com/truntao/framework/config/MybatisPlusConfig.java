package com.truntao.framework.config;

import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.truntao.**.mapper")
public class MybatisPlusConfig {

    /**
     * 分页插件
     *
     * @return PaginationInterceptor
     */
    @Bean
    @ConditionalOnMissingBean
    public PaginationInnerInterceptor paginationInterceptor() {
        return new PaginationInnerInterceptor();
    }


    /**
     * 乐观锁插件
     *
     * @return OptimisticLockerInnerInterceptor
     */
    @Bean
    public OptimisticLockerInnerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    @Bean
    public IKeyGenerator keyGenerator() {
        return new OracleKeyGenerator();
    }
}
