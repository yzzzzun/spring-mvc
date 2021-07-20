package com.yzzzzun.servlet.basic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HelloData {
	private String username;
	private int age;

	protected HelloData() {
	}

	public HelloData(String username, int age) {
		this.username = username;
		this.age = age;
	}
}
