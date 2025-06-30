package com.piveguyz.empickbackend.employment.applicant.command.application.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.employment.applicant.command.application.dto.ApplicationResponseCommandDTO;
import com.piveguyz.empickbackend.employment.applicant.command.domain.aggregate.ApplicationResponseEntity;
import com.piveguyz.empickbackend.employment.applicant.command.domain.repository.ApplicationResponseRepository;
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
@DisplayName("지원서 응답 Command Service 테스트")
class ApplicationResponseCommandServiceTest {

    @Mock
    private ApplicationResponseRepository applicationResponseRepository;

    @InjectMocks
    private ApplicationResponseCommandServiceImp applicationResponseCommandService;

    private ApplicationResponseEntity mockEntity;
    private ApplicationResponseCommandDTO mockDto;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        // Mock DTO 설정
        mockDto = ApplicationResponseCommandDTO.builder()
                .applicationId(1)
                .applicationItemId(1)
                .content("지원 동기 및 포부를 작성합니다.")
                .build();

        // Mock Entity 설정
        mockEntity = ApplicationResponseEntity.builder()
                .id(1)
                .applicationId(1)
                .applicationItemId(1)
                .content("지원 동기 및 포부를 작성합니다.")
                .build();
    }

    @Test
    @DisplayName("지원서 응답 저장 - 정상적인 데이터로 응답 저장 시 성공")
    void testSaveResponse() {
        // Given
        given(applicationResponseRepository.existsByApplicationIdAndApplicationItemId(1, 1))
                .willReturn(false);
        given(applicationResponseRepository.save(any(ApplicationResponseEntity.class)))
                .willReturn(mockEntity);

        // When
        ApplicationResponseCommandDTO result = applicationResponseCommandService.saveResponse(mockDto);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getApplicationId());
        assertEquals(1, result.getApplicationItemId());
        assertEquals("지원 동기 및 포부를 작성합니다.", result.getContent());

        verify(applicationResponseRepository).existsByApplicationIdAndApplicationItemId(1, 1);
        verify(applicationResponseRepository).save(any(ApplicationResponseEntity.class));
    }

    @Test
    @DisplayName("지원서 응답 저장 실패 - 응답 내용이 null인 경우 예외 발생")
    void testSaveResponseWithNullContent() {
        // Given
        ApplicationResponseCommandDTO nullContentDto = ApplicationResponseCommandDTO.builder()
                .applicationId(1)
                .applicationItemId(1)
                .content(null)
                .build();

        // When & Then
        assertThrows(BusinessException.class, () -> {
            applicationResponseCommandService.saveResponse(nullContentDto);
        });

        verify(applicationResponseRepository, never()).existsByApplicationIdAndApplicationItemId(anyInt(), anyInt());
        verify(applicationResponseRepository, never()).save(any(ApplicationResponseEntity.class));
    }

    @Test
    @DisplayName("지원서 응답 저장 실패 - 응답 내용이 빈 문자열인 경우 예외 발생")
    void testSaveResponseWithEmptyContent() {
        // Given
        ApplicationResponseCommandDTO emptyContentDto = ApplicationResponseCommandDTO.builder()
                .applicationId(1)
                .applicationItemId(1)
                .content("")
                .build();

        // When & Then
        assertThrows(BusinessException.class, () -> {
            applicationResponseCommandService.saveResponse(emptyContentDto);
        });

        verify(applicationResponseRepository, never()).existsByApplicationIdAndApplicationItemId(anyInt(), anyInt());
        verify(applicationResponseRepository, never()).save(any(ApplicationResponseEntity.class));
    }

    @Test
    @DisplayName("지원서 응답 저장 실패 - 응답 내용이 공백만 있는 경우 예외 발생")
    void testSaveResponseWithWhitespaceContent() {
        // Given
        ApplicationResponseCommandDTO whitespaceContentDto = ApplicationResponseCommandDTO.builder()
                .applicationId(1)
                .applicationItemId(1)
                .content("   ")
                .build();

        // When & Then
        assertThrows(BusinessException.class, () -> {
            applicationResponseCommandService.saveResponse(whitespaceContentDto);
        });

        verify(applicationResponseRepository, never()).existsByApplicationIdAndApplicationItemId(anyInt(), anyInt());
        verify(applicationResponseRepository, never()).save(any(ApplicationResponseEntity.class));
    }

    @Test
    @DisplayName("지원서 응답 저장 실패 - 중복된 응답이 이미 존재하는 경우 예외 발생")
    void testSaveResponseDuplicate() {
        // Given
        given(applicationResponseRepository.existsByApplicationIdAndApplicationItemId(1, 1))
                .willReturn(true);

        // When & Then
        assertThrows(BusinessException.class, () -> {
            applicationResponseCommandService.saveResponse(mockDto);
        });

        verify(applicationResponseRepository).existsByApplicationIdAndApplicationItemId(1, 1);
        verify(applicationResponseRepository, never()).save(any(ApplicationResponseEntity.class));
    }

    @Test
    @DisplayName("지원서 응답 저장 - 긴 내용의 응답 저장 시 성공")
    void testSaveResponseWithLongContent() {
        // Given
        String longContent = "이것은 매우 긴 내용의 지원서 응답입니다. ".repeat(100);
        ApplicationResponseCommandDTO longContentDto = ApplicationResponseCommandDTO.builder()
                .applicationId(2)
                .applicationItemId(2)
                .content(longContent)
                .build();

        ApplicationResponseEntity longContentEntity = ApplicationResponseEntity.builder()
                .id(2)
                .applicationId(2)
                .applicationItemId(2)
                .content(longContent)
                .build();

        given(applicationResponseRepository.existsByApplicationIdAndApplicationItemId(2, 2))
                .willReturn(false);
        given(applicationResponseRepository.save(any(ApplicationResponseEntity.class)))
                .willReturn(longContentEntity);

        // When
        ApplicationResponseCommandDTO result = applicationResponseCommandService.saveResponse(longContentDto);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getApplicationId());
        assertEquals(2, result.getApplicationItemId());
        assertEquals(longContent, result.getContent());

        verify(applicationResponseRepository).existsByApplicationIdAndApplicationItemId(2, 2);
        verify(applicationResponseRepository).save(any(ApplicationResponseEntity.class));
    }
} 