# 发布订阅

## 依赖

```uast
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
    <version>2.2.10.RELEASE</version>
</dependency>

<dependency>
    <groupId>com.maxmind.geoip2</groupId>
    <artifactId>geoip2</artifactId>
    <version>2.12.0</version>
</dependency>
```

## 实现原理

```uast
基于 webSocket + redis 实现的信息的推送
```

## 功能

* 单点推送
* 广播
* 获取在线人数
* 获得在线人信息
* 获得曾在线人的信息
* 将IP地址转化为城市地址

## 简介

```
实现的仅为 webSocket 的简单模板， 具体的功能开发尚未实现，目送推送目前仅为文本格式，暂不支持图片及表情
```

## 测试

```uast
可以基于 WebSocketController 测试相关功能

本程序测试url：（定义在 WebSocketEndpoint）
    http://127.0.0.1:8083/mp/connect/{identifier}
    
    {identifier} : 为用户名

webSocket连接的测试网址：
    http://coolaf.com/zh/tool/chattest
```

