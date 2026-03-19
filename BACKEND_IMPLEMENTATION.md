# MyBlog Backend 实现文档

> 技术栈: Spring Boot + MySQL  
> 设计日期: 2026-03-19  
> 版本: v1.0

---

## 一、数据库设计

### 1.1 数据库创建

```sql
-- 创建数据库
CREATE DATABASE IF NOT EXISTS myblog CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE myblog;
```

### 1.2 Posts 表结构

```sql
-- 文章表
CREATE TABLE IF NOT EXISTS posts (
    -- 主键
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章ID',
    
    -- 基础信息
    title VARCHAR(255) NOT NULL COMMENT '文章标题',
    slug VARCHAR(255) NOT NULL COMMENT 'URL别名/文章标识',
    content LONGTEXT COMMENT '文章内容 (Markdown格式)',
    description VARCHAR(500) DEFAULT '' COMMENT '文章摘要',
    
    -- 封面图片
    cover_image VARCHAR(500) DEFAULT '' COMMENT '封面图片URL或路径',
    
    -- 分类与标签
    category VARCHAR(100) DEFAULT '' COMMENT '分类',
    tags JSON COMMENT '标签数组 (JSON格式存储)',
    
    -- 状态控制
    draft TINYINT(1) DEFAULT 0 COMMENT '是否为草稿: 0-否, 1-是',
    pinned TINYINT(1) DEFAULT 0 COMMENT '是否置顶: 0-否, 1-是',
    priority INT DEFAULT 0 COMMENT '置顶优先级 (数值越小优先级越高)',
    
    -- 加密功能
    encrypted TINYINT(1) DEFAULT 0 COMMENT '是否加密: 0-否, 1-是',
    password_hash VARCHAR(255) DEFAULT '' COMMENT '文章密码哈希 (BCrypt)',
    
    -- 元数据
    author VARCHAR(100) DEFAULT '' COMMENT '作者',
    lang VARCHAR(10) DEFAULT 'zh_CN' COMMENT '语言代码',
    license_name VARCHAR(100) DEFAULT '' COMMENT '许可证名称',
    license_url VARCHAR(500) DEFAULT '' COMMENT '许可证链接',
    source_link VARCHAR(500) DEFAULT '' COMMENT '原文链接',
    
    -- 评论控制
    comment_enabled TINYINT(1) DEFAULT 1 COMMENT '是否启用评论: 0-否, 1-是',
    
    -- 时间戳
    published_at DATETIME COMMENT '发布时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    
    -- 软删除
    deleted_at DATETIME DEFAULT NULL COMMENT '删除时间 (NULL表示未删除)',
    
    -- 约束
    PRIMARY KEY (id),
    UNIQUE KEY uk_slug (slug),
    KEY idx_published_at (published_at),
    KEY idx_category (category),
    KEY idx_draft (draft),
    KEY idx_pinned (pinned),
    KEY idx_deleted_at (deleted_at),
    FULLTEXT KEY ft_title_content (title, content)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章表';
```

### 1.3 系统配置表

```sql
-- 系统配置表 (存储管理员密码等)
CREATE TABLE IF NOT EXISTS system_config (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    config_key VARCHAR(100) NOT NULL COMMENT '配置键',
    config_value TEXT COMMENT '配置值',
    description VARCHAR(255) DEFAULT '' COMMENT '配置说明',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_config_key (config_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表';

-- 插入默认管理员密码 (明文: admin123, 实际使用时应使用BCrypt加密)
-- 示例密码哈希: $2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EO
INSERT INTO system_config (config_key, config_value, description) VALUES
('admin.password.hash', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EO', '管理员密码哈希'),
('site.title', 'MyBlog', '网站标题'),
('site.subtitle', 'A simple blog', '网站副标题'),
('site.theme.hue', '60', '主题色相 (0-360)'),
('site.page.size', '8', '每页文章数');
```

### 1.4 初始化数据

```sql
-- 插入示例文章
INSERT INTO posts (title, slug, content, description, category, tags, draft, pinned, author, lang, published_at) VALUES
(
    '欢迎使用 MyBlog',
    'welcome-to-myblog',
    '# 欢迎使用 MyBlog\n\n这是一篇示例文章，展示博客的基本功能。\n\n## 特性\n\n- 简洁的设计\n- Markdown 支持\n- 响应式布局\n\n感谢使用！',
    '这是一篇示例文章，介绍 MyBlog 的基本功能。',
    '默认分类',
    '["示例", "介绍"]',
    0,
    1,
    '管理员',
    'zh_CN',
    NOW()
);
```

---

## 二、API 契约

### 2.1 通用响应格式

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

| 字段 | 类型 | 说明 |
|------|------|------|
| code | Integer | HTTP 状态码 |
| message | String | 响应消息 |
| data | Object/Array | 响应数据 |

### 2.2 公开 API (无需认证)

#### 2.2.1 获取文章列表

```
GET /api/posts
```

**请求参数:**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| page | Integer | 否 | 页码，默认 1 |
| size | Integer | 否 | 每页数量，默认 8 |
| category | String | 否 | 分类筛选 |
| tag | String | 否 | 标签筛选 |
| search | String | 否 | 搜索关键词 |

**响应示例:**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "total": 100,
    "page": 1,
    "size": 8,
    "pages": 13,
    "list": [
      {
        "id": 1,
        "title": "欢迎使用 MyBlog",
        "slug": "welcome-to-myblog",
        "description": "这是一篇示例文章...",
        "coverImage": "/assets/cover/welcome.jpg",
        "category": "默认分类",
        "tags": ["示例", "介绍"],
        "pinned": true,
        "priority": 0,
        "author": "管理员",
        "publishedAt": "2025-01-20T10:00:00",
        "updatedAt": "2025-01-20T10:00:00"
      }
    ]
  }
}
```

#### 2.2.2 获取文章详情

```
GET /api/posts/{id}
```

**路径参数:**

| 参数 | 类型 | 说明 |
|------|------|------|
| id | Long | 文章ID |

**响应示例 (普通文章):**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "title": "欢迎使用 MyBlog",
    "slug": "welcome-to-myblog",
    "content": "# 欢迎使用 MyBlog\n\n这是一篇示例文章...",
    "description": "这是一篇示例文章...",
    "coverImage": "/assets/cover/welcome.jpg",
    "category": "默认分类",
    "tags": ["示例", "介绍"],
    "draft": false,
    "pinned": true,
    "priority": 0,
    "encrypted": false,
    "author": "管理员",
    "lang": "zh_CN",
    "licenseName": "CC BY-NC-SA 4.0",
    "licenseUrl": "https://creativecommons.org/licenses/by-nc-sa/4.0/",
    "sourceLink": "",
    "commentEnabled": true,
    "publishedAt": "2025-01-20T10:00:00",
    "updatedAt": "2025-01-20T10:00:00",
    "prevPost": {
      "id": 0,
      "title": "",
      "slug": ""
    },
    "nextPost": {
      "id": 2,
      "title": "Markdown 教程",
      "slug": "markdown-tutorial"
    }
  }
}
```

**响应示例 (加密文章):**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 2,
    "title": "加密文章示例",
    "slug": "encrypted-post",
    "description": "这是一篇加密文章",
    "coverImage": "/assets/cover/encrypted.jpg",
    "category": "私密",
    "tags": ["加密"],
    "encrypted": true,
    "content": null,
    "author": "管理员",
    "publishedAt": "2025-01-20T10:00:00"
  }
}
```

#### 2.2.3 验证加密文章密码

```
POST /api/posts/{id}/verify
```

**请求体:**

```json
{
  "password": "article123"
}
```

**响应示例 (验证成功):**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "content": "# 加密文章内容\n\n这是解密后的内容..."
  }
}
```

**响应示例 (验证失败):**

```json
{
  "code": 403,
  "message": "密码错误",
  "data": null
}
```

#### 2.2.4 获取分类列表

```
GET /api/categories
```

**响应示例:**

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "name": "默认分类",
      "count": 10
    },
    {
      "name": "技术",
      "count": 5
    }
  ]
}
```

#### 2.2.5 获取标签列表

```
GET /api/tags
```

**响应示例:**

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "name": "示例",
      "count": 3
    },
    {
      "name": "介绍",
      "count": 2
    }
  ]
}
```

---

### 2.3 管理 API (需要认证)

**认证方式:** 请求头中携带 `X-Admin-Password`

```
X-Admin-Password: admin123
```

#### 2.3.1 创建文章

```
POST /api/admin/posts
```

**请求体:**

```json
{
  "title": "新文章标题",
  "slug": "new-post-slug",
  "content": "# 文章内容\n\nMarkdown 格式...",
  "description": "文章摘要",
  "coverImage": "/assets/cover/new.jpg",
  "category": "技术",
  "tags": ["Java", "Spring"],
  "draft": false,
  "pinned": false,
  "priority": 0,
  "encrypted": false,
  "password": "",
  "author": "管理员",
  "lang": "zh_CN",
  "licenseName": "CC BY-NC-SA 4.0",
  "licenseUrl": "https://creativecommons.org/licenses/by-nc-sa/4.0/",
  "sourceLink": "",
  "commentEnabled": true,
  "publishedAt": "2025-01-20T10:00:00"
}
```

