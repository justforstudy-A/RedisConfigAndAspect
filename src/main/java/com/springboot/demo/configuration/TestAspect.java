package com.springboot.demo.configuration;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TestAspect {


    /*@Around("execution(public * com.springboot.demo.serice.impl.TestServiceimpl.*(..))")
    public void log(ProceedingJoinPoint point){
        System.out.println(point.getSignature().getName());
        System.out.println("-----------");
    }*/
}
