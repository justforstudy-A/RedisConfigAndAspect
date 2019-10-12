package com.springboot.demo.serice;

import com.springboot.demo.entity.User;

/**
 * 测试aop
 */
public interface TestService {

    void justPrint();

    String testCache(String id);

    String testCache2(String id);

    String testCache3(String one, String two, String three, String four);
}
