package com.blog.blogemail;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.blog.bloguser.mapper")
@NacosPropertySource(dataId = "email-service.yaml", autoRefreshed = true)
public class BlogEmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogEmailApplication.class, args);
    }

}
