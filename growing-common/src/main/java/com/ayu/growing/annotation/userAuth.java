package com.bilibili.growing.annotation;

public @interface userAuth {

    String key() default "accessKey";
    int support() default 0;//默认0 0表示只支持accessKey和cookie，1表示既支持accessKey和cookie，也支持传uid
}
