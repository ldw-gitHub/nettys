package com.framework.config;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.framework.constant.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;


/**
 * RedisUtils 工具类
 */
@SuppressWarnings("unchecked")
@Component
public class RedisUtils {

    @SuppressWarnings("rawtypes")
	private RedisTemplate redisTemplate;
    
    @SuppressWarnings("rawtypes")
	@Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }
    
    
    /**
     * 批量删除对应的value
     * 
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     * 
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     * 
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     * 
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }
    
    /**
     * 递增操作
     * @param key
     * @param delta 增量
     * @return
     */
    public Long incr(String key, long delta) {
    	return redisTemplate.opsForValue().increment(key, delta);
    }
    
    /**
     * 递减操作
     * @param key
     * @param delta 减量
     * @return
     */
    public Long decr(String key, long delta) {
    	return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * 读取缓存
     * 
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object resultInfo = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        resultInfo = operations.get(key);
        return resultInfo;
    }

    /**
     * 写入缓存
     * 
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean resultInfo = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            resultInfo = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return resultInfo;
    }

    /**
     * 写入缓存
     * 
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean resultInfo = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            resultInfo = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return resultInfo;
    }
    
    /**
     * 设置ttl
     * @param key
     * @param expireTime 单位秒
     * @return 
     * @date 2018年5月15日
     * @author sky_luan
     */
    public boolean setTTL(final String key, Long expireTime) {
    	boolean resultInfo = false;
    	try {
    		redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    		resultInfo = true;
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	return resultInfo;
    }
    
    //==========================Hash==================================================
    
    public boolean hset(final String key, Map<String, Object> map, Long expireTime) {
    	boolean resultInfo = false;
    	try {
    		HashOperations<String, String, Object> operations = redisTemplate.opsForHash();
    		operations.putAll(key, map);
    		if(null != expireTime){
    			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    		}
    		resultInfo = true;
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	return resultInfo;
    }
    
    /**
     * 
     * hash获取整个Map
     * @param key
     * @return 
     * @date 2018年1月11日
     * @author sky_luan
     */
    public Map<String, Object> hgetAll(final String key) {
    	try {
    		HashOperations<String, String, Object> operations = redisTemplate.opsForHash();
    		Map<String, Object> map = operations.entries(key);
    		return map;
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    /**
     * 
     * 获取hash map中的某一个key对应的值
     * @param key
     * @param hashKey
     * @return 
     * @date 2018年1月11日
     * @author sky_luan
     */
    public String hget(final String key, final String hashKey) {
    	try {
    		HashOperations<String, String, String> operations = redisTemplate.opsForHash();
    		return operations.get(key, hashKey);
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    
    
}