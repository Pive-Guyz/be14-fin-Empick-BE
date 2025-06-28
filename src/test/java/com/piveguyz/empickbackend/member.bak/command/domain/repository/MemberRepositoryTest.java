/*
package com.piveguyz.empickbackend.member.command.domain.repository;

import com.piveguyz.empickbackend.member.command.domain.aggregate.MemberEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("사번으로 회원 조회")
    void findByEmployeeNumber_사번조회_테스트() {
        // given
        MemberEntity member = new MemberEntity();
        member.setEmployeeNumber(1001);
        member.setPassword("encodedPassword");
        member.setName("테스트사원");
        member.setPhone("010-1234-5678");
        member.setPictureUrl("http://example.com/test.jpg");
        member.setEmail("test@example.com");
        member.setAddress("서울시 테스트구 테스트동");
        member.setHireAt(LocalDateTime.now());
        member.setCreatedAt(LocalDateTime.now());
        member.setStatus(1); // 계정 활성화

        memberRepository.save(member);

        // when
        Optional<MemberEntity> result = memberRepository.findByEmployeeNumber(1001);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getEmployeeNumber()).isEqualTo(1001);
    }
}
*/