package hello.login.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class MemberRepository {
	private static Map<Long, Member> store = new ConcurrentHashMap<>();
	private static long sequance = 0L;

	public Member save(Member member) {
		member.setId(++sequance);
		log.info("save : member = {}", member);
		store.put(member.getId(), member);
		return member;
	}

	public Member findById(Long id) {
		return store.get(id);
	}

	public Optional<Member> findByLoginId(String loginId) {
		return findAll().stream()
			.filter(member -> member.getLoginId().equals(loginId))
			.findFirst();
	}

	public List<Member> findAll() {
		return new ArrayList<>(store.values());
	}

	public void clearStore(){
		store.clear();
	}
}
