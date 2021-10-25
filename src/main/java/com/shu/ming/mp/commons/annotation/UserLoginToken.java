package com.shu.ming.mp.commons.annotation;

import com.shu.ming.mp.commons.domain.Permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*需要登录才能进行操作的注解*/
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserLoginToken {
    boolean required() default true;
    int[] permission() default {Permission.LOGIN_PERMISSION};
}
