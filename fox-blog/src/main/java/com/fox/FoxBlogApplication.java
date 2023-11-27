package com.fox;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.fox.mapper")
@EnableScheduling//@EnableScheduling是spring提供的定时任务的注解
public class FoxBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoxBlogApplication.class,args);
    }
}
