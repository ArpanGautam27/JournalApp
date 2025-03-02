package com.arpangautam27.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

	@Autowired
	private RedisTemplate redisTemplate;
	
	@Test
	public void emailTest() {
		redisTemplate.opsForValue().set("name", "Arpan");
		
		Object object = redisTemplate.opsForValue().get("name");
		System.out.println(object);
	}
	
}
