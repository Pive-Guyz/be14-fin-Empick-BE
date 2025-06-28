package com.piveguyz.empickbackend.employment.introduce.command.application.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.employment.introduce.command.application.dto.IntroduceCommandDTO;
import com.piveguyz.empickbackend.employment.introduce.command.domain.aggregate.IntroduceEntity;
import com.piveguyz.empickbackend.employment.introduce.command.domain.repository.IntroduceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("자기소개서 Command Service 테스트")
class IntroduceCommandServiceTest {

    @Mock
    private IntroduceRepository introduceRepository;

    @InjectMocks
    private IntroduceCommandServiceImp introduceCommandService;

    private IntroduceCommandDTO mockDto;
    private IntroduceEntity mockEntity;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        mockDto = IntroduceCommandDTO.builder()
                .applicantId(10)
                .introduceTemplateId(1)
                .content("저는 열정적인 개발자입니다.")
                .build();

        mockEntity = IntroduceEntity.builder()
                .id(1)
                .applicantId(10)
                .introduceTemplateId(1)
                .content("저는 열정적인 개발자입니다.")
                .build();
    }

    @Test
    @DisplayName("자기소개서 등록 - 정상적인 데이터로 등록 시 성공")
    void testCreateIntroduce() {
        // Given
        given(introduceRepository.existsByApplicantIdAndIntroduceTemplateId(10, 1))
                .willReturn(false);
        given(introduceRepository.save(any(IntroduceEntity.class)))
                .willReturn(mockEntity);

        // When
        IntroduceCommandDTO result = introduceCommandService.create(mockDto);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(10, result.getApplicantId());
        assertEquals(1, result.getIntroduceTemplateId());
        assertEquals("저는 열정적인 개발자입니다.", result.getContent());

        verify(introduceRepository).existsByApplicantIdAndIntroduceTemplateId(10, 1);
        verify(introduceRepository).save(any(IntroduceEntity.class));
    }

    @Test
    @DisplayName("자기소개서 등록 실패 - ID가 포함된 경우 예외 발생")
    void testCreateIntroduceWithId() {
        // Given
        mockDto.setId(1); // ID가 포함된 DTO

        // When & Then
        assertThrows(BusinessException.class, () -> {
            introduceCommandService.create(mockDto);
        });

        verify(introduceRepository, never()).existsByApplicantIdAndIntroduceTemplateId(anyInt(), anyInt());
        verify(introduceRepository, never()).save(any(IntroduceEntity.class));
    }

    @Test
    @DisplayName("자기소개서 등록 실패 - 중복된 지원자와 템플릿 조합인 경우 예외 발생")
    void testCreateIntroduceDuplicate() {
        // Given
        given(introduceRepository.existsByApplicantIdAndIntroduceTemplateId(10, 1))
                .willReturn(true);

        // When & Then
        assertThrows(BusinessException.class, () -> {
            introduceCommandService.create(mockDto);
        });

        verify(introduceRepository).existsByApplicantIdAndIntroduceTemplateId(10, 1);
        verify(introduceRepository, never()).save(any(IntroduceEntity.class));
    }
} 