**响应示例:**

```json
{
  "code": 201,
  "message": "创建成功",
  "data": {
    "id": 3,
    "title": "新文章标题",
    "slug": "new-post-slug",
    "createdAt": "2025-01-20T10:00:00"
  }
}
```

#### 2.3.2 更新文章

```
PUT /api/admin/posts/{id}
```

**请求体:** (与创建相同，全量更新)

```json
{
  "title": "更新后的标题",
  "slug": "updated-slug",
  "content": "# 更新后的内容...",
  "description": "更新后的摘要",
  "coverImage": "/assets/cover/updated.jpg",
  "category": "技术",
  "tags": ["Java", "Spring", "更新"],
  "draft": false,
  "pinned": true,
  "priority": 1,
  "encrypted": false,
  "password": "",
  "author": "管理员",
  "lang": "zh_CN",
  "licenseName": "CC BY-NC-SA 4.0",
  "licenseUrl": "https://creativecommons.org/licenses/by-nc-sa/4.0/",
  "sourceLink": "",
  "commentEnabled": true,
  "publishedAt": "2025-01-20T10:00:00"
}
```

**响应示例:**

```json
{
  "code": 200,
  "message": "更新成功",
  "data": {
    "id": 3,
    "title": "更新后的标题",
    "updatedAt": "2025-01-20T12:00:00"
  }
}
```

#### 2.3.3 删除文章 (软删除)

```
DELETE /api/admin/posts/{id}
```

**响应示例:**

```json
{
  "code": 200,
  "message": "删除成功",
  "data": null
}
```

#### 2.3.4 获取所有文章 (包含草稿和已删除)

```
GET /api/admin/posts
```

**请求参数:**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| page | Integer | 否 | 页码，默认 1 |
| size | Integer | 否 | 每页数量，默认 20 |
| includeDeleted | Boolean | 否 | 是否包含已删除，默认 false |

**响应示例:**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "total": 100,
    "page": 1,
    "size": 20,
    "list": [
      {
        "id": 1,
        "title": "欢迎使用 MyBlog",
        "slug": "welcome-to-myblog",
        "draft": false,
        "deletedAt": null,
        "publishedAt": "2025-01-20T10:00:00",
        "createdAt": "2025-01-20T10:00:00"
      }
    ]
  }
}
```

#### 2.3.5 验证管理员密码

```
POST /api/admin/verify
```

**请求体:**

```json
{
  "password": "admin123"
}
```

**响应示例:**

```json
{
  "code": 200,
  "message": "验证成功",
  "data": {
    "valid": true
  }
}
```

---

## 三、Java 拦截器实现

### 3.1 管理员认证拦截器

```java
package com.myblog.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myblog.entity.Result;
import com.myblog.service.SystemConfigService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;

/**
 * 管理员认证拦截器
 * 校验 Header 中的 X-Admin-Password
 */
@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    private static final String ADMIN_PASSWORD_HEADER = "X-Admin-Password";
    private static final String ADMIN_PASSWORD_KEY = "admin.password.hash";

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, 
                            HttpServletResponse response, 
                            Object handler) throws Exception {
        
        // 1. 从请求头获取密码
        String providedPassword = request.getHeader(ADMIN_PASSWORD_HEADER);
        
        // 2. 验证密码是否存在
        if (providedPassword == null || providedPassword.trim().isEmpty()) {
            writeErrorResponse(response, 401, "未提供管理员密码");
            return false;
        }
        
        // 3. 从数据库获取存储的密码哈希
        String storedHash = systemConfigService.getConfigValue(ADMIN_PASSWORD_KEY);
        
        if (storedHash == null || storedHash.isEmpty()) {
            writeErrorResponse(response, 500, "系统配置错误：未设置管理员密码");
            return false;
        }
        
        // 4. 使用 BCrypt 验证密码
        boolean isValid = BCrypt.checkpw(providedPassword, storedHash);
        
        if (!isValid) {
            writeErrorResponse(response, 403, "管理员密码错误");
            return false;
        }
        
        // 5. 验证通过，继续处理请求
        return true;
    }

    /**
     * 写入错误响应
     */
    private void writeErrorResponse(HttpServletResponse response, 
                                   int code, 
                                   String message) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(code);
        
        Result<?> result = Result.error(code, message);
        String json = objectMapper.writeValueAsString(result);
        
        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    }
}
```

### 3.2 拦截器配置

```java
package com.myblog.config;

