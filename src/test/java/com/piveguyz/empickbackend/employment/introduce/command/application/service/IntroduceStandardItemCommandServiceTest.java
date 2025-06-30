package com.piveguyz.empickbackend.employment.introduce.command.application.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.employment.introduce.command.application.dto.IntroduceStandardItemCommandDTO;
import com.piveguyz.empickbackend.employment.introduce.command.domain.aggregate.IntroduceStandardItemEntity;
import com.piveguyz.empickbackend.employment.introduce.command.domain.repository.IntroduceStandardItemRepository;
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
@DisplayName("자기소개 표준 항목 Command Service 테스트")
class IntroduceStandardItemCommandServiceTest {

    @Mock
    private IntroduceStandardItemRepository introduceStandardItemRepository;

    @InjectMocks
    private IntroduceStandardItemCommandServiceImp introduceStandardItemCommandService;

    private IntroduceStandardItemCommandDTO mockDto;
    private IntroduceStandardItemEntity mockEntity;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        mockDto = IntroduceStandardItemCommandDTO.builder()
                .content("지원동기를 작성해주세요.")
                .memberId(1)
                .introduceStandardId(1)
                .build();

        mockEntity = IntroduceStandardItemEntity.builder()
                .id(1)
                .content("지원동기를 작성해주세요.")
                .memberId(1)
                .introduceStandardId(1)
                .build();
    }

    @Test
    @DisplayName("표준 항목 등록 - 정상적인 데이터로 등록 시 성공")
    void testCreateStandardItem() {
        // Given
        given(introduceStandardItemRepository.existsByContent("지원동기를 작성해주세요."))
                .willReturn(false);
        given(introduceStandardItemRepository.save(any(IntroduceStandardItemEntity.class)))
                .willReturn(mockEntity);

        // When
        IntroduceStandardItemCommandDTO result = introduceStandardItemCommandService.create(mockDto);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("지원동기를 작성해주세요.", result.getContent());
        assertEquals(1, result.getMemberId());
        assertEquals(1, result.getIntroduceStandardId());

        verify(introduceStandardItemRepository).existsByContent("지원동기를 작성해주세요.");
        verify(introduceStandardItemRepository).save(any(IntroduceStandardItemEntity.class));
    }

    @Test
    @DisplayName("표준 항목 등록 실패 - ID가 포함된 경우 예외 발생")
    void testCreateStandardItemWithId() {
        // Given
        mockDto.setId(1); // ID가 포함된 DTO

        // When & Then
        assertThrows(BusinessException.class, () -> {
            introduceStandardItemCommandService.create(mockDto);
        });

        verify(introduceStandardItemRepository, never()).existsByContent(anyString());
        verify(introduceStandardItemRepository, never()).save(any(IntroduceStandardItemEntity.class));
    }

    @Test
    @DisplayName("표준 항목 등록 실패 - 중복된 내용인 경우 예외 발생")
    void testCreateStandardItemDuplicate() {
        // Given
        given(introduceStandardItemRepository.existsByContent("지원동기를 작성해주세요."))
                .willReturn(true);

        // When & Then
        assertThrows(BusinessException.class, () -> {
            introduceStandardItemCommandService.create(mockDto);
        });

        verify(introduceStandardItemRepository).existsByContent("지원동기를 작성해주세요.");
        verify(introduceStandardItemRepository, never()).save(any(IntroduceStandardItemEntity.class));
    }

    @Test
    @DisplayName("표준 항목 수정 - 정상적인 데이터로 수정 시 성공")
    void testUpdateStandardItem() {
        // Given
        IntroduceStandardItemCommandDTO updateDto = IntroduceStandardItemCommandDTO.builder()
                .content("수정된 지원동기")
                .memberId(2)
                .introduceStandardId(2)
                .build();

        given(introduceStandardItemRepository.findById(1))
                .willReturn(Optional.of(mockEntity));
        given(introduceStandardItemRepository.save(any(IntroduceStandardItemEntity.class)))
                .willReturn(mockEntity);

        // When
        IntroduceStandardItemCommandDTO result = introduceStandardItemCommandService.update(1, updateDto);

        // Then
        assertNotNull(result);
        verify(introduceStandardItemRepository).findById(1);
        verify(introduceStandardItemRepository).save(any(IntroduceStandardItemEntity.class));
    }

    @Test
    @DisplayName("표준 항목 수정 실패 - 존재하지 않는 ID인 경우 예외 발생")
    void testUpdateStandardItemNotFound() {
        // Given
        IntroduceStandardItemCommandDTO updateDto = IntroduceStandardItemCommandDTO.builder()
                .content("수정된 지원동기")
                .build();

        given(introduceStandardItemRepository.findById(999))
                .willReturn(Optional.empty());

        // When & Then
        assertThrows(BusinessException.class, () -> {
            introduceStandardItemCommandService.update(999, updateDto);
        });

        verify(introduceStandardItemRepository).findById(999);
        verify(introduceStandardItemRepository, never()).save(any(IntroduceStandardItemEntity.class));
    }

    @Test
    @DisplayName("표준 항목 삭제 - 정상적인 ID로 삭제 시 성공")
    void testDeleteStandardItem() {
        // Given
        given(introduceStandardItemRepository.findById(1))
                .willReturn(Optional.of(mockEntity));

        // When
        int result = introduceStandardItemCommandService.delete(1);

        // Then
        assertEquals(1, result);
        verify(introduceStandardItemRepository).findById(1);
        verify(introduceStandardItemRepository).delete(mockEntity);
    }

    @Test
    @DisplayName("표준 항목 삭제 실패 - 존재하지 않는 ID인 경우 예외 발생")
    void testDeleteStandardItemNotFound() {
        // Given
        given(introduceStandardItemRepository.findById(999))
                .willReturn(Optional.empty());

        // When & Then
        assertThrows(BusinessException.class, () -> {
            introduceStandardItemCommandService.delete(999);
        });

        verify(introduceStandardItemRepository).findById(999);
        verify(introduceStandardItemRepository, never()).delete(any(IntroduceStandardItemEntity.class));
    }
} 