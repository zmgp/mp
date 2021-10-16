## 第三方工具包

### hutool

```uast
官网： https://www.hutool.cn/docs/#/
```

### 热部署

```uast
开启步骤
1. file
2. setting
3. Build...
4. compile
5. 选上 Build Project autom...
```


### swagger

```uast
地址： http://localhost:8083/swagger-ui.html
```


### 数据库表

#### 权限表

```mysql
USE `mp`;
DROP TABLE IF EXISTS `authority`;

CREATE TABLE `authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `authority` int(2) NOT NULL COMMENT '普通用户0，管理员1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```


#### 用户表

```mysql
DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

#### SpringBoot集成JWT实现token验证
https://www.jianshu.com/p/e88d3f8151db