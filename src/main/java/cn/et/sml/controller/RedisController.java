package cn.et.sml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {
	
	@Autowired
	StringRedisTemplate template;

	//http://localhost/testRedis
	@GetMapping("/testRedis")
	public String userList() {
		/**
		 * 最简单的redis集成，
		 * boundValueOps("键").set("值") 字符串类型
		template.boundSetOps("键").add("值");  set类型
		template.boundHashOps("键").put("属性","值");  hash类型
		template.boundListOps("键"); list类型
		template.boundZSetOps("键");  zset类型
		*/
		template.boundValueOps("user:1").set("zyx");
	
		/*	template.boundSetOps("user2").add("clx");
		template.boundHashOps("user3").put("user", "jwx");
		*/
		return null;
	}
}
