package com.wiki;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.wiki.dao") // 扫描 MyBatis 的 Mapper 接口
@ServletComponentScan       // 兼容旧的 Servlet
@ComponentScan("com.wiki.controller") // 强制扫描 Controller 包
public class WikiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WikiApplication.class, args);
        System.out.println("✦ 终端连接成功！KuruoWiki 系统已启动 ✦");
        System.out.println("请访问: http://localhost:8088/");
    }
}
