# Spring Boot JPA Demo

这是一个基于 Spring Boot 和 JPA 的简单演示项目，展示了如何使用 Spring Boot 创建 RESTful API 并进行数据库操作。

## 技术栈

- **Spring Boot 2.3.1**
- **Spring Data JPA**
- **MySQL**
- **Maven**
- **Java 1.8**

## 项目结构

```
springboot_jpa/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── springboot_jpa/
│   │   │               ├── SpringbootJpaApplication.java    # 主启动类
│   │   │               ├── entity/
│   │   │               │   └── User.java                    # 用户实体类
│   │   │               ├── dao/
│   │   │               │   └── UserDao.java                 # 数据访问层
│   │   │               ├── service/
│   │   │               │   └── UserService.java            # 业务逻辑层
│   │   │               └── controller/
│   │   │                   └── UserController.java         # 控制器层
│   │   └── resources/
│   │       └── application.yml                            # 配置文件
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── springboot_jpa/
│                       └── SpringbootJpaApplicationTests.java
├── pom.xml                                               # Maven 依赖管理
└── README.md                                             # 项目说明文档
```

## 功能特性

- 用户信息的增删改查操作
- RESTful API 设计
- JPA 数据持久化
- MySQL 数据库集成

## API 接口

### 获取所有用户
```
GET /user
```

### 根据ID获取用户
```
GET /user/{id}
```

## 数据库配置

项目使用 MySQL 数据库，配置信息位于 `src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://数据库地址:端口/数据库名?characterEncoding=utf8&serverTimezone=Hongkong
    username: 用户名
    password: 密码
    driver-class-name: com.mysql.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
```

## 运行项目

1. 确保已安装 Java 1.8 和 Maven
2. 配置 MySQL 数据库连接信息
3. 运行项目：
   ```bash
   mvn spring-boot:run
   ```
4. 或直接运行 `SpringbootJpaApplication.java` 主类

## 实体类说明

### User 实体
- `userId`: 用户ID（主键）
- `username`: 用户名
- `password`: 密码

## 项目特点

1. **分层架构**：采用经典的 MVC 架构模式
2. **RESTful 设计**：遵循 REST 风格的 API 设计规范
3. **JPA 集成**：使用 Spring Data JPA 简化数据库操作
4. **自动建表**：通过 `ddl-auto: update` 自动管理数据库表结构

## 开发环境

- IDE: IntelliJ IDEA / Eclipse
- 构建工具: Maven
- 数据库: MySQL 5.7+
- Java: JDK 1.8+
