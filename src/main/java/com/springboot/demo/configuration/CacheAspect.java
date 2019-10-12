package com.springboot.demo.configuration;

import com.springboot.demo.animation.CacheTime;
import com.springboot.demo.utils.RedisUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
public class CacheAspect {

    private final static Logger log = LoggerFactory.getLogger(CacheAspect.class);

    @Autowired
    private RedisUtils redisUtils;

    @Pointcut(value = "@annotation(com.springboot.demo.animation.CacheTime)")
    public void makeCache(){}

    @Around("makeCache()")
    public Object handle(ProceedingJoinPoint point) throws Throwable {
        Class<?> className = point.getTarget().getClass();
        String methodName = point.getSignature().getName();
        Class[] parameterTypes = ((MethodSignature) point.getSignature()).getParameterTypes();
        Object[] args = point.getArgs();
        String key = "";
        long expire = -1;
        TimeUnit unit = null;
        try{
            Method method = className.getMethod(methodName, parameterTypes);
            method.setAccessible(true);
            CacheTime cacheTime = method.getAnnotation(CacheTime.class);
            if(cacheTime != null){
                //如果存在该注解
                expire = this.getExpire(cacheTime);
                key = this.getKey(cacheTime, args);
                unit = cacheTime.unit();
            }
        }catch (Exception e){
            log.error("缓存设置失败!!!");
            log.error(e.getMessage(), e);
        }
        /**设置redis缓存*/
        if (redisUtils.hasKey(key)) {
            return redisUtils.getKey(key);
        }else{
            //设置key
            Object result = point.proceed();
            redisUtils.setKey(key, result, expire, unit);
            return result;
        }
    }

    /**
     * 获取到期时间
     * @param cacheTime
     * @return
     */
    private long getExpire(CacheTime cacheTime){
        return cacheTime.expire();
    }

    /**
     * 获取键
     * @param cacheTime
     * @return
     */
    private String getKey(CacheTime cacheTime, Object[] args) throws Exception {
        return formatKey(cacheTime.key(), args);
    }

    private String formatKey(String key, Object args[]) throws Exception {
        if(key.indexOf('#') != -1){
            StringBuilder builder = new StringBuilder();
            String[] p = key.trim().replaceAll(" ", "").split("\\+");
            int i = 0;
            for(; i < p.length; i++){
                if(p[i].contains("#p")){
                    int paramsIndex = Integer.valueOf(p[i].trim().replaceAll(" ", "").substring(2)) - 1; //第几个参数
                    if(paramsIndex > args.length - 1){
                        throw new Exception("键非法!!!");
                    }
                    p[i] = String.valueOf(args[paramsIndex]);
                }
                builder.append(p[i]);
            }
            return builder.toString();
        }
        return key;
    }
}
