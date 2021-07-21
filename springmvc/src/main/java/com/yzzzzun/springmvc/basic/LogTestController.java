package com.yzzzzun.springmvc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogTestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogTestController.class);

	@GetMapping("/log-test")
	public String logTest() {
		String name = "Spring";
		LOGGER.trace("trace log = {}", name);
		LOGGER.debug("debug log = {}", name);
		LOGGER.info("info log = {}", name);
		LOGGER.warn("warn log = {}", name);
		LOGGER.error("error log = {}", name);
		return "OK";
	}
}
