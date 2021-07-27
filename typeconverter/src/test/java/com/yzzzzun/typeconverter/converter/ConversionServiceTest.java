package com.yzzzzun.typeconverter.converter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import com.yzzzzun.typeconverter.type.IpPort;

public class ConversionServiceTest {
	@DisplayName("")
	@Test
	void conversionServiceTest() {

		DefaultConversionService conversionService = new DefaultConversionService();
		conversionService.addConverter(new StringToIntegerConverter());
		conversionService.addConverter(new IntegerToStringConverter());
		conversionService.addConverter(new StringToIpPortConverter());
		conversionService.addConverter(new IpPortToStringConverter());

		IpPort ipPort = new IpPort("127.0.0.1", 8080);
		String ipPortString = "127.0.0.1:8080";

		Assertions.assertThat(conversionService.convert(10, String.class)).isEqualTo("10");
		Assertions.assertThat(conversionService.convert("10", Integer.class)).isEqualTo(10);
		Assertions.assertThat(conversionService.convert(ipPortString, IpPort.class)).isEqualTo(ipPort);
		Assertions.assertThat(conversionService.convert(ipPort, String.class)).isEqualTo(ipPortString);
	}
}
