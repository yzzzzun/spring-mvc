package com.yzzzzun.springmvc.basic.response;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yzzzzun.springmvc.basic.HelloData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ResponseBodyController {

	@GetMapping("/response-body-string-v1")
	public void responseBodyV1(HttpServletResponse response) throws IOException {
		response.getWriter().write("ok");
	}

	@GetMapping("/response-body-string-v2")
	public HttpEntity<String> responseBodyV2() {
		return new ResponseEntity<>("ok", HttpStatus.OK);
	}

	@GetMapping("/response-body-string-v3")
	public String responseBodyV3() {
		return "ok";
	}

	@GetMapping("/response-body-json-v1")
	public ResponseEntity<HelloData> responseBodyJsonV1() {
		HelloData helloData = new HelloData();
		helloData.setUsername("hello");
		helloData.setAge(20);
		return new ResponseEntity<>(helloData, HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/response-body-json-v2")
	public HelloData responseBodyJsonV2() {
		HelloData helloData = new HelloData();
		helloData.setUsername("hello");
		helloData.setAge(20);
		return helloData;
	}

}
