package com.blog.blogemail;

import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Import({
        com.blog.base.emailApi.config.Config.class
})
@EnableFeignClients(basePackages = {
        com.blog.base.emailApi.config.Config.API_BASE_PACKAGE
})
@SpringBootApplication
@MapperScan("com.blog.blogemail.mapper")
@EnableSwagger2
public class BlogEmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogEmailApplication.class, args);
    }

}
