package com.yzzzzun.springmvc.basic.request;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestHeaderController {
	private Logger log = LoggerFactory.getLogger(RequestHeaderController.class);

	@RequestMapping("/headers")
	public String headers(HttpServletRequest request, HttpServletResponse response, HttpMethod httpMethod,
		Locale locale,
		@RequestHeader MultiValueMap<String, String> headerMap,
		@RequestHeader("host") String host,
		@CookieValue(value = "myCookie", required = false) String cookie) {

		log.info("request={}", request);
		log.info("response={}", response);
		log.info("httpMethod={}", httpMethod);
		log.info("locale={}", locale);
		log.info("headerMap={}", headerMap);
		log.info("host={}", host);
		log.info("myCookie={}", cookie);
		return "ok";
	}

}
