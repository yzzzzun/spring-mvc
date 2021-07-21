package com.yzzzzun.springmvc.basic.request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzzzzun.springmvc.basic.HelloData;

@Controller
public class RequestBodyJsonController {
	private ObjectMapper objectMapper = new ObjectMapper();
	private Logger log = LoggerFactory.getLogger(RequestBodyJsonController.class);

	@PostMapping("/request-body-json-v1")
	public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServletInputStream inputStream = request.getInputStream();
		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

		log.info("messageBody = {}", messageBody);
		HelloData data = objectMapper.readValue(messageBody, HelloData.class);
		log.info("helloData = {}", data);

		response.getWriter().write("ok");
	}

	@ResponseBody
	@PostMapping("/request-body-json-v2")
	public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
		log.info("messageBody = {}", messageBody);
		HelloData data = objectMapper.readValue(messageBody, HelloData.class);
		log.info("helloData = {}", data);
		return "ok";
	}

	@ResponseBody
	@PostMapping("/request-body-json-v3")
	public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {
		log.info("helloData = {}", helloData);
		return "ok";
	}

	@ResponseBody
	@PostMapping("/request-body-json-v4")
	public String requestBodyJsonV4(HttpEntity<HelloData> helloData) throws IOException {
		log.info("helloData = {}", helloData.getBody());
		return "ok";
	}

	@ResponseBody
	@PostMapping("/request-body-json-v5")
	public HelloData requestBodyJsonV5(@RequestBody HelloData helloData) throws IOException {
		log.info("helloData = {}", helloData);
		return helloData;
	}
}
