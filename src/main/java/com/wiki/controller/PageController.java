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

    /**
     * 当用户访问根路径 ("/") 时，自动转发到 index.html 页面
     * @return "index" 会被 Spring Boot 的视图解析器找到并返回 /static/index.html
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
