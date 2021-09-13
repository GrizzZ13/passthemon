package com.backend.passthemon.annotation;

import java.lang.annotation.*;
//针对单个用户的限流，防止恶意攻击
@Inherited
@Documented
@Target({ElementType.FIELD,ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {
    //标识，指定sec时间段内的访问次数限制
    int limit() default 5;
    //标识 时间段
    int seconds() default 5;
}
