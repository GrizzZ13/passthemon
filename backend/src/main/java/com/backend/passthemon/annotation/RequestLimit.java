package com.backend.passthemon.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimit {
    //每秒创建令牌个数,默认:500
    int QPS() default 500;
    //获取令牌等待时间 默认：500
    int timeout() default 500;
    //超时时间单位 默认：毫秒
    TimeUnit timeunit() default TimeUnit.MILLISECONDS;
}
