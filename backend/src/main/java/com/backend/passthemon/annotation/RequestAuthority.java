package com.backend.passthemon.annotation;

import java.lang.annotation.*;
//权限认证
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RequestAuthority {
    String[] value();
    //所有的权限都满足，默认为false,表示满足一个就可以
    boolean andAuthority() default false;
}
