package com.piveguyz.empickbackend.employment.introduce.command.application.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.employment.introduce.command.application.dto.IntroduceTemplateItemCommandDTO;
import com.piveguyz.empickbackend.employment.introduce.command.domain.aggregate.IntroduceTemplateItemEntity;
import com.piveguyz.empickbackend.employment.introduce.command.domain.repository.IntroduceTemplateItemRepository;
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
@DisplayName("자기소개 템플릿 항목 Command Service 테스트")
class IntroduceTemplateItemCommandServiceTest {

    @Mock
    private IntroduceTemplateItemRepository introduceTemplateItemRepository;

    @InjectMocks
    private IntroduceTemplateItemCommandServiceImp introduceTemplateItemCommandService;

    private IntroduceTemplateItemCommandDTO mockDto;
    private IntroduceTemplateItemEntity mockEntity;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        // IntroduceTemplateItemCommandDTO는 @Builder가 없으므로 생성자 사용
        mockDto = new IntroduceTemplateItemCommandDTO();
        mockDto.setTitle("지원동기");
        mockDto.setMemberId(1);
        mockDto.setIntroduceTemplateId(null);

        mockEntity = IntroduceTemplateItemEntity.builder()
                .id(1)
                .title("지원동기")
                .memberId(1)
                .introduceTemplateId(null)
                .build();
    }

    @Test
    @DisplayName("템플릿 항목 등록 - 정상적인 데이터로 등록 시 성공")
    void testCreateTemplateItem() {
        // Given
        given(introduceTemplateItemRepository.existsByTitle("지원동기"))
                .willReturn(false);
        given(introduceTemplateItemRepository.save(any(IntroduceTemplateItemEntity.class)))
                .willReturn(mockEntity);

        // When
        IntroduceTemplateItemCommandDTO result = introduceTemplateItemCommandService.create(mockDto);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("지원동기", result.getTitle());
        assertEquals(1, result.getMemberId());

        verify(introduceTemplateItemRepository).existsByTitle("지원동기");
        verify(introduceTemplateItemRepository).save(any(IntroduceTemplateItemEntity.class));
    }

    @Test
    @DisplayName("템플릿 항목 등록 실패 - ID가 포함된 경우 예외 발생")
    void testCreateTemplateItemWithId() {
        // Given
        mockDto.setId(1); // ID가 포함된 DTO

        // When & Then
        assertThrows(BusinessException.class, () -> {
            introduceTemplateItemCommandService.create(mockDto);
        });

        verify(introduceTemplateItemRepository, never()).existsByTitle(anyString());
        verify(introduceTemplateItemRepository, never()).save(any(IntroduceTemplateItemEntity.class));
    }

    @Test
    @DisplayName("템플릿 항목 등록 실패 - 중복된 제목인 경우 예외 발생")
    void testCreateTemplateItemDuplicate() {
        // Given
        given(introduceTemplateItemRepository.existsByTitle("지원동기"))
                .willReturn(true);

        // When & Then
        assertThrows(BusinessException.class, () -> {
            introduceTemplateItemCommandService.create(mockDto);
        });

        verify(introduceTemplateItemRepository).existsByTitle("지원동기");
        verify(introduceTemplateItemRepository, never()).save(any(IntroduceTemplateItemEntity.class));
    }

    @Test
    @DisplayName("템플릿 항목 등록 - 다른 제목으로 등록 시 성공")
    void testCreateTemplateItemWithDifferentTitle() {
        // Given
        IntroduceTemplateItemCommandDTO anotherDto = new IntroduceTemplateItemCommandDTO();
        anotherDto.setTitle("자기소개");
        anotherDto.setMemberId(2);

        IntroduceTemplateItemEntity anotherEntity = IntroduceTemplateItemEntity.builder()
                .id(2)
                .title("자기소개")
                .memberId(2)
                .introduceTemplateId(null)
                .build();

        given(introduceTemplateItemRepository.existsByTitle("자기소개"))
                .willReturn(false);
        given(introduceTemplateItemRepository.save(any(IntroduceTemplateItemEntity.class)))
                .willReturn(anotherEntity);

        // When
        IntroduceTemplateItemCommandDTO result = introduceTemplateItemCommandService.create(anotherDto);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals("자기소개", result.getTitle());
        assertEquals(2, result.getMemberId());

        verify(introduceTemplateItemRepository).existsByTitle("자기소개");
        verify(introduceTemplateItemRepository).save(any(IntroduceTemplateItemEntity.class));
    }

    @Test
    @DisplayName("템플릿 항목 삭제 - 정상적인 ID로 삭제 시 성공")
    void testDeleteTemplateItem() {
        // Given
        given(introduceTemplateItemRepository.findById(1))
                .willReturn(Optional.of(mockEntity));

        // When
        int result = introduceTemplateItemCommandService.delete(1);

        // Then
        assertEquals(1, result);
        verify(introduceTemplateItemRepository).findById(1);
        verify(introduceTemplateItemRepository).delete(mockEntity);
    }

    @Test
    @DisplayName("템플릿 항목 삭제 실패 - 존재하지 않는 ID인 경우 예외 발생")
    void testDeleteTemplateItemNotFound() {
        // Given
        given(introduceTemplateItemRepository.findById(999))
                .willReturn(Optional.empty());

        // When & Then
        assertThrows(BusinessException.class, () -> {
            introduceTemplateItemCommandService.delete(999);
        });

        verify(introduceTemplateItemRepository).findById(999);
        verify(introduceTemplateItemRepository, never()).delete(any(IntroduceTemplateItemEntity.class));
    }

    @Test
    @DisplayName("템플릿 항목 등록 - 템플릿 ID가 할당된 상태로 등록")
    void testCreateTemplateItemWithTemplateId() {
        // Given
        IntroduceTemplateItemCommandDTO dtoWithTemplateId = new IntroduceTemplateItemCommandDTO();
        dtoWithTemplateId.setTitle("경력사항");
        dtoWithTemplateId.setMemberId(3);
        dtoWithTemplateId.setIntroduceTemplateId(5);

        IntroduceTemplateItemEntity entityWithTemplateId = IntroduceTemplateItemEntity.builder()
                .id(3)
                .title("경력사항")
                .memberId(3)
                .introduceTemplateId(5)
                .build();

        given(introduceTemplateItemRepository.existsByTitle("경력사항"))
                .willReturn(false);
        given(introduceTemplateItemRepository.save(any(IntroduceTemplateItemEntity.class)))
                .willReturn(entityWithTemplateId);

        // When
        IntroduceTemplateItemCommandDTO result = introduceTemplateItemCommandService.create(dtoWithTemplateId);

        // Then
        assertNotNull(result);
        assertEquals(3, result.getId());
        assertEquals("경력사항", result.getTitle());
        assertEquals(3, result.getMemberId());
        assertEquals(5, result.getIntroduceTemplateId());

        verify(introduceTemplateItemRepository).existsByTitle("경력사항");
        verify(introduceTemplateItemRepository).save(any(IntroduceTemplateItemEntity.class));
    }
} 