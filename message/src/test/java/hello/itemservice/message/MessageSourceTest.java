package hello.itemservice.message;

import static org.assertj.core.api.Assertions.*;

import java.util.Locale;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

@SpringBootTest
class MessageSourceTest {

	@Autowired
	MessageSource messageSource;

	@DisplayName("기본 코드로 메세지를 찾을때")
	@Test
	void testHelloMessage() {
		String actual = messageSource.getMessage("hello", null, null);
		assertThat(actual).isEqualTo("안녕");
	}

	@DisplayName("properties에 code가 없을때")
	@Test
	void notFoundMessageCode() {
		assertThatThrownBy(() -> {
			messageSource.getMessage("no_code", null, null);
		}).isInstanceOf(NoSuchMessageException.class);
	}

	@DisplayName("code 못찾았을때 default message 반환")
	@Test
	void notFoundMessageCodeDefaultMessage() {
		String defaultMessage = "기본 메시지";
		String actual = messageSource.getMessage("no_code", null, defaultMessage, null);
		assertThat(actual).isEqualTo(defaultMessage);
	}

	@DisplayName("argument 포함 메시지 반환 확인")
	@Test
	void testArgumentMessage() {
		String actual = messageSource.getMessage("hello.name", new Object[] {"spring"}, null);
		assertThat(actual).isEqualTo("안녕, spring");
	}

	@DisplayName("기본 메시지 반환")
	@Test
	void testDefaultLang() {
		String message = messageSource.getMessage("hello", null, null);
		String messageLocale = messageSource.getMessage("hello", null, Locale.KOREA);

		assertThat(message).isEqualTo("안녕");
		assertThat(messageLocale).isEqualTo("안녕");
	}

	@DisplayName("locale en 설정")
	void testEnLang() {
		String actual = messageSource.getMessage("hello", null, Locale.ENGLISH);
		assertThat(actual).isEqualTo("Hello");
	}
}
