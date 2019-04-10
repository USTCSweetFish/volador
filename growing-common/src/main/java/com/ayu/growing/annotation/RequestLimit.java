package com.ayu.growing.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface RequestLimit {

    int count() default Integer.MAX_VALUE;

    int time() default 60;
}
