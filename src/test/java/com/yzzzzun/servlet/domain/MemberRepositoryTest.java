package com.yzzzzun.servlet.domain;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberRepositoryTest {

	MemberRepository memberRepository = MemberRepository.getInstance();

	@AfterEach
	void afterEach() {
		memberRepository.clearStore();
	}

	@DisplayName("")
	@Test
	void testSave() {
		Member member = new Member("hello", 20);
		Member savedMember = memberRepository.save(member);

		Member findMember = memberRepository.findById(savedMember.getId());

		Assertions.assertThat(findMember).isEqualTo(savedMember);
	}

	@DisplayName("")
	@Test
	void testFindAll() {
		Member member1 = new Member("member1", 20);
		Member member2 = new Member("member2", 30);

		memberRepository.save(member1);
		memberRepository.save(member2);

		List<Member> result = memberRepository.findAll();
		Assertions.assertThat(result.size()).isEqualTo(2);
		Assertions.assertThat(result).containsExactly(member1, member2);
	}

}