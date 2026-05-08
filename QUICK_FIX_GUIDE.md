# 快速修复指南

## 问题1：角色数据没有显示

### 原因
后端Spring Boot服务没有运行，导致无法获取角色数据。

### 解决方案
启动后端服务：

**方式1：使用IDE**
1. 在IDEA中找到 `src/main/java/com/wiki/WikiApplication.java`
2. 右键点击 → Run 'WikiApplication'

**方式2：使用Maven命令**
```bash
mvn spring-boot:run
```

**方式3：使用已编译的jar**
```bash
java -jar target/KuroWiki-0.0.1-SNAPSHOT.jar
```

### 验证服务是否启动
打开浏览器访问：
- http://localhost:9088/api/characters

如果看到JSON数据，说明服务启动成功。

---

## 问题2：筛选栏样式优化

### 已完成的优化

1. **缩短选项文字**
   - ✦ 全部属性 → 全部属性
   - ✦ 全部星级 → 全部星级  
   - 5星 共鸣者 → 5星
   - 4星 共鸣者 → 4星

2. **调整样式**
   - 减小padding: 12px → 10px
   - 减小border-radius: 10px → 8px
   - 添加 padding-right 让下拉箭头有空间

3. **移除不必要的宽度限制**
   - select框现在会根据内容自动调整宽度

---

## 当前文件状态

### 已创建/更新的文件
- ✅ `src/main/resources/static/js/common.js` - 公共JS库
- ✅ `src/main/resources/static/css/common.css` - 公共CSS样式
- ✅ `src/main/resources/static/index.html` - 优化后的首页

### 优化内容
1. **安全性**
   - 修复XSS注入风险
   - 强化Token管理

2. **性能**
   - mousemove事件节流
   - 优化DOM操作

3. **代码质量**
   - 抽离公共函数
   - 统一样式规范

---

## 下一步操作

1. **启动后端服务**（必须）
   ```bash
   mvn spring-boot:run
   ```

2. **刷新浏览器**
   - 访问 http://localhost:9088/index.html
   - 按 Ctrl+F5 强制刷新

3. **检查控制台**
   - 按 F12 打开开发者工具
   - 查看 Console 标签是否有错误
   - 查看 Network 标签检查API请求

---

## 预期效果

启动服务后，你应该看到：
- ✅ 角色卡片正常显示
- ✅ 筛选栏更紧凑美观
- ✅ 搜索和筛选功能正常
- ✅ 鼠标悬停有流光效果

---

## 如果还有问题

### 检查清单
- [ ] 后端服务是否在9088端口运行
- [ ] 数据库是否正常连接
- [ ] 浏览器控制台是否有JavaScript错误
- [ ] Network标签中API请求是否返回200

### 常见错误
1. **端口被占用**
   - 修改 `application.properties` 中的端口号

2. **数据库连接失败**
   - 检查MySQL是否运行
   - 验证数据库配置

3. **404错误**
   - 确认访问的URL正确
   - 检查静态资源路径

---

**最后更新：** 2026-05-08  
**状态：** 等待后端服务启动
