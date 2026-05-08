# 安全性修复总结

## 修复的问题

### 1. ✅ WebConfig 拦截器配置自相矛盾
**问题**：拦截器配置中既添加了 `/api/strategies/**` 又排除了它，导致拦截器形同虚设。
**修复**：
- 正确配置拦截器，保护所有需要登录的接口
- 放行登录接口和查询接口（GET 请求）
- 拦截所有增删改操作（POST/PUT/DELETE）

### 2. ✅ 缺少权限验证注解
**问题**：后台操作没有权限检查，任何人都可以修改数据。
**修复**：
- 创建 `@RequiresRole` 注解用于标记需要权限的方法
- 创建 `RoleCheckAspect` 切面进行权限验证
- 在所有后台操作上添加 `@RequiresRole(1)` 注解

### 3. ✅ CharacterController 缺少权限保护
**修复**：
- 在 `addCharacter()`、`updateCharacter()`、`deleteCharacter()` 上添加 `@RequiresRole(1)` 注解
- 只有管理员（role=1）才能执行这些操作

### 4. ✅ WeaponController 缺少权限保护
**修复**：
- 在 `addWeapon()`、`updateWeapon()`、`deleteWeapon()` 上添加 `@RequiresRole(1)` 注解

### 5. ✅ EchoController 缺少权限保护
**修复**：
- 在 `addEcho()`、`updateEcho()`、`deleteEcho()` 上添加 `@RequiresRole(1)` 注解

### 6. ✅ StrategyController 缺少编辑功能
**修复**：
- 添加 `update()` 方法支持编辑攻略
- 只允许攻略作者编辑自己的攻略
- 更新 StrategyMapper 和 XML 配置支持更新操作

### 7. ✅ LoginInterceptor 未存储用户角色
**修复**：
- 在拦截器中添加 `request.setAttribute("userRole", claims.get("role"))`
- 使权限验证切面能够获取用户角色信息

## 新增文件

1. **RequiresRole.java** - 权限验证注解
2. **RoleCheckAspect.java** - 权限验证切面

## 修改文件

1. **WebConfig.java** - 修复拦截器配置
2. **LoginInterceptor.java** - 添加角色存储
3. **CharacterController.java** - 添加权限保护
4. **WeaponController.java** - 添加权限保护
5. **EchoController.java** - 添加权限保护
6. **StrategyController.java** - 添加编辑功能和权限保护
7. **StrategyService.java** - 添加更新方法
8. **StrategyMapper.java** - 添加更新方法
9. **StrategyMapper.xml** - 添加更新 SQL

## 权限验证流程

```
请求 → LoginInterceptor 验证 Token
       ↓
   提取 userId 和 role 存入 Request
       ↓
   RoleCheckAspect 检查 @RequiresRole 注解
       ↓
   比较用户 role 是否满足要求
       ↓
   满足 → 执行业务逻辑
   不满足 → 返回 "权限不足" 错误
```

## 测试建议

1. **测试权限验证**：
   - 用普通用户账号尝试修改角色/武器/声骸 → 应返回权限不足错误
   - 用管理员账号修改 → 应成功

2. **测试攻略编辑**：
   - 用户A发布攻略后，用户B尝试编辑 → 应返回越权错误
   - 用户A编辑自己的攻略 → 应成功

3. **测试拦截器**：
   - 不带 Token 访问受保护接口 → 应返回 401 错误
   - 带有效 Token 访问 → 应正常处理

## 后续优化建议

1. **密码加密**：使用 BCrypt 加密存储密码
2. **Token 刷新**：实现 Token 刷新机制
3. **操作日志**：记录所有管理员操作
4. **CORS 配置**：添加跨域资源共享配置
5. **输入验证**：添加更多的数据验证规则
