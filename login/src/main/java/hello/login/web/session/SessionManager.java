package hello.login.web.session;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class SessionManager {

	private static final String SESSION_COOKIE_NAME = "mySessionId";
	private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

	public void createSession(Object value, HttpServletResponse response){
		String sessionId = UUID.randomUUID().toString();
		sessionStore.put(sessionId, value);
		Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
		response.addCookie(cookie);
	}

	public Object getSession(HttpServletRequest request){
		Cookie sessionCookie = findCookie(request);
		if(sessionCookie == null){
			return null;
		}
		return sessionStore.get(sessionCookie.getValue());
	}

	private Cookie findCookie(HttpServletRequest request) {
		return Arrays.stream(request.getCookies())
			.filter(cookie -> cookie.getName().equals(SESSION_COOKIE_NAME))
			.findAny()
			.orElse(null);
	}

	public void expire(HttpServletRequest request){
		Cookie sessionCookie = findCookie(request);
		if(sessionCookie != null){
			sessionStore.remove(sessionCookie.getValue());
		}
	}
}
