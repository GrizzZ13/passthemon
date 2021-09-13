package com.backend.passthemon.annotation;

import java.lang.annotation.*;
//安全认证，不能获取、修改他人的信息
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RequestConsistent {
    //是否要求用户的userid与访问接口带的userid一致,默认值为false
    boolean identityConsistent() default false;
}
