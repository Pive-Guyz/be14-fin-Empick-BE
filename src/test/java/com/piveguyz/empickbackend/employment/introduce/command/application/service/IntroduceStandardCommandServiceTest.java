package com.piveguyz.empickbackend.employment.introduce.command.application.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.employment.introduce.command.application.dto.IntroduceStandardCommandDTO;
import com.piveguyz.empickbackend.employment.introduce.command.domain.aggregate.IntroduceStandardEntity;
import com.piveguyz.empickbackend.employment.introduce.command.domain.repository.IntroduceStandardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("자기소개서 표준 Command Service 테스트")
class IntroduceStandardCommandServiceTest {

    @Mock
    private IntroduceStandardRepository introduceStandardRepository;

    @InjectMocks
    private IntroduceStandardCommandServiceImp introduceStandardCommandService;

    private IntroduceStandardCommandDTO mockDto;
    private IntroduceStandardEntity mockEntity;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        mockDto = IntroduceStandardCommandDTO.builder()
                .content("개발자를 위한 표준 자기소개서 양식")
                .memberId(100)
                .build();

        mockEntity = IntroduceStandardEntity.builder()
                .id(1)
                .content("개발자를 위한 표준 자기소개서 양식")
                .memberId(100)
                .build();
    }

    @Test
    @DisplayName("자기소개서 표준 등록 - 정상적인 데이터로 등록 시 성공")
    void testCreateIntroduceStandard() {
        // Given
        given(introduceStandardRepository.existsByContent("개발자를 위한 표준 자기소개서 양식"))
                .willReturn(false);
        given(introduceStandardRepository.save(any(IntroduceStandardEntity.class)))
                .willReturn(mockEntity);

        // When
        IntroduceStandardCommandDTO result = introduceStandardCommandService.create(mockDto);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("개발자를 위한 표준 자기소개서 양식", result.getContent());
        assertEquals(100, result.getMemberId());

        verify(introduceStandardRepository).existsByContent("개발자를 위한 표준 자기소개서 양식");
        verify(introduceStandardRepository).save(any(IntroduceStandardEntity.class));
    }

    @Test
    @DisplayName("자기소개서 표준 등록 실패 - ID가 포함된 경우 예외 발생")
    void testCreateIntroduceStandardWithId() {
        // Given
        mockDto.setId(1); // ID가 포함된 DTO

        // When & Then
        assertThrows(BusinessException.class, () -> {
            introduceStandardCommandService.create(mockDto);
        });

        verify(introduceStandardRepository, never()).existsByContent(anyString());
        verify(introduceStandardRepository, never()).save(any(IntroduceStandardEntity.class));
    }

    @Test
    @DisplayName("자기소개서 표준 등록 실패 - 중복된 내용인 경우 예외 발생")
    void testCreateIntroduceStandardDuplicate() {
        // Given
        given(introduceStandardRepository.existsByContent("개발자를 위한 표준 자기소개서 양식"))
                .willReturn(true);

        // When & Then
        assertThrows(BusinessException.class, () -> {
            introduceStandardCommandService.create(mockDto);
        });

        verify(introduceStandardRepository).existsByContent("개발자를 위한 표준 자기소개서 양식");
        verify(introduceStandardRepository, never()).save(any(IntroduceStandardEntity.class));
    }

    @Test
    @DisplayName("자기소개서 표준 삭제 - 정상적인 ID로 삭제 시 성공")
    void testDeleteIntroduceStandard() {
        // Given
        given(introduceStandardRepository.findById(1))
                .willReturn(Optional.of(mockEntity));

        // When
        int result = introduceStandardCommandService.delete(1);

        // Then
        assertEquals(1, result);

        verify(introduceStandardRepository).findById(1);
        verify(introduceStandardRepository).delete(mockEntity);
    }

    @Test
    @DisplayName("자기소개서 표준 삭제 실패 - 존재하지 않는 ID로 삭제 시 예외 발생")
    void testDeleteIntroduceStandardNotFound() {
        // Given
        given(introduceStandardRepository.findById(999))
                .willReturn(Optional.empty());

        // When & Then
        assertThrows(BusinessException.class, () -> {
            introduceStandardCommandService.delete(999);
        });

        verify(introduceStandardRepository).findById(999);
        verify(introduceStandardRepository, never()).delete(any(IntroduceStandardEntity.class));
    }
} 