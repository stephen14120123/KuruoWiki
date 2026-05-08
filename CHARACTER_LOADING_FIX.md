# 后台管理界面数据加载修复

## 问题分析

后台管理界面无法加载角色、武器、声骸数据，原因是：

1. **数据格式不匹配** - 前端期望直接的数组，但后端返回的是 `Result` 对象
2. **接口路径不一致** - 前端调用的接口路径与后端提供的接口不一致

### 具体问题

前端代码：
```javascript
fetch('/api/characters', {
    headers: { 'Authorization': 'Bearer ' + getToken() }
}).then(res => res.json()).then(data => {
    data.forEach(c => { ... })  // 期望 data 是数组
});
```

后端返回格式：
```json
{
    "code": 200,
    "message": "操作成功",
    "data": [...]  // 实际返回的是 Result 对象
}
```

## 修复方案

### 1. 在 AdminController 中添加新接口

添加以下接口，直接返回数组（不包装在 Result 对象中）：

```java
@GetMapping("/characters")
@RequiresRole(1)
public List<CharacterInfo> getCharacters() {
    return characterService.getAllCharacters();
}

@GetMapping("/weapons")
@RequiresRole(1)
public List<WeaponInfo> getWeapons() {
    return weaponService.getAllWeapons();
}

@GetMapping("/echoes")
@RequiresRole(1)
public List<EchoInfo> getEchoes() {
    return echoService.getAllEchoes();
}
```

### 2. 修改前端调用的接口路径

修改 `admin.html` 中的三个加载函数：

- `loadCharacters()` - 从 `/api/characters` 改为 `/api/admin/characters`
- `loadWeapons()` - 从 `/api/weapon/list` 改为 `/api/admin/weapons`
- `loadEchoes()` - 从 `/api/echo/list` 改为 `/api/admin/echoes`

## 修改文件

1. **src/main/java/com/wiki/controller/AdminController.java**
   - 添加 `getCharacters()` 方法
   - 添加 `getWeapons()` 方法
   - 添加 `getEchoes()` 方法

2. **src/main/resources/static/admin.html**
   - 修改 `loadCharacters()` 函数的 fetch URL
   - 修改 `loadWeapons()` 函数的 fetch URL
   - 修改 `loadEchoes()` 函数的 fetch URL

## 接口总结

### 管理员接口（需要 @RequiresRole(1)）

| 方法 | 路径 | 返回格式 | 说明 |
|------|------|---------|------|
| GET | `/api/admin/characters` | 数组 | 获取所有角色 |
| GET | `/api/admin/weapons` | 数组 | 获取所有武器 |
| GET | `/api/admin/echoes` | 数组 | 获取所有声骸 |
| GET | `/api/admin/users` | Result | 获取所有用户 |
| GET | `/api/admin/strategies` | Result | 获取所有攻略 |
| POST | `/api/admin/character` | Result | 新增/编辑/删除角色 |
| POST | `/api/admin/weapon` | Result | 新增/编辑/删除武器 |
| POST | `/api/admin/echo` | Result | 新增/编辑/删除声骸 |

### 公开接口（不需要登录）

| 方法 | 路径 | 返回格式 | 说明 |
|------|------|---------|------|
| GET | `/api/characters` | Result | 获取所有角色 |
| GET | `/api/weapons` | Result | 获取所有武器 |
| GET | `/api/echoes` | Result | 获取所有声骸 |
| GET | `/api/weapon/list` | 数组 | 获取武器列表 |
| GET | `/api/echo/list` | 数组 | 获取声骸列表 |

## 测试步骤

1. 登录管理员账号
2. 访问 `/admin.html`
3. 点击"角色图鉴管理" - 应该显示所有角色
4. 点击"武器图鉴管理" - 应该显示所有武器
5. 点击"声骸图鉴管理" - 应该显示所有声骸
6. 尝试新增、编辑、删除数据

## 注意事项

- 所有 `/api/admin/*` 接口都需要管理员权限（`@RequiresRole(1)`）
- 前端必须在请求头中携带有效的 JWT Token
- 数据加载接口返回直接的数组，便于前端处理
- 数据操作接口返回 Result 对象，包含操作状态信息
