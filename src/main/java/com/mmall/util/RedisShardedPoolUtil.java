package com.mmall.util;

import com.mmall.common.RedisShardedPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.ShardedJedis;

@Slf4j
public class RedisShardedPoolUtil {

    public static String set(String key, String value){
        ShardedJedis shardedJedis = null;
        String result = null;
        try {
            shardedJedis = RedisShardedPool.getJedis();
            result = shardedJedis.set(key, value);
        } catch (Exception e) {
            log.error("set key:{} value:{} error", key, value, e);
            RedisShardedPool.returnBrokenResource(shardedJedis);
            return result;
        }
        RedisShardedPool.returnResource(shardedJedis);
        return result;
    }

    public static String get(String key){
        ShardedJedis shardedJedis = null;
        String result = null;
        try {
            shardedJedis = RedisShardedPool.getJedis();
            result = shardedJedis.get(key);
        } catch (Exception e) {
            log.error("set key:{} error", key, e);
            RedisShardedPool.returnBrokenResource(shardedJedis);
            return result;
        }
        RedisShardedPool.returnResource(shardedJedis);
        return result;
    }

    public static Long del(String key){
        ShardedJedis shardedJedis = null;
        Long result = null;
        try {
            shardedJedis = RedisShardedPool.getJedis();
            result = shardedJedis.del(key);
        } catch (Exception e) {
            log.error("del key:{} error", key, e);
            RedisShardedPool.returnBrokenResource(shardedJedis);
            return result;
        }
        RedisShardedPool.returnResource(shardedJedis);
        return result;
    }

    public static String setEx(String key, String value, int exTime){   //exTime的单位是秒
        ShardedJedis shardedJedis = null;
        String result = null;
        try {
            shardedJedis = RedisShardedPool.getJedis();
            result = shardedJedis.setex(key, exTime, value);
        } catch (Exception e) {
            log.error("setex key:{} expireTime:{}s value:{} error", key, exTime, value, e);
            RedisShardedPool.returnBrokenResource(shardedJedis);
            return result;
        }
        RedisShardedPool.returnResource(shardedJedis);
        return result;
    }

    /**
     * 设置key的有效期
     * @param key
     * @param exTime 单位是秒
     * @return
     */
    public static Long expire(String key, int exTime){
        ShardedJedis shardedJedis = null;
        Long result = null;
        try {
            shardedJedis = RedisShardedPool.getJedis();
            result = shardedJedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("expire key:{} expireTime:{}s value:{} error", key, exTime, e);
            RedisShardedPool.returnBrokenResource(shardedJedis);
            return result;
        }
        RedisShardedPool.returnResource(shardedJedis);
        return result;
    }

    public static Long setnx(String key, String value){   //exTime的单位是秒
        ShardedJedis shardedJedis = null;
        Long result = null;
        try {
            shardedJedis = RedisShardedPool.getJedis();
            result = shardedJedis.setnx(key, value);
        } catch (Exception e) {
            log.error("setex key:{} value:{} error", key, value, e);
            RedisShardedPool.returnBrokenResource(shardedJedis);
            return result;
        }
        RedisShardedPool.returnResource(shardedJedis);
        return result;
    }

    public static String getSet(String key, String value){
        ShardedJedis shardedJedis = null;
        String result = null;
        try {
            shardedJedis = RedisShardedPool.getJedis();
            result = shardedJedis.getSet(key, value);
        } catch (Exception e) {
            log.error("getSet key:{} value:{} error", key, value, e);
            RedisShardedPool.returnBrokenResource(shardedJedis);
            return result;
        }
        RedisShardedPool.returnResource(shardedJedis);
        return result;
    }

}
