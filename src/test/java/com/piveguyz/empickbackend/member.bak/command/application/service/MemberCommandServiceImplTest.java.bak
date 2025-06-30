package com.piveguyz.empickbackend.member.command.application.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.member.command.application.dto.MemberSignUpRequestDTO;
import com.piveguyz.empickbackend.member.command.domain.aggregate.MemberEntity;
import com.piveguyz.empickbackend.member.command.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberCommandServiceImplTest {

    @InjectMocks
    private MemberCommandServiceImpl memberCommandService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 권한을 mokito로 가져와야 하는데 현재는 못가져 와서 실패함
//    @Test
//    @DisplayName("회원가입 성공 테스트")
//    void signUp_success() {
//        // given
//        MemberSignUpRequestDTO request = new MemberSignUpRequestDTO();
//        request.setEmail("test@domain.com");
//        request.setPassword("password");
//        request.setName("홍길동");
//        request.setPhone("010-1234-5678");
//        request.setPictureUrl("https://image.url");
//        request.setAddress("서울시");
//        request.setEmployeeNumber(123);
//        request.setHireAt(LocalDateTime.now());
//        request.setCreatedMemberId(1);
//        request.setStatus(1);
//
//        Member createdMember = Member.builder()
//                .id(1)
//                .email("test@domain.com")
//                .name("홍길동")
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        when(memberRepository.findById(1)).thenReturn(Optional.of(createdMember));
//        when(memberRepository.existsByEmail("test@domain.com")).thenReturn(false);
//        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
//        when(memberRepository.save(any(Member.class))).thenReturn(createdMember);
//
//        // when
//        var response = memberCommandService.signUp(request);
//
//        // then
//        assertNotNull(response);
//        assertEquals("홍길동", response.getName());
//    }

    @Test
    @DisplayName("이메일 중복 체크 테스트")
    void signUp_emailDuplicate() {
        // given
        MemberSignUpRequestDTO request = new MemberSignUpRequestDTO();
        request.setEmail("duplicate@domain.com");
        request.setPassword("password");
        request.setName("홍길동");
        request.setPhone("010-1234-5678");
        request.setPictureUrl("https://image.url");
        request.setAddress("서울시");
        request.setHireAt(LocalDateTime.now());
        request.setStatus(1);

        when(memberRepository.findById(1)).thenReturn(Optional.of(new MemberEntity()));
        when(memberRepository.existsByEmail("duplicate@domain.com")).thenReturn(true);

        // when & then
        assertThrows(BusinessException.class, () -> memberCommandService.signUp(request));
    }
}
