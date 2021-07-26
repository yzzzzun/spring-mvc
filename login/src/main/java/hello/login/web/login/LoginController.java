package hello.login.web.login;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {
	private final LoginService loginService;

	@GetMapping("/login")
	public String loginForm(@ModelAttribute("loginForm") LoginForm form){
		return "login/loginForm";
	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute LoginForm form , BindingResult bindingResult, HttpServletResponse response){
		if(bindingResult.hasErrors()){
			return "login/loginForm";
		}

		Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
		if(loginMember==null){
			bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
			return "login/loginForm";
		}

		// Login 성공처리, 쿠키에 시간정보 없으면 SessionCookie(브라우저 종료시 사라짐)
		Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
		response.addCookie(idCookie);


		return "redirect:/";
	}
	
	@PostMapping("/logout")
	public String logout(HttpServletResponse response, String cookieName){
		expireCookie(response,"memberId");
		return "home";
	}

	private void expireCookie(HttpServletResponse response, String cookieName) {
		Cookie cookie= new Cookie(cookieName, null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
}