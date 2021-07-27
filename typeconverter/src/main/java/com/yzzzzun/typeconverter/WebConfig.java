package com.yzzzzun.typeconverter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.yzzzzun.typeconverter.converter.IpPortToStringConverter;
import com.yzzzzun.typeconverter.converter.StringToIpPortConverter;
import com.yzzzzun.typeconverter.formatter.MyNumberFormatter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringToIpPortConverter());
		registry.addConverter(new IpPortToStringConverter());
		registry.addFormatter(new MyNumberFormatter());
	}
}
