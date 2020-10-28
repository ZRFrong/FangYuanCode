package com.ruoyi.common.redis.util;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import sun.applet.Main;

/**
 * Redis工具类
 */
@Component
public class RedisUtils
{
    @Autowired
    private RedisTemplate<String, String>   redisTemplate;

    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> valueOperations;
    private ZSetOperations<String,String> zSetOperations;

    /**  默认过期时长，单位：秒 */
    public final static long                DEFAULT_EXPIRE = 60 * 60 * 24;

    /**  不设置过期时长 */
    public final static long                NOT_EXPIRE     = -1;

    /**
     * 插入缓存默认时间
     * @param key 键
     * @param value 值
     * @author zmr
     */
    public void set(String key, Object value)
    {
        set(key, value, DEFAULT_EXPIRE);
    }

    /**
     * 插入缓存
     * @param key 键
     * @param value 值
     * @param expire 过期时间(s)
     * @author zmr
     */
    public void set(String key, Object value, long expire)
    {
        valueOperations.set(key, toJson(value));
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * zset 插入
     * @param key 集合key
     * @param value
     * @param score 分数 add是覆盖的方式
     */
    public void zSetAdd(String key,String value,double score){
        redisTemplate.opsForZSet().add(key,value,score);
    }

    /**
     * 删除结合中元素
     * @param key 集合key
     * @param value
     */
    public void zSetRemove(String key,String value){
        redisTemplate.opsForZSet().remove(key,value);
    }

    /**
     * 给指定集合key设置过期时间 单位秒
     * @param key
     * @param expire
     */
    public void setKeyExpire(String key,Long expire){
        redisTemplate.expire(key,expire,TimeUnit.SECONDS);
    }

    /**
     * 分数递增
     * @param key 集合key
     * @param value
     * @param score
     * @return 增量或减量之后的分数
     */
    public Double incrScore(String key,String value,Double score){
        Double result = redisTemplate.opsForZSet().incrementScore(key, value, score);
        return result;
    }

    /**
     * 获取元素在集合中的分数
     * @param key
     * @param value
     * @return
     */
    public Double score(String key, String value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 获取元素在集合中的排名
     * @param key
     * @param value
     * @return
     */
    public Long rank(String key, String value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * 返回集合的长度
     *
     * @param key
     * @return
     */
    public Long size(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 查询集合中指定顺序的值， 0 -1 表示获取全部的集合内容  zrange
     *
     * 返回有序的集合，score小的在前面
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> range(String key, int start, int end) {
        Set<String> range = redisTemplate.opsForZSet().range(key, start, end);
        return range;
    }

    /**
     * 查询集合中指定顺序的值和score，0, -1 表示获取全部的集合内容
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> rangeWithScore(String key, int start, int end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * 查询集合中指定顺序的值  zrevrange
     *
     * 返回有序的集合中，score大的在前面
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> revRange(String key, int start, int end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 根据score的值倒序，来获取指定区间的集合  zrangebyscore
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<String> sortRange(String key, int min, int max) {
        return redisTemplate.opsForZSet().reverseRange(key,min,max);
    }

    /**
     * 删除指定区间元素
     * @param key
     * @param start
     * @param end
     */
    public void removeByRank(String key,Long start, Long end){
        //ZREMRANGEBYRANK
        zSetOperations.removeRange(key,start,end);
    }

    /**
     * 判断一个key是否在redis中存在
     * @param key
     * @return
     */
    public Boolean getKeyIsExist(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 获取正序的第一个元素
     * @param key
     * @return
     */
    public Long getZSetValue(String key){
        Set<Object> set = zSetOperations.range(key, 0, 0);
        return Long.valueOf(set.toArray()[0]+"");
    }

    /**
     * 获取zst集合长度
     * @param key
     * @return
     */
    public Long getZSetCard(String key){
        return zSetOperations.zCard(key);
    }

    /**git pull origin master
     * 返回字符串结果
     * @param key 键
     * @return
     * @author zmr
     */
    public String get(String key)
    {
        return valueOperations.get(key);
    }

    /**
     * 返回指定类型结果
     * @param key 键
     * @param clazz 类型class
     * @return
     * @author zmr
     */
    public <T> T get(String key, Class<T> clazz)
    {
        String value = valueOperations.get(key);
        return value == null ? null : fromJson(value, clazz);
    }

    /**
     * 删除缓存
     * @param key 键
     * @author zmr
     */
    public void delete(String key)
    {
        redisTemplate.delete(key);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object)
    {
        if (object instanceof Integer || object instanceof Long || object instanceof Float || object instanceof Double
                || object instanceof Boolean || object instanceof String)
        {
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }

    /**
     * 模糊查询key
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern){
        try {
            Set<String> keys = redisTemplate.keys(pattern);
            return keys;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz)
    {
        return JSON.parseObject(json, clazz);
    }

}