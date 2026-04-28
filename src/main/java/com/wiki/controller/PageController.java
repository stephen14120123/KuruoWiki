package com.wiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 专门用于处理页面跳转的控制器
 * 注意：这里用的是 @Controller 而不是 @RestController
 * 因为我们需要返回的是页面的路径，而不是 JSON 数据
 */
@Controller
public class PageController {
    @GetMapping("/")
    public String index() {
        // 使用 forward: 前缀，告诉 Spring 去 static 目录下找 index.html
        return "forward:/index.html";
    }
}
