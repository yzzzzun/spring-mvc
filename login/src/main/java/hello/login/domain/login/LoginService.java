package hello.login.domain.login;

import java.util.Optional;

import org.springframework.stereotype.Service;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
	private final MemberRepository memberRepository;

	public Member login(String loginId, String password){
		return memberRepository.findByLoginId(loginId)
			.filter(member -> member.getPassword().equals(password))
			.orElse(null);
	}
}
