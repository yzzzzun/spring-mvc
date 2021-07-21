package com.yzzzzun.springmvc.basic.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RequestBodyStringController {

	private Logger log = LoggerFactory.getLogger(RequestBodyStringController.class);

	@PostMapping("/request-body-string-v1")
	public void requestBodyStringV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServletInputStream inputStream = request.getInputStream();
		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

		log.info("messageBody = {}", messageBody);
		response.getWriter().write("ok");
	}

	@PostMapping("/request-body-string-v2")
	public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

		log.info("messageBody = {}", messageBody);
		responseWriter.write("ok");
	}

	@PostMapping("/request-body-string-v3")
	public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
		String messageBody = httpEntity.getBody();
		log.info("messageBody = {}", messageBody);

		return new ResponseEntity<>("ok", HttpStatus.CREATED);
	}

	/**
	 * HttpMessageConverter를 통해 파싱됨
	 */
	@ResponseBody
	@PostMapping("/request-body-string-v4")
	public String requestBodyStringV4(@RequestBody String messageBody) throws IOException {
		log.info("messageBody = {}", messageBody);
		return "ok";
	}
}
