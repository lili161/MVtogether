package com.Rsoft.mvtogether;

import com.Rsoft.mvtogether.Utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MvtogetherApplicationTests {
	@Autowired
	public static RedisUtils redisUtils;

  public static void main(String[] args) {
  }
}
