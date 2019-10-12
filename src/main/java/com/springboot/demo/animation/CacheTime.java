package com.springboot.demo.animation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheTime {

    /**
     * 过期时间  默认60s
     * @return
     */
    long expire() default 60;

    /**
     * 键
     *
     * 以下键添加参数写入
     * @return
     */
    String key();

    /**
     * 第二层键
     * @return
     */
    String value() default "";

    /**
     * 时间单位
     * @return
     */
    TimeUnit unit() default TimeUnit.SECONDS;


}
