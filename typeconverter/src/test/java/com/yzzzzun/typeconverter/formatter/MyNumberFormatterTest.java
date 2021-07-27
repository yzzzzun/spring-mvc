package com.yzzzzun.typeconverter.formatter;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.util.Locale;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MyNumberFormatterTest {

	@DisplayName("formatter parse")
	@Test
	void testFormatterParse() throws ParseException {
	    MyNumberFormatter formatter = new MyNumberFormatter();
		Number result = formatter.parse("1000", Locale.KOREA);
		Assertions.assertThat(result).isEqualTo(1000L);
	}

	@DisplayName("formatter print")
	@Test
	void testFormatterPrint() {
		MyNumberFormatter formatter = new MyNumberFormatter();
		String result = formatter.print(1000, Locale.KOREA);
		Assertions.assertThat(result).isEqualTo("1,000");
	}
}