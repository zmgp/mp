# 限流

## 同一IP短时间内访问限流

```java
public @interface AccessLimit {
    /**
     * 指定second 时间内，API最多的请求次数
     */
    int times() default 3;

    /**
     * 指定时间second，redis数据过期时间
     */
    int second() default 10;
}
```

### AOP依赖

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```
### 实现原理

```
1. 定义切面RequestLimitAOP
2. 
```
