package com.piveguyz.empickbackend.employment.introduce.command.application.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.employment.introduce.command.application.dto.IntroduceRatingResultCreateCommandDTO;
import com.piveguyz.empickbackend.employment.introduce.command.application.dto.IntroduceRatingResultUpdateCommandDTO;
import com.piveguyz.empickbackend.employment.introduce.command.application.mapper.IntroduceRatingResultCommandMapper;
import com.piveguyz.empickbackend.employment.introduce.command.domain.aggregate.IntroduceRatingResultEntity;
import com.piveguyz.empickbackend.employment.introduce.command.domain.repository.IntroduceRatingResultRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("자기소개서 평가 결과 Command Service 테스트")
class IntroduceRatingResultCommandServiceTest {

    @Mock
    private IntroduceRatingResultRepository introduceRatingResultRepository;

    @InjectMocks
    private IntroduceRatingResultCommandServiceImp introduceRatingResultCommandService;

    private IntroduceRatingResultCreateCommandDTO mockCreateDto;
    private IntroduceRatingResultUpdateCommandDTO mockUpdateDto;
    private IntroduceRatingResultEntity mockEntity;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        mockCreateDto = IntroduceRatingResultCreateCommandDTO.builder()
                .memberId(100)
                .content("우수한 자기소개서입니다.")
                .ratingScore(85)
                .introduceStandardId(1)
                .introduceId(1)
                .updatedBy(100)
                .build();

        mockUpdateDto = IntroduceRatingResultUpdateCommandDTO.builder()
                .content("수정된 평가 내용입니다.")
                .ratingScore(90)
                .build();

        mockEntity = IntroduceRatingResultEntity.builder()
                .id(1)
                .memberId(100)
                .content("우수한 자기소개서입니다.")
                .ratingScore(85)
                .introduceStandardId(1)
                .introduceId(1)
                .updatedBy(100)
                .build();
    }

    @Test
    @DisplayName("자기소개서 평가 결과 등록 - 정상적인 데이터로 등록 시 성공")
    void testCreateIntroduceRatingResult() {
        // Given
        try (MockedStatic<IntroduceRatingResultCommandMapper> mapperMock = mockStatic(IntroduceRatingResultCommandMapper.class)) {
            
            // 매퍼 정적 메서드 목업 설정
            mapperMock.when(() -> IntroduceRatingResultCommandMapper.toEntity(any(IntroduceRatingResultCreateCommandDTO.class)))
                    .thenReturn(mockEntity);
            
            IntroduceRatingResultCreateCommandDTO expectedResult = IntroduceRatingResultCreateCommandDTO.builder()
                    .id(1)
                    .memberId(100)
                    .content("우수한 자기소개서입니다.")
                    .ratingScore(85)
                    .introduceStandardId(1)
                    .introduceId(1)
                    .updatedBy(100)
                    .build();
            
            mapperMock.when(() -> IntroduceRatingResultCommandMapper.toCreateDto(any(IntroduceRatingResultEntity.class)))
                    .thenReturn(expectedResult);
            
            given(introduceRatingResultRepository.save(any(IntroduceRatingResultEntity.class)))
                    .willReturn(mockEntity);

            // When
            IntroduceRatingResultCreateCommandDTO result = 
                    introduceRatingResultCommandService.create(mockCreateDto);

            // Then
            assertNotNull(result);
            assertEquals(1, result.getId());
            assertEquals(100, result.getMemberId());
            assertEquals("우수한 자기소개서입니다.", result.getContent());
            assertEquals(85, result.getRatingScore());

            verify(introduceRatingResultRepository).save(any(IntroduceRatingResultEntity.class));
        }
    }

    @Test
    @DisplayName("자기소개서 평가 결과 수정 - 정상적인 데이터로 수정 시 성공")
    void testUpdateIntroduceRatingResult() {
        // Given
        try (MockedStatic<IntroduceRatingResultCommandMapper> mapperMock = mockStatic(IntroduceRatingResultCommandMapper.class)) {
            
            IntroduceRatingResultUpdateCommandDTO expectedResult = IntroduceRatingResultUpdateCommandDTO.builder()
                    .content("수정된 평가 내용입니다.")
                    .ratingScore(90)
                    .build();
            
            mapperMock.when(() -> IntroduceRatingResultCommandMapper.toUpdateDto(any(IntroduceRatingResultEntity.class)))
                    .thenReturn(expectedResult);
            
            given(introduceRatingResultRepository.findById(1))
                    .willReturn(Optional.of(mockEntity));
            given(introduceRatingResultRepository.save(any(IntroduceRatingResultEntity.class)))
                    .willReturn(mockEntity);

            // When
            IntroduceRatingResultUpdateCommandDTO result = 
                    introduceRatingResultCommandService.update(1, mockUpdateDto);

            // Then
            assertNotNull(result);
            assertEquals("수정된 평가 내용입니다.", result.getContent());
            assertEquals(90, result.getRatingScore());

            verify(introduceRatingResultRepository).findById(1);
            verify(introduceRatingResultRepository).save(any(IntroduceRatingResultEntity.class));
        }
    }

    @Test
    @DisplayName("자기소개서 평가 결과 삭제 - 정상적인 ID로 삭제 시 성공")
    void testDeleteIntroduceRatingResult() {
        // Given
        given(introduceRatingResultRepository.findById(1))
                .willReturn(Optional.of(mockEntity));

        // When
        Integer result = introduceRatingResultCommandService.delete(1);

        // Then
        assertEquals(1, result);

        verify(introduceRatingResultRepository).findById(1);
        verify(introduceRatingResultRepository).delete(mockEntity);
    }

    @Test
    @DisplayName("자기소개서 평가 결과 수정 실패 - 존재하지 않는 ID로 수정 시 예외 발생")
    void testUpdateIntroduceRatingResultNotFound() {
        // Given
        given(introduceRatingResultRepository.findById(999))
                .willReturn(Optional.empty());

        // When & Then
        assertThrows(BusinessException.class, () -> {
            introduceRatingResultCommandService.update(999, mockUpdateDto);
        });

        verify(introduceRatingResultRepository).findById(999);
        verify(introduceRatingResultRepository, never()).save(any(IntroduceRatingResultEntity.class));
    }

    @Test
    @DisplayName("자기소개서 평가 결과 삭제 실패 - 존재하지 않는 ID로 삭제 시 예외 발생")
    void testDeleteIntroduceRatingResultNotFound() {
        // Given
        given(introduceRatingResultRepository.findById(999))
                .willReturn(Optional.empty());

        // When & Then
        assertThrows(BusinessException.class, () -> {
            introduceRatingResultCommandService.delete(999);
        });

        verify(introduceRatingResultRepository).findById(999);
        verify(introduceRatingResultRepository, never()).delete(any(IntroduceRatingResultEntity.class));
    }
} 