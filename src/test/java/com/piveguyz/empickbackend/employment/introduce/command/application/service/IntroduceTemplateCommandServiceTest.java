package com.piveguyz.empickbackend.employment.introduce.command.application.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.employment.introduce.command.application.dto.IntroduceTemplateCommandDTO;
import com.piveguyz.empickbackend.employment.introduce.command.domain.aggregate.IntroduceTemplateEntity;
import com.piveguyz.empickbackend.employment.introduce.command.domain.aggregate.IntroduceTemplateItemEntity;
import com.piveguyz.empickbackend.employment.introduce.command.domain.repository.IntroduceTemplateRepository;
import com.piveguyz.empickbackend.employment.introduce.command.domain.repository.IntroduceTemplateItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("자기소개 템플릿 Command Service 테스트")
class IntroduceTemplateCommandServiceTest {

    @Mock
    private IntroduceTemplateRepository introduceTemplateRepository;

    @Mock
    private IntroduceTemplateItemRepository introduceTemplateItemRepository;

    @InjectMocks
    private IntroduceTemplateCommandServiceImp introduceTemplateCommandService;

    private IntroduceTemplateCommandDTO mockDto;
    private IntroduceTemplateEntity mockEntity;
    private IntroduceTemplateItemEntity mockItemEntity;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        mockDto = IntroduceTemplateCommandDTO.builder()
                .title("신입 개발자 템플릿")
                .memberId(1)
                .itemIds(Arrays.asList(1, 2, 3))
                .build();

        mockEntity = IntroduceTemplateEntity.builder()
                .id(1)
                .title("신입 개발자 템플릿")
                .memberId(1)
                .build();

        mockItemEntity = IntroduceTemplateItemEntity.builder()
                .id(1)
                .title("지원동기")
                .introduceTemplateId(null)
                .build();
    }

    @Test
    @DisplayName("템플릿 등록 - 정상적인 데이터로 등록 시 성공")
    void testCreateTemplate() {
        // Given
        given(introduceTemplateRepository.existsByTitle("신입 개발자 템플릿"))
                .willReturn(false);
        given(introduceTemplateRepository.save(any(IntroduceTemplateEntity.class)))
                .willReturn(mockEntity);
        given(introduceTemplateItemRepository.findById(1))
                .willReturn(Optional.of(mockItemEntity));
        given(introduceTemplateItemRepository.findById(2))
                .willReturn(Optional.of(mockItemEntity));
        given(introduceTemplateItemRepository.findById(3))
                .willReturn(Optional.of(mockItemEntity));

        // When
        IntroduceTemplateCommandDTO result = introduceTemplateCommandService.create(mockDto);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("신입 개발자 템플릿", result.getTitle());
        assertEquals(1, result.getMemberId());
        assertEquals(Arrays.asList(1, 2, 3), result.getItemIds());

        verify(introduceTemplateRepository).existsByTitle("신입 개발자 템플릿");
        verify(introduceTemplateRepository).save(any(IntroduceTemplateEntity.class));
        verify(introduceTemplateItemRepository, times(3)).findById(anyInt());
        verify(introduceTemplateItemRepository, times(3)).save(any(IntroduceTemplateItemEntity.class));
    }

    @Test
    @DisplayName("템플릿 등록 실패 - ID가 포함된 경우 예외 발생")
    void testCreateTemplateWithId() {
        // Given
        mockDto.setId(1); // ID가 포함된 DTO

        // When & Then
        assertThrows(BusinessException.class, () -> {
            introduceTemplateCommandService.create(mockDto);
        });

        verify(introduceTemplateRepository, never()).existsByTitle(anyString());
        verify(introduceTemplateRepository, never()).save(any(IntroduceTemplateEntity.class));
    }

    @Test
    @DisplayName("템플릿 등록 실패 - 중복된 제목인 경우 예외 발생")
    void testCreateTemplateDuplicate() {
        // Given
        given(introduceTemplateRepository.existsByTitle("신입 개발자 템플릿"))
                .willReturn(true);

        // When & Then
        assertThrows(BusinessException.class, () -> {
            introduceTemplateCommandService.create(mockDto);
        });

        verify(introduceTemplateRepository).existsByTitle("신입 개발자 템플릿");
        verify(introduceTemplateRepository, never()).save(any(IntroduceTemplateEntity.class));
    }

    @Test
    @DisplayName("템플릿 등록 실패 - 존재하지 않는 템플릿 항목 ID인 경우 예외 발생")
    void testCreateTemplateItemNotFound() {
        // Given
        given(introduceTemplateRepository.existsByTitle("신입 개발자 템플릿"))
                .willReturn(false);
        given(introduceTemplateRepository.save(any(IntroduceTemplateEntity.class)))
                .willReturn(mockEntity);
        given(introduceTemplateItemRepository.findById(1))
                .willReturn(Optional.empty()); // 첫 번째 아이템이 존재하지 않음

        // When & Then
        assertThrows(BusinessException.class, () -> {
            introduceTemplateCommandService.create(mockDto);
        });

        verify(introduceTemplateRepository).existsByTitle("신입 개발자 템플릿");
        verify(introduceTemplateRepository).save(any(IntroduceTemplateEntity.class));
        verify(introduceTemplateItemRepository).findById(1);
    }

    @Test
    @DisplayName("템플릿 등록 - 템플릿 항목이 없는 경우에도 성공")
    void testCreateTemplateWithoutItems() {
        // Given
        IntroduceTemplateCommandDTO dtoWithoutItems = IntroduceTemplateCommandDTO.builder()
                .title("빈 템플릿")
                .memberId(1)
                .itemIds(null)
                .build();

        IntroduceTemplateEntity entityWithoutItems = IntroduceTemplateEntity.builder()
                .id(2)
                .title("빈 템플릿")
                .memberId(1)
                .build();

        given(introduceTemplateRepository.existsByTitle("빈 템플릿"))
                .willReturn(false);
        given(introduceTemplateRepository.save(any(IntroduceTemplateEntity.class)))
                .willReturn(entityWithoutItems);

        // When
        IntroduceTemplateCommandDTO result = introduceTemplateCommandService.create(dtoWithoutItems);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals("빈 템플릿", result.getTitle());

        verify(introduceTemplateRepository).existsByTitle("빈 템플릿");
        verify(introduceTemplateRepository).save(any(IntroduceTemplateEntity.class));
        verify(introduceTemplateItemRepository, never()).findById(anyInt());
    }

    @Test
    @DisplayName("템플릿 삭제 - 정상적인 ID로 삭제 시 성공")
    void testDeleteTemplate() {
        // Given
        given(introduceTemplateRepository.findById(1))
                .willReturn(Optional.of(mockEntity));

        // When
        int result = introduceTemplateCommandService.delete(1);

        // Then
        assertEquals(1, result);
        verify(introduceTemplateRepository).findById(1);
        verify(introduceTemplateRepository).delete(mockEntity);
    }

    @Test
    @DisplayName("템플릿 삭제 실패 - 존재하지 않는 ID인 경우 예외 발생")
    void testDeleteTemplateNotFound() {
        // Given
        given(introduceTemplateRepository.findById(999))
                .willReturn(Optional.empty());

        // When & Then
        assertThrows(BusinessException.class, () -> {
            introduceTemplateCommandService.delete(999);
        });

        verify(introduceTemplateRepository).findById(999);
        verify(introduceTemplateRepository, never()).delete(any(IntroduceTemplateEntity.class));
    }
} 