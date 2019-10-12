package com.springboot.demo.Controller;

import com.springboot.demo.serice.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping(value = "/index")
    public String test(){
        testService.justPrint();
        return "success";
    }

    @GetMapping(value = "/testCache/{id}")
    public String testCache(@PathVariable(value = "id") String id){
        String s = testService.testCache(id);
        return s;
    }

    @GetMapping(value = "/testCache2/{id}")
    public String testCache2(@PathVariable(value = "id") String id){
        String s = testService.testCache2(id);
        return s;
    }

    @GetMapping(value = "/testCache3")
    public String testCache3(){
        String s = testService.testCache3("a", "b", "c", "d");
        return s;
    }
}
