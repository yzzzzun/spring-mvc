package com.yzzzzun.typeconverter.formatter;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;

import com.yzzzzun.typeconverter.converter.IpPortToStringConverter;
import com.yzzzzun.typeconverter.converter.StringToIpPortConverter;
import com.yzzzzun.typeconverter.type.IpPort;

class FormattingConversionServiceTest {

	@DisplayName("formatting conversion service test")
	@Test
	void formattingConversionService(){
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();

		//converter 등록
		conversionService.addConverter(new StringToIpPortConverter());
		conversionService.addConverter(new IpPortToStringConverter());
		//formatter 등록
		conversionService.addFormatter(new MyNumberFormatter());

		IpPort ipPort = new IpPort("127.0.0.1", 8080);
		String ipPortString = "127.0.0.1:8080";
		assertThat(conversionService.convert(ipPortString, IpPort.class)).isEqualTo(ipPort);
		assertThat(conversionService.convert(ipPort, String.class)).isEqualTo(ipPortString);
		assertThat(conversionService.convert(1000,String.class)).isEqualTo("1,000");
		assertThat(conversionService.convert("1,000",Long.class)).isEqualTo(1000L);
	}
}
