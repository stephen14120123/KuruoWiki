package com.wiki;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.wiki.dao")
@ServletComponentScan
public class WikiApplication {
    public static void main(String[] args) {
        SpringApplication.run(WikiApplication.class, args);
        System.out.println("✦ 终端连接成功！KuroWiki 系统已启动 ✦");
        System.out.println("请访问: http://localhost:8088/");
    }
}