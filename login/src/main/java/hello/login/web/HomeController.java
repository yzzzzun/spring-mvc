package hello.login.web;

import javax.servlet.http.HttpServletRequest;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

	private final MemberRepository memberRepository;
	private final SessionManager sessionManager;

	// @GetMapping("/")
	public String home() {
		return "home";
	}

	// @GetMapping("/")
	public String homeLoginV1(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {

		if (memberId == null) {
			return "home";
		}

		Member loginMember = memberRepository.findById(memberId);
		if (loginMember == null) {
			return "home";
		}

		model.addAttribute("member", loginMember);
		return "loginHome";

	}

	@GetMapping("/")
	public String homeLoginV2(HttpServletRequest request, Model model) {

		Member loginMember = (Member)sessionManager.getSession(request);

		if (loginMember == null) {
			return "home";
		}

		model.addAttribute("member", loginMember);
		return "loginHome";

	}
}