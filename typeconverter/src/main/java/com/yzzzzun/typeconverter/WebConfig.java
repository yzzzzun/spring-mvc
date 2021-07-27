package com.yzzzzun.typeconverter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.yzzzzun.typeconverter.converter.IntegerToStringConverter;
import com.yzzzzun.typeconverter.converter.IpPortToStringConverter;
import com.yzzzzun.typeconverter.converter.StringToIntegerConverter;
import com.yzzzzun.typeconverter.converter.StringToIpPortConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringToIntegerConverter());
		registry.addConverter(new IntegerToStringConverter());
		registry.addConverter(new StringToIpPortConverter());
		registry.addConverter(new IpPortToStringConverter());
	}
}
