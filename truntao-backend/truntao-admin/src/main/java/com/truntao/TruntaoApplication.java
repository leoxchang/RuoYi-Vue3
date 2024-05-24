package com.truntao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author truntao
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TruntaoApplication {
    public static void main(String[] args) {
        SpringApplication.run(TruntaoApplication.class, args);
    }
}
