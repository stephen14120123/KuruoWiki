package com.wiki.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 实验十三：全局统一异常处理
 * @RestControllerAdvice 相当于给所有的 Controller 请了一个“保镖”
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 只要系统抛出了 Exception（所有异常的爹），就会被这个方法拦截
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handleException(Exception e) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 500);
        // 不再给前端看可怕的英文报错堆栈，而是返回温柔的提示
        result.put("message", "【全局异常拦截生效】系统开小差了，报错原因: " + e.getMessage());
        return result;
    }
}