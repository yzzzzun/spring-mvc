package com.yzzzzun.typeconverter.converter;

import org.springframework.core.convert.converter.Converter;

import com.yzzzzun.typeconverter.type.IpPort;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringToIpPortConverter implements Converter<String, IpPort> {
	@Override
	public IpPort convert(String source) {
		log.info("convert source = {}", source);

		String[] split = source.split(":");
		String ip = split[0];
		int port = Integer.parseInt(split[1]);
		return new IpPort(ip,port);
	}
}
