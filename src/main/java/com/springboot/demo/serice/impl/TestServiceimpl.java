package com.springboot.demo.serice.impl;

import com.springboot.demo.animation.CacheTime;
import com.springboot.demo.entity.User;
import com.springboot.demo.serice.TestService;
import org.aspectj.lang.annotation.Around;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class TestServiceimpl implements TestService {


    @Override
    @Around("writeLog()")
    public void justPrint() {
        System.out.println("===========");

    }

    @Override
    @Cacheable(value = "TestRedis", key = "#id", unless="#result == null")
    public String testCache(String id) {
        return Math.random() + "";
    }

    @Override
    @CacheTime(expire = 120, key = "TestServiceimpl_testCache2")
    public String testCache2(String id) {
        return Math.random() + "";
    }

    @Override
    @CacheTime(expire = 120, key = "TestServiceimpl_testCache3 + #p1 + #p3 + # p2 + __+#p4")
    public String testCache3(String one, String two, String three, String four) {
        return Math.random() + "";
    }
}
