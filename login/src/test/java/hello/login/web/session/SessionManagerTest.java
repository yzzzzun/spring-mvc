package hello.login.web.session;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import hello.login.domain.member.Member;

class SessionManagerTest {

	SessionManager sessionManager = new SessionManager();
	
	@DisplayName("session 생성 테스트")
	@Test
	void testSession() {

		MockHttpServletResponse response = new MockHttpServletResponse();
		
		Member member = new Member();
		member.setName("test");
		member.setLoginId("test");
		member.setPassword("test!");
		sessionManager.createSession(member, response);

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(response.getCookies());
		Member findMember = (Member)sessionManager.getSession(request);

		assertThat(findMember).isEqualTo(member);

		sessionManager.expire(request);
		Object expired = sessionManager.getSession(request);
		assertThat(expired).isNull();
	}
	
}