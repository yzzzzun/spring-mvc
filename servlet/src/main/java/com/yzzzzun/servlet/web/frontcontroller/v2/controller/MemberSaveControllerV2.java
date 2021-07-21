package com.yzzzzun.servlet.web.frontcontroller.v2.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yzzzzun.servlet.domain.Member;
import com.yzzzzun.servlet.domain.MemberRepository;
import com.yzzzzun.servlet.web.frontcontroller.MyView;
import com.yzzzzun.servlet.web.frontcontroller.v2.ControllerV2;

public class MemberSaveControllerV2 implements ControllerV2 {
	private MemberRepository memberRepository = MemberRepository.getInstance();

	@Override
	public MyView process(HttpServletRequest request, HttpServletResponse response) throws
		ServletException,
		IOException {
		String username = request.getParameter("username");
		int age = Integer.parseInt(request.getParameter("age"));
		Member member = new Member(username, age);

		memberRepository.save(member);
		request.setAttribute("member", member);
		
		return new MyView("/WEB-INF/views/save-result.jsp");
	}
}
