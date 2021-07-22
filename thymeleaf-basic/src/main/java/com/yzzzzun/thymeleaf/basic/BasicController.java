package com.yzzzzun.thymeleaf.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.Data;

@Controller
@RequestMapping("/basic")
public class BasicController {

	@GetMapping("/text-basic")
	public String textBasic(Model model){
		model.addAttribute("data","hello <b>thymeleaf!</b>");
		return "basic/text-basic";
	}

	@GetMapping("/text-unescaped")
	public String textUnescaped(Model model){
		model.addAttribute("data","hello <b>thymeleaf!</b>");
		return "basic/text-unescaped";
	}

	@GetMapping("/variable")
	public String variable(Model model){
		User userA = new User("userA", 10);
		User userB = new User("userB", 20);

		List<User> list = new ArrayList<>();
		list.add(userA);
		list.add(userB);

		Map<String,User> map = new ConcurrentHashMap<>();
		map.put("userA",userA);
		map.put("userB",userB);

		model.addAttribute("user", userA);
		model.addAttribute("users", list);
		model.addAttribute("userMap", map);

		return "basic/variable";
	}

	@Data
	static class User{
		private String username;
		private int age;

		public User(String username, int age) {
			this.username = username;
			this.age = age;
		}
	}
}
