package com.yzzzzun.typeconverter.converter;

import org.springframework.core.convert.converter.Converter;

import com.yzzzzun.typeconverter.type.IpPort;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IpPortToStringConverter implements Converter<IpPort, String> {
	@Override
	public String convert(IpPort source) {
		log.info("convert source={}", source);
		return source.getIp() + ":" + source.getPort();
	}
}
