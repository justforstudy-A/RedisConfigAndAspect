package com.springboot.demo.configuration;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 控制层日志输出
 */
@Component
@Aspect
public class ControllerAspect {


    private final static Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    /*@Before("execution(public * com.springboot..*.Controller.*.*(..))")
    public void inputArgs(JoinPoint point){
        System.out.println("调用方法-----" + point.getSignature().getName());
        System.out.println("请求参数-----" + JSONObject.toJSONString(point.getArgs()));
    }*/

    /*@Around("execution(public * com.springboot..*.Controller.*.*(..))")
    public String outArgs(ProceedingJoinPoint point) throws Throwable {
//        System.out.println("调用方法1-----" + point.getSignature().getName());
        logger.info("调用方法2-----" + point.getSignature().getName());
        logger.info("返回结果-----" + JSONObject.toJSONString(point.proceed()));
        //language=JSON
        return JSONObject.toJSONString(point.proceed());
    }*/
}
