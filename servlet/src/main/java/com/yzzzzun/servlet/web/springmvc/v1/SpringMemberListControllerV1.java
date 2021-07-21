package com.yzzzzun.servlet.web.springmvc.v1;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yzzzzun.servlet.domain.Member;
import com.yzzzzun.servlet.domain.MemberRepository;

@Controller
public class SpringMemberListControllerV1 {

	private MemberRepository memberRepository = MemberRepository.getInstance();

	@RequestMapping("/springmvc/v1/members")
	public ModelAndView process(Map<String, String> paramMap) {
		List<Member> members = memberRepository.findAll();

		ModelAndView modelAndView = new ModelAndView("members");
		modelAndView.addObject("members", members);
		return modelAndView;
	}
}
