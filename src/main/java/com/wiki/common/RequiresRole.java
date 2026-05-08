package com.wiki.common;

import java.lang.annotation.*;

/**
 * 权限验证注解：用于标记需要特定角色的接口
 * 0 - 普通玩家, 1 - 管理员
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresRole {
    int value() default 1; // 默认需要管理员权限
}
