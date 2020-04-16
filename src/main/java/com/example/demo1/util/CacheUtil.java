package com.example.demo1.util;

import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class CacheUtil {
    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;

    public<T> boolean saveCacheO(final String key, T t){
        //转JSON
        String string = JSONUtil.parse(t).toString();
        redisTemplate.opsForValue().set(key, string);
        return true;
    }



    public <T> T getCacheO(final String key,Class<T> tClass){
        String jsonString = redisTemplate.opsForValue().get(key).toString();
        T bean = JSONUtil.parseObj(jsonString).toBean(tClass);
        return bean;
    }

    public boolean hasKey(final String key){
        return redisTemplate.hasKey(key);
    }
    //写入缓存并设定失效时间
    public boolean setAndExpire(final String key, String value, long time) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更新缓存
     */
    public boolean getAndSet(final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().getAndSet(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除缓存
     */
    public boolean delete(final String key) {
        boolean result = false;
        try {
            redisTemplate.delete(key);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public String get(final String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }


    /**
     * 写入缓存
     */
    public boolean set(final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public<T> boolean setList(final String key, List<T> list){
        boolean result = false;
        try {
            for (T t : list) {
                redisTemplate.opsForList().leftPush(key,JSONUtil.parse(t).toString());
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public <T> List<T> getList(final String key,Class<T> tClass){
        ArrayList<T> results = new ArrayList<>();
        List<String> range = redisTemplate.opsForList().range(key, 0, -1);
        for (String s : range) {
            results.add(JSONUtil.parseObj(s).toBean(tClass));
        }
        return results;
    }

}
