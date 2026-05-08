# 角色详情页面攻略加载修复

## 问题分析

后台管理界面显示有攻略记录，但前端角色详情页面（detail.html）无法显示这些攻略。

### 根本原因

1. **拦截器保护过度** - `/api/strategies` 的 GET 请求被拦截器保护，需要登录
2. **前端未携带 Token** - 前端在加载攻略列表时没有在请求头中携带 Token
3. **逻辑不合理** - 查看攻略不需要登录，只有发布、编辑、删除才需要登录

## 修复方案

### 修改 LoginInterceptor

在拦截器中添加特殊处理：**GET 请求到 `/api/strategies` 不需要登录**

```java
// GET 请求到 /api/strategies 不需要登录（只是查看攻略）
if ("GET".equalsIgnoreCase(request.getMethod()) && request.getRequestURI().startsWith("/api/strategies")) {
    return true;
}
```

这样做的好处：
- 任何人都可以查看攻略（GET 请求）
- 只有登录用户才能发布、编辑、删除攻略（POST/PUT/DELETE 请求）

## 修改文件

1. **src/main/java/com/wiki/interceptor/LoginInterceptor.java**
   - 添加 GET 请求特殊处理逻辑

## 前端流程

### 加载攻略列表（无需登录）
```javascript
fetch(`/api/strategies?characterId=${charId}`)
    .then(res => res.json())
    .then(data => {
        // 显示攻略列表
    });
```

### 发布攻略（需要登录）
```javascript
fetch('/api/strategies', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + token  // 必须携带 Token
    },
    body: JSON.stringify(data)
});
```

### 删除攻略（需要登录）
```javascript
fetch(`/api/strategies/${id}`, {
    method: 'DELETE',
    headers: {
        'Authorization': 'Bearer ' + token  // 必须携带 Token
    }
});
```

## 权限控制总结

| 操作 | 方法 | 需要登录 | 说明 |
|------|------|---------|------|
| 查看攻略 | GET | ❌ 否 | 任何人都可以查看 |
| 发布攻略 | POST | ✅ 是 | 需要登录用户 |
| 编辑攻略 | PUT | ✅ 是 | 只能编辑自己的攻略 |
| 删除攻略 | DELETE | ✅ 是 | 只能删除自己的攻略 |

## 测试步骤

1. **不登录状态下**
   - 访问角色详情页面
   - 应该能看到该角色的所有攻略

2. **登录状态下**
   - 访问角色详情页面
   - 应该能看到攻略列表
   - 应该能发布新攻略
   - 应该能删除自己的攻略

3. **权限验证**
   - 用户A发布的攻略，用户B无法删除
   - 应该显示"越权操作"错误

## 拦截器配置总结

```
请求 → 检查是否为 OPTIONS 预检请求
  ↓
是 → 放行
  ↓
否 → 检查是否为 GET /api/strategies
  ↓
是 → 放行（无需登录）
  ↓
否 → 检查 Authorization 头中的 Token
  ↓
Token 有效 → 放行，存储 userId 和 role
Token 无效或不存在 → 返回 401 错误
```

## 注意事项

- GET 请求到 `/api/strategies` 不需要登录，但如果提供了有效的 Token，会自动提取用户信息
- POST/PUT/DELETE 请求必须携带有效的 Token
- 删除攻略时，后端会检查是否为攻略作者，防止越权操作
