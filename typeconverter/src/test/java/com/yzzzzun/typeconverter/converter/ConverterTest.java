package com.yzzzzun.typeconverter.converter;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.yzzzzun.typeconverter.type.IpPort;

class ConverterTest {

 	@DisplayName("string to integer")
 	@Test
 	void testStringToInteger() {
 		StringToIntegerConverter converter = new StringToIntegerConverter();
		Integer result = converter.convert("10");
		assertThat(result).isEqualTo(10);
 	}

 	@DisplayName("integer to string")
 	@Test
 	void testIntegerToString() {
 		IntegerToStringConverter converter = new IntegerToStringConverter();
		String result = converter.convert(10);
		assertThat(result).isEqualTo("10");
 	}

 	@DisplayName("ipPort to String")
 	@Test
 	void testIpPortToString() {
 		IpPortToStringConverter converter = new IpPortToStringConverter();
		IpPort ipPort = new IpPort("127.0.0.1", 8080);
		String result = converter.convert(ipPort);
		assertThat(result).isEqualTo("127.0.0.1:8080");
	}

	@DisplayName("String to IpPort")
	@Test
	void testStringToIpPort() {
		StringToIpPortConverter converter = new StringToIpPortConverter();
		IpPort ipPort = new IpPort("127.0.0.1", 8080);
		IpPort result = converter.convert("127.0.0.1:8080");
		assertThat(result).isEqualTo(ipPort);
	}
}
