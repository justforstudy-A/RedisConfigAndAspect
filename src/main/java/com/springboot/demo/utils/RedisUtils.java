package com.springboot.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    private final static Logger log = LoggerFactory.getLogger(RedisUtils.class);

    private RedisUtils(){}

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    public boolean setKey(String key, Object val){
        try {
            redisTemplate.opsForValue().set(key, val);
            return true;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public Object getKey(String key){
        try{
           return redisTemplate.opsForValue().get(key);

        }catch (Exception e){
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public boolean delKey(String key){
        try {
            redisTemplate.delete(key);
            return true;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public boolean delKeys(String ...keys){
        try {
            redisTemplate.delete(Arrays.asList(keys));
            return true;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public void hset(String key, Object field, Object val){
        redisTemplate.opsForHash().put(key, field, val);
    }

    public void hsetVals(String key, Map<?, ?> map){
        redisTemplate.opsForHash().putAll(key, map);
    }

    public Object getHKey(String key, Object field){
       return redisTemplate.opsForHash().get(key, field);
    }

    public Set<Object> getHKeys(String key){
        return redisTemplate.opsForHash().keys(key);
    }

    public boolean hasKey(String key){
        try{
            return redisTemplate.hasKey(key);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public boolean expire(String key, TimeUnit unit, long time){
        return redisTemplate.expire(key, time, unit);
    }

    public boolean setKey(String key, Object val, long time, TimeUnit unit){
        redisTemplate.opsForValue().set(key, val, time, unit);
        return true;
    }

    public boolean setKey(String key, Object val, Duration duration){
        redisTemplate.opsForValue().set(key, val, duration);
        return true;
    }

}
