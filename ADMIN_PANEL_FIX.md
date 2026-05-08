# 管理员后台面板修复总结

## 问题分析

前端登录管理员账号后无法跳转到后台管理界面，原因是：

1. **缺少管理员接口** - 前端调用的 `/api/admin/*` 接口不存在
2. **缺少公开接口** - 前端调用的 `/api/weapon/list` 和 `/api/echo/list` 接口不存在
3. **拦截器配置不完整** - 没有正确保护 `/api/admin` 接口

## 修复内容

### 1. 创建 AdminController（新增）
**文件**: `src/main/java/com/wiki/controller/AdminController.java`

提供以下管理接口（需要管理员权限）：

#### 用户管理
- `GET /api/admin/users` - 获取所有用户列表
- `POST /api/admin/user/delete` - 删除用户

#### 攻略管理
- `GET /api/admin/strategies` - 获取所有攻略列表
- `POST /api/admin/strategy/delete` - 删除攻略

#### 角色管理
- `POST /api/admin/character` - 新增/编辑/删除角色
  - 参数: `action=add|update|delete`

#### 武器管理
- `POST /api/admin/weapon` - 新增/编辑/删除武器
  - 参数: `action=add|update|delete`

#### 声骸管理
- `POST /api/admin/echo` - 新增/编辑/删除声骸
  - 参数: `action=add|update|delete`

### 2. 创建 PublicApiController（新增）
**文件**: `src/main/java/com/wiki/controller/PublicApiController.java`

提供以下公开接口（不需要登录）：

- `GET /api/weapon/list` - 获取武器列表
- `GET /api/echo/list` - 获取声骸列表

### 3. 更新 StrategyService
**文件**: `src/main/java/com/wiki/service/StrategyService.java`

添加方法：
- `getAllStrategies()` - 获取所有攻略（用于管理员查看）

### 4. 更新 StrategyMapper
**文件**: `src/main/java/com/wiki/dao/StrategyMapper.java`

添加方法：
- `getAllStrategies()` - 查询所有攻略

### 5. 更新 StrategyMapper.xml
**文件**: `src/main/resources/mapper/StrategyMapper.xml`

添加 SQL：
```xml
<select id="getAllStrategies" resultType="StrategyGuide">
    SELECT s.*, u.nickname as authorName, c.name as characterName
    FROM strategy_guide s
             LEFT JOIN user u ON s.user_id = u.id
             LEFT JOIN character_info c ON s.character_id = c.id
    ORDER BY s.create_time DESC
</select>
```

### 6. 更新 WebConfig
**文件**: `src/main/java/com/wiki/config/WebConfig.java`

完善拦截器配置：
- 添加 `/api/admin/**` 到拦截器保护列表
- 放行 `/api/weapon/list` 和 `/api/echo/list`

## 权限验证流程

```
前端请求 /api/admin/* 
    ↓
LoginInterceptor 验证 Token
    ↓
RoleCheckAspect 检查 @RequiresRole(1) 注解
    ↓
验证用户 role 是否为 1（管理员）
    ↓
✓ 是 → 执行业务逻辑
✗ 否 → 返回 "权限不足" 错误
```

## 前端流程

1. 用户登录 → 获取 JWT Token
2. 访问 `/admin.html` → 前端检查用户权限
3. 调用 `/api/user/info` 验证用户身份和角色
4. 如果 `role === 1` → 显示管理后台
5. 否则 → 重定向到首页

## 测试步骤

1. **登录管理员账号**
   - 用户名: admin
   - 密码: admin123
   - 角色: 1（管理员）

2. **验证后台功能**
   - 用户管理 - 查看和删除用户
   - 攻略管理 - 查看和删除攻略
   - 角色管理 - 新增/编辑/删除角色
   - 武器管理 - 新增/编辑/删除武器
   - 声骸管理 - 新增/编辑/删除声骸

3. **验证权限控制**
   - 用普通用户账号尝试访问 `/admin.html` → 应重定向到首页
   - 用管理员账号访问 → 应正常显示后台

## 新增文件

1. `src/main/java/com/wiki/controller/AdminController.java` - 管理员接口
2. `src/main/java/com/wiki/controller/PublicApiController.java` - 公开接口

## 修改文件

1. `src/main/java/com/wiki/config/WebConfig.java` - 拦截器配置
2. `src/main/java/com/wiki/service/StrategyService.java` - 添加查询所有攻略方法
3. `src/main/java/com/wiki/dao/StrategyMapper.java` - 添加查询所有攻略方法
4. `src/main/resources/mapper/StrategyMapper.xml` - 添加查询所有攻略 SQL

## 注意事项

- 所有管理接口都需要 `@RequiresRole(1)` 注解保护
- 前端必须在请求头中携带 `Authorization: Bearer <token>`
- 管理员账号的 `role` 字段必须为 `1`
