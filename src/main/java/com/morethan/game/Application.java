package com.morethan.game;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring-boot入口类
 *
 * @CreateDate: 31/03/2018
 * @UpdateDate: 31/03/2018 9:58 PM
 * @Version: 1.0
 */
@EnableAsync
@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.morethan.game.mapper")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }

}

