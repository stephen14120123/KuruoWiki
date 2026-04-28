package com.wiki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exp13")
public class Exp13Controller {

    // 注入 Spring 帮我们准备好的“翻译官”
    @Autowired
    private MessageSource messageSource;

    // ==========================================
    // 1. 验收测试：统一异常处理
    // ==========================================
    @GetMapping("/error")
    public String testError() {
        // 故意制造一个“除以零”的数学错误，看看会不会被我们的 GlobalExceptionHandler 拦截
        int a = 1 / 0;
        return "这行代码永远不会执行，因为上面报错了";
    }

    // ==========================================
    // 2. 验收测试：国际化 (i18n)
    // ==========================================
    @GetMapping("/i18n")
    public String testI18n() {
        // 让翻译官去词典里找 "info.welcome" 这句话。
        // 它会根据你浏览器的语言设置，自动决定去读中文词典还是英文词典！
        return messageSource.getMessage("info.welcome", null, LocaleContextHolder.getLocale());
    }
}