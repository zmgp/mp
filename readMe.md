# MP 项目

## 功能简介

* 限流
    * 根据IP进行API限流
    * 全局对请求进行限流
    
* 权限管理
    * 用户权限管理
    
* 布隆过滤器
    * 根据 hutool + redis 实现简易布隆过滤器
    
* webSocket
    * 根据 websocket + redis实现
   
* 定时任务
    * 定时统计在线人数
    
* 监听在线用户信息
    * ip地址与城市信息
    * 请求次数
    * 上线时间
    * 下线时间
    * ...
    
## 项目简介

```uast
    mp 项目是由国内著名前端程序员 ping 和 后端大神 ming 功能开发，鉴于两位大神事情繁忙，因此项目周期可能会拉的比较长，但是依旧掩盖不了本
项目的精彩
``` 

## 项目主要依赖

* hutool
* mybatisPlus
* guava
* webSocket
* aop
* swagger
* redis
* devtools
* geoip2:  进行ip地址与物理地址得映射
* junit: 测试
* easyexcel: excel操作

## 数据库

* mysql
* redis
