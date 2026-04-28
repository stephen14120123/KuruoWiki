package com.wiki.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect // 告诉 Spring 这是一个切面类 (也就是我说的"保安")
@Component // 交给 Spring 管理
public class LogAspect {

    // @Before 意思是：在执行目标方法之前，先执行下面这个方法
    // 这里的表达式意思是：拦截 com.wiki.controller 包下的所有方法
    @Before("execution(* com.wiki.controller.*.*(..))")
    public void logBefore() {
        System.out.println("【AOP日志记录】 -> 接收到前端请求，正在处理...");
    }
}