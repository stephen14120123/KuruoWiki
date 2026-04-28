package com.wiki;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
// 👈 注意：这里那一排 @ComponentScan("com.wiki.controller") 已经被删掉了！

@SpringBootApplication
@MapperScan("com.wiki.dao")
@ServletComponentScan
public class WikiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WikiApplication.class, args);
        System.out.println("✦ 终端连接成功！KuruoWiki 系统已启动 ✦");
        System.out.println("请访问: http://localhost:8088/");
    }
}
