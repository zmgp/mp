# 限流

## 同一IP短时间内访问限流

```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface RequestLimit {
    /**
     * 指定second 时间内，API最多的请求次数
     * 次数： 不间断点击大约 4s 点击 20 次， 故而设置 15
     */
    int count() default 15;

    /**
     * 指定时间second，redis数据过期时间
     */
    int second() default 4;

    /**
     * 指定超过限制后，多久后能重新访问
     */
    int expire() default 60;

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
基于redis + AOP的原理实现同一IP地址 + API接口的限流
```

# 布隆过滤器

## 实现原理

```
基于  hutool + redis  实现布隆过滤器
```

