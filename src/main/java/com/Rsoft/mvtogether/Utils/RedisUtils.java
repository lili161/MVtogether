package com.Rsoft.mvtogether.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class RedisUtils {
  @Autowired private RedisTemplate redisTemplate;
  /**
   * 写入缓存
   *
   * @param key
   * @param value
   * @return
   */
  public boolean set(final String key, Object value) {
    boolean result = false;
    try {
      ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
      operations.set(key, value);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }
  /**
   * 写入缓存设置时效时间
   *
   * @param key
   * @param value
   * @return
   */
  public boolean set(final String key, Object value, Long expireTime, TimeUnit timeUnit) {
    boolean result = false;
    try {
      ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
      operations.set(key, value);
      redisTemplate.expire(key, expireTime, timeUnit);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
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
   * 读取缓存
   *
   * @param key
   * @return
   */
  public Object get(final String key) {
    Object result = null;
    ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
    result = operations.get(key);
    return result;
  }
  /**
   * 哈希 添加
   *
   * @param key
   * @param hashKey
   * @param value
   */
  public void hmSet(String key, Object hashKey, Object value) {
    HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
    hash.put(key, hashKey, value);
  }
  /**
   * 哈希获取数据
   *
   * @param key
   * @param hashKey
   * @return
   */
  public Object hmGet(String key, Object hashKey) {
    HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
    return hash.get(key, hashKey);
  }
  /**
   * 列表添加
   *
   * @param k
   * @param v
   */
  public void lPush(String k, Object v) {
    ListOperations<String, Object> list = redisTemplate.opsForList();
    list.rightPush(k, v);
  }
  /**
   * 列表获取
   *
   * @param k
   * @param l
   * @param l1
   * @return
   */
  public List<Object> lRange(String k, long l, long l1) {
    ListOperations<String, Object> list = redisTemplate.opsForList();
    return list.range(k, l, l1);
  }
  /**
   * 集合添加
   *
   * @param key
   * @param value
   */
  public void add(String key, Object value) {
    SetOperations<String, Object> set = redisTemplate.opsForSet();
    set.add(key, value);
  }
  /**
   * 集合获取
   *
   * @param key
   * @return
   */
  public Set<Object> setMembers(String key) {
    SetOperations<String, Object> set = redisTemplate.opsForSet();
    return set.members(key);
  }
  /**
   * 有序集合添加
   *
   * @param key
   * @param value
   * @param scoure
   */
  public void zAdd(String key, Object value, double scoure) {
    ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
    zset.add(key, value, scoure);
  }
  /**
   * 有序集合获取
   *
   * @param key
   * @param scoure
   * @param scoure1
   * @return
   */
  public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
    ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
    return zset.rangeByScore(key, scoure, scoure1);
  }

  private String useScriptIncr(String key, long expireseconds) {
    StringBuilder script =
        new StringBuilder(
            " local current = redis.call('incr',KEYS[1]);local current = redis.call('incr',KEYS[1]);local current = redis.call('incr',KEYS[1]);"
               );
    DefaultRedisScript redisScript = new DefaultRedisScript();
    redisScript.setScriptText(script.toString());
    redisTemplate.execute(redisScript, Arrays.asList(new String[] {key}), expireseconds);
    return null;
  }

  public void increasePerSecond(String key, long expireSeconds, long DurationSeconds) {
    // 判断是否已经有线程在运行自增长
    String inuse = key + "inuse";
    String temp;
    try {
      temp = redisTemplate.opsForValue().get(inuse).toString();
    } catch (NullPointerException e) {
      temp = null;
    }

    if (null == temp || temp.equals("0")) {
      set(inuse, 1);
      Timer timer = new Timer();
      timer.schedule(
          new TimerTask() {
            @Override
            public void run() {
              useScriptIncr(key, expireSeconds);
              //
              //                this.cancel();
            }
          },
          4000,
          3000);
      timer.schedule(
          new TimerTask() {
            @Override
            public void run() {
              System.out.println("timer-2 is running");
              if (Long.parseLong(redisTemplate.opsForValue().get(key).toString())
                  >= DurationSeconds) {
                redisTemplate.delete(key + "inuse");
                //删除 <roomNum>
                //删除 <-roomNum-MvNum>
                //删除<ownerName>
                //删除<customerName>
                //删除 数据库本房间信息
                timer.cancel();
              }
            }
          },
          1000 , // 延迟秒检查
          1000 * 60 * 10); // 10min一检查
    }
  }
}
