package com.morethan.game.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * 类简单作用描述
 *
 * @Description: 类作用描述
 * @Author: Anthony
 * @CreateDate: 2018/4/21
 * @UpdateUser: Anthony
 * @UpdateDate: 2018/4/21 10:58 AM
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Component
public class RedisUtil {

    public static final int TIMEOUT = 1000;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    public void push(String key, String value){
        stringRedisTemplate.opsForList().leftPush(key, value);
    }

    public String pop(String key){
        return stringRedisTemplate.opsForList().rightPop(key);
    }

    public void set(String key, String value, Integer time) {
    	if(time==null || time==-1) {
    		stringRedisTemplate.opsForValue().set(key, value);
    	}else {
    		stringRedisTemplate.opsForValue().set(key, value, time, TimeUnit.HOURS);
    	}
    }
    
    public void setValue(String key, String value, Integer time) {
    	stringRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        stringRedisTemplate.opsForValue().getOperations().delete(key);
    }

    public boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    public void pushPoke(String key, Map<String, Object> value){
        stringRedisTemplate.opsForList().leftPush(key, JSONObject.toJSONString(value));
    }

    @SuppressWarnings("unchecked")
	public Map<String, Object> popPoke(String key){
        String str = stringRedisTemplate.opsForList().rightPop(key);
        if(null == str){
            return null;
        }
        return JSONObject.parseObject(str, Map.class);
    }

    
    /**
	 * 从队列删除数据
	 * @param key
	 * @param value
	 */
	public void remove(String key,String value) {
		stringRedisTemplate.opsForList().remove(key, 0, value);
	}
	
	public List<String> getList(String key) {
        Set<String> keys = stringRedisTemplate.keys(key);
        if (keys.isEmpty()) {
            return null;
        }
        return stringRedisTemplate.opsForValue().multiGet(keys);
    }
}
