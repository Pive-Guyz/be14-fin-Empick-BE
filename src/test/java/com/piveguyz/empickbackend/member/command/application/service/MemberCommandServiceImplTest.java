package com.piveguyz.empickbackend.member.command.application.service;

import com.piveguyz.empickbackend.auth.facade.AuthFacade;
import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.employment.mail.facade.MailFacade;
import com.piveguyz.empickbackend.orgstructure.member.command.application.dto.MemberSignUpRequestDTO;
import com.piveguyz.empickbackend.orgstructure.member.command.application.service.MemberCommandServiceImpl;
import com.piveguyz.empickbackend.orgstructure.member.command.domain.aggregate.MemberEntity;
import com.piveguyz.empickbackend.orgstructure.member.command.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

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

    @Mock
    private AuthFacade authFacade;

    @Mock
    private MailFacade mailFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("이메일 중복 체크 테스트")
    void signUp_emailDuplicate() {
        // given
        MemberSignUpRequestDTO request = new MemberSignUpRequestDTO();
        request.setEmail("duplicate@domain.com");
        request.setPassword("password");
        request.setName("홍길동");
        request.setPhone("010-1234-5678");
        request.setAddress("서울시");
        request.setHireAt(LocalDateTime.now());
        request.setStatus(1);

        // Mock MultipartFile
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(false);
        request.setProfileImage(mockFile);

        // Mock AuthFacade
        when(authFacade.getCurrentMemberId()).thenReturn(1);
        doNothing().when(authFacade).checkHasRole(any());

        // Mock Repository
        when(memberRepository.findById(1)).thenReturn(Optional.of(new MemberEntity()));
        when(memberRepository.existsByEmail("duplicate@domain.com")).thenReturn(true);

        // when & then
        assertThrows(BusinessException.class, () -> memberCommandService.signUp(request));
    }

    @Test
    @DisplayName("프로필 이미지 필수 체크 테스트")
    void signUp_profileImageRequired() {
        // given
        MemberSignUpRequestDTO request = new MemberSignUpRequestDTO();
        request.setEmail("test@domain.com");
        request.setPassword("password");
        request.setName("홍길동");
        request.setPhone("010-1234-5678");
        request.setAddress("서울시");
        request.setHireAt(LocalDateTime.now());
        request.setStatus(1);

        // Mock empty MultipartFile
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(true);
        request.setProfileImage(mockFile);

        // Mock AuthFacade
        when(authFacade.getCurrentMemberId()).thenReturn(1);
        doNothing().when(authFacade).checkHasRole(any());

        // Mock Repository
        when(memberRepository.findById(1)).thenReturn(Optional.of(new MemberEntity()));
        when(memberRepository.existsByEmail("test@domain.com")).thenReturn(false);

        // when & then
        assertThrows(BusinessException.class, () -> memberCommandService.signUp(request));
    }
}