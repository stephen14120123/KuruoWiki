package com.wiki.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import com.wiki.entity.StrategyGuide;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 实验三核心任务：使用 Spring MVC 替代传统的 Servlet
 * (目前仅保留少量早期的独立实验测试接口，废弃的级联测试已清理)
 */
@RestController
@RequestMapping("/api") // 统一接口前缀
public class WikiController {

    // ==========================================
    // 实验八验收测试接口：使用 @ModelAttribute 接收请求参数
    // ==========================================
    @PostMapping("/test/modelAttribute")
    public Object testModelAttribute(@ModelAttribute StrategyGuide guide) {
        System.out.println("【实验八测试】成功通过 @ModelAttribute 接收到攻略标题：" + guide.getTitle());
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "实验八测试成功！接收到的标题是: " + guide.getTitle());
        return result;
    }

    // ==========================================
    // 实验十一验收测试接口：数据验证
    // ==========================================
    @PostMapping("/test/validation")
    public String testValidation(@Validated @RequestBody StrategyGuide guide) {
        // @Validated 注解会触发实体类里的 @NotBlank 规则。
        return "数据验证通过！收到的攻略标题是：" + guide.getTitle();
    }
}