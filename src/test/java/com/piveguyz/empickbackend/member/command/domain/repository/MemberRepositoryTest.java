package com.piveguyz.empickbackend.member.command.domain.repository;

import com.piveguyz.empickbackend.orgstructure.member.command.domain.aggregate.MemberEntity;
import com.piveguyz.empickbackend.orgstructure.member.command.domain.repository.MemberRepository;
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
        MemberEntity member = MemberEntity.builder()
                .employeeNumber(1001)
                .password("encodedPassword")
                .name("테스트사원")
                .phone("010-1234-5678")
                .pictureUrl("http://example.com/test.jpg")
                .email("test@example.com")
                .address("서울시 테스트구 테스트동")
                .vacationCount(0)
                .hireAt(LocalDateTime.now())
                .status(1)
                .build();

        memberRepository.save(member);

        // when
        Optional<MemberEntity> result = memberRepository.findByEmployeeNumber(1001);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getEmployeeNumber()).isEqualTo(1001);
    }
}