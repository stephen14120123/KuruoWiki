package com.wiki.aspect;

import com.wiki.common.RequiresRole;
import com.wiki.common.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限验证切面：检查用户是否有权限执行操作
 */
@Aspect
@Component
public class RoleCheckAspect {

    @Around("@annotation(requiresRole)")
    public Object checkRole(ProceedingJoinPoint joinPoint, RequiresRole requiresRole) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return Result.error("无法获取请求信息");
        }

        HttpServletRequest request = attributes.getRequest();
        Integer userRole = (Integer) request.getAttribute("userRole");

        if (userRole == null) {
            return Result.error("用户信息不存在，请重新登录");
        }

        // 检查用户角色是否满足要求
        if (userRole < requiresRole.value()) {
            return Result.error("权限不足：只有管理员才能执行此操作");
        }

        return joinPoint.proceed();
    }
}
