package com.Rsoft.mvtogether.Utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LR
 * @date 2021/2/18
 * 交流可加qq群 1027263551
 **/
@SpringBootTest
class RedisUtilsTest {
@Autowired
public RedisUtils redisUtils;
  @Test
  void increasePerSecond() {
    redisUtils.increasePerSecond("second",1000,14400);
  }
}