import com.myblog.interceptor.AdminAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AdminAuthInterceptor adminAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminAuthInterceptor)
                // 拦截所有 /api/admin/** 路径
                .addPathPatterns("/api/admin/**")
                // 排除验证接口 (可选，根据需求决定)
                .excludePathPatterns("/api/admin/verify");
    }
}
```

### 3.3 统一响应结果类

```java
package com.myblog.entity;

import lombok.Data;

/**
 * 统一响应结果
 */
@Data
public class Result<T> {

    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(null);
        return result;
    }

    public static <T> Result<T> error(String message) {
        return error(500, message);
    }
}
```

### 3.4 系统配置服务接口

```java
package com.myblog.service;

/**
 * 系统配置服务
 */
public interface SystemConfigService {
    
    /**
     * 根据 key 获取配置值
     */
    String getConfigValue(String key);
    
    /**
     * 更新配置值
     */
    void updateConfig(String key, String value);
    
    /**
     * 验证管理员密码
     */
    boolean verifyAdminPassword(String password);
}
```

### 3.5 系统配置服务实现

```java
package com.myblog.service.impl;

import com.myblog.entity.SystemConfig;
import com.myblog.mapper.SystemConfigMapper;
import com.myblog.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class SystemConfigServiceImpl implements SystemConfigService {

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Override
    public String getConfigValue(String key) {
        SystemConfig config = systemConfigMapper.selectByKey(key);
        return config != null ? config.getConfigValue() : null;
    }

    @Override
    public void updateConfig(String key, String value) {
        SystemConfig config = systemConfigMapper.selectByKey(key);
        if (config != null) {
            config.setConfigValue(value);
            systemConfigMapper.update(config);
        } else {
            config = new SystemConfig();
            config.setConfigKey(key);
            config.setConfigValue(value);
            systemConfigMapper.insert(config);
        }
    }

    @Override
    public boolean verifyAdminPassword(String password) {
        String storedHash = getConfigValue("admin.password.hash");
        if (storedHash == null || storedHash.isEmpty()) {
            return false;
        }
        return BCrypt.checkpw(password, storedHash);
    }
}
```

---

## 四、项目结构建议

```
myblog-backend/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/myblog/
│       │       ├── MyBlogApplication.java
│       │       ├── config/
│       │       │   ├── WebConfig.java
│       │       │   └── MyBatisConfig.java
│       │       ├── controller/
│       │       │   ├── PostController.java
│       │       │   └── AdminController.java
│       │       ├── entity/
│       │       │   ├── Post.java
│       │       │   ├── SystemConfig.java
│       │       │   └── Result.java
│       │       ├── mapper/
│       │       │   ├── PostMapper.java
│       │       │   └── SystemConfigMapper.java
│       │       ├── service/
│       │       │   ├── PostService.java
│       │       │   ├── SystemConfigService.java
│       │       │   └── impl/
│       │       │       ├── PostServiceImpl.java
│       │       │       └── SystemConfigServiceImpl.java
│       │       ├── interceptor/
│       │       │   └── AdminAuthInterceptor.java
│       │       └── dto/
│       │           ├── PostListDTO.java
│       │           ├── PostDetailDTO.java
│       │           ├── PostCreateRequest.java
│       │           ├── PostUpdateRequest.java
│       │           └── PasswordVerifyRequest.java
│       └── resources/
│           ├── application.yml
│           ├── application-dev.yml
│           ├── application-prod.yml
│           └── mapper/
│               ├── PostMapper.xml
│               └── SystemConfigMapper.xml
├── pom.xml
└── BACKEND_IMPLEMENTATION.md
```

---

## 五、Maven 依赖建议

```xml
<dependencies>
    <!-- Spring Boot Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- MySQL 驱动 -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- MyBatis -->
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>3.0.3</version>
    </dependency>

    <!-- BCrypt 密码加密 -->
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-crypto</artifactId>
    </dependency>

    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <!-- 参数校验 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
</dependencies>
```

---

## 六、application.yml 配置示例

```yaml
server:
  port: 8080

spring:
  application:
    name: myblog-backend
  
  datasource:
    url: jdbc:mysql://localhost:3306/myblog?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
    serialization:
      write-dates-as-timestamps: false

mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.myblog.entity
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl

# 日志配置
logging:
  level:
    com.myblog.mapper: debug
```

---

*文档生成完毕，请审阅后确认实现方案。*
