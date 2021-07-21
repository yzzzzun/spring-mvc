package com.yzzzzun.servlet.web.frontcontroller.v3.controller;

import java.util.List;
import java.util.Map;

import com.yzzzzun.servlet.domain.Member;
import com.yzzzzun.servlet.domain.MemberRepository;
import com.yzzzzun.servlet.web.frontcontroller.ModelView;
import com.yzzzzun.servlet.web.frontcontroller.v3.ControllerV3;

public class MemberListControllerV3 implements ControllerV3 {

	private MemberRepository memberRepository = MemberRepository.getInstance();

	@Override
	public ModelView process(Map<String, String> paramMap) {
		List<Member> members = memberRepository.findAll();

		ModelView modelView = new ModelView("members");
		modelView.getModel().put("members", members);
		return modelView;
	}
}
