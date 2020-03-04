package com.oudake.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author wangyi
 */
@SpringBootApplication
@MapperScan(basePackages = "com.oudake.core.dao")
public class CoreApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow","|{}");
        return application.sources(CoreApplication.class);
    }

}

