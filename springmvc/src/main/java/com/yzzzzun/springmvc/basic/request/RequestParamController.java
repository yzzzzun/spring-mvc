package com.yzzzzun.springmvc.basic.request;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzzzzun.springmvc.basic.HelloData;

@Controller
public class RequestParamController {
	private Logger log = LoggerFactory.getLogger(RequestParamController.class);

	@RequestMapping("/request-param-v1")
	public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		int age = Integer.parseInt(request.getParameter("age"));

		log.info("username={}, age={}", username, age);
		response.getWriter().write("ok");
	}

	@ResponseBody
	@RequestMapping("/request-param-v2")
	public String requestParamV2(@RequestParam("username") String name, @RequestParam("age") int age) {
		log.info("username={}, age={}", name, age);
		return "ok text";
	}

	@ResponseBody
	@RequestMapping("/request-param-v3")
	public String requestParamV3(@RequestParam String username, @RequestParam int age) {
		log.info("username={}, age={}", username, age);
		return "ok text";
	}

	@ResponseBody
	@RequestMapping("/request-param-v4")
	public String requestParamV4(String username, int age) {
		log.info("username={}, age={}", username, age);
		return "ok text";
	}

	@ResponseBody
	@RequestMapping("/request-param-required")
	public String requestParamRequired(@RequestParam String username, @RequestParam(required = false) Integer age) {
		log.info("username={}, age={}", username, age);
		return "ok text";
	}

	@ResponseBody
	@RequestMapping("/request-param-default")
	public String requestParamDefault(@RequestParam(defaultValue = "guest") String username,
		@RequestParam(defaultValue = "-1", required = false) int age) {
		log.info("username={}, age={}", username, age);
		return "ok text";
	}

	@ResponseBody
	@RequestMapping("/request-param-map")
	public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
		log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
		return "ok text";
	}

	@ResponseBody
	@RequestMapping("/model-attribute-v1")
	public String requestModelAttributeV1(@ModelAttribute HelloData helloData) {
		log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
		return "ok text";
	}

	@ResponseBody
	@RequestMapping("/model-attribute-v2")
	public String requestModelAttributeV2(HelloData helloData) {
		log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
		return "ok text";
	}
}
