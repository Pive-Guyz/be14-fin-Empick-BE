package com.piveguyz.empickbackend.employment.introduce.command.application.service;

import com.piveguyz.empickbackend.employment.introduce.command.application.dto.IntroduceTemplateItemResponseCommandDTO;
import com.piveguyz.empickbackend.employment.introduce.command.application.mapper.IntroduceTemplateItemResponseCommandMapper;
import com.piveguyz.empickbackend.employment.introduce.command.domain.aggregate.IntroduceTemplateItemResponseEntity;
import com.piveguyz.empickbackend.employment.introduce.command.domain.repository.IntroduceTemplateItemResponseCommandRepository;
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
@DisplayName("자기소개 템플릿 항목 응답 Command Service 테스트")
class IntroduceTemplateItemResponseCommandServiceTest {

    @Mock
    private IntroduceTemplateItemResponseCommandRepository introduceTemplateItemResponseCommandRepository;

    @Mock
    private IntroduceTemplateItemResponseCommandMapper introduceTemplateItemResponseCommandMapper;

    @InjectMocks
    private IntroduceTemplateItemResponseCommandServiceImp introduceTemplateItemResponseCommandService;

    private IntroduceTemplateItemResponseCommandDTO mockDto;
    private IntroduceTemplateItemResponseEntity mockEntity;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        mockDto = IntroduceTemplateItemResponseCommandDTO.builder()
                .introduceId(1)
                .introduceTemplateItemId(1)
                .content("저는 이 회사에 지원한 이유는...")
                .build();

        mockEntity = IntroduceTemplateItemResponseEntity.builder()
                .id(1)
                .introduceId(1)
                .introduceTemplateItemId(1)
                .content("저는 이 회사에 지원한 이유는...")
                .build();
    }

    @Test
    @DisplayName("템플릿 항목 응답 등록 - 정상적인 데이터로 등록 시 성공")
    void testCreateTemplateItemResponse() {
        // Given
        given(introduceTemplateItemResponseCommandMapper.toEntity(mockDto))
                .willReturn(mockEntity);
        given(introduceTemplateItemResponseCommandRepository.save(mockEntity))
                .willReturn(mockEntity);
        given(introduceTemplateItemResponseCommandMapper.toDTO(mockEntity))
                .willReturn(mockDto);

        // When
        IntroduceTemplateItemResponseCommandDTO result = introduceTemplateItemResponseCommandService.create(mockDto);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getIntroduceId());
        assertEquals(1, result.getIntroduceTemplateItemId());
        assertEquals("저는 이 회사에 지원한 이유는...", result.getContent());

        verify(introduceTemplateItemResponseCommandMapper).toEntity(mockDto);
        verify(introduceTemplateItemResponseCommandRepository).save(mockEntity);
        verify(introduceTemplateItemResponseCommandMapper).toDTO(mockEntity);
    }

    @Test
    @DisplayName("템플릿 항목 응답 등록 - 빈 내용으로도 등록 가능")
    void testCreateTemplateItemResponseWithEmptyContent() {
        // Given
        IntroduceTemplateItemResponseCommandDTO emptyDto = IntroduceTemplateItemResponseCommandDTO.builder()
                .introduceId(2)
                .introduceTemplateItemId(2)
                .content("")
                .build();

        IntroduceTemplateItemResponseEntity emptyEntity = IntroduceTemplateItemResponseEntity.builder()
                .id(2)
                .introduceId(2)
                .introduceTemplateItemId(2)
                .content("")
                .build();

        given(introduceTemplateItemResponseCommandMapper.toEntity(emptyDto))
                .willReturn(emptyEntity);
        given(introduceTemplateItemResponseCommandRepository.save(emptyEntity))
                .willReturn(emptyEntity);
        given(introduceTemplateItemResponseCommandMapper.toDTO(emptyEntity))
                .willReturn(emptyDto);

        // When
        IntroduceTemplateItemResponseCommandDTO result = introduceTemplateItemResponseCommandService.create(emptyDto);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getIntroduceId());
        assertEquals(2, result.getIntroduceTemplateItemId());
        assertEquals("", result.getContent());

        verify(introduceTemplateItemResponseCommandMapper).toEntity(emptyDto);
        verify(introduceTemplateItemResponseCommandRepository).save(emptyEntity);
        verify(introduceTemplateItemResponseCommandMapper).toDTO(emptyEntity);
    }

    @Test
    @DisplayName("템플릿 항목 응답 등록 - null 내용으로도 등록 가능")
    void testCreateTemplateItemResponseWithNullContent() {
        // Given
        IntroduceTemplateItemResponseCommandDTO nullDto = IntroduceTemplateItemResponseCommandDTO.builder()
                .introduceId(3)
                .introduceTemplateItemId(3)
                .content(null)
                .build();

        IntroduceTemplateItemResponseEntity nullEntity = IntroduceTemplateItemResponseEntity.builder()
                .id(3)
                .introduceId(3)
                .introduceTemplateItemId(3)
                .content(null)
                .build();

        given(introduceTemplateItemResponseCommandMapper.toEntity(nullDto))
                .willReturn(nullEntity);
        given(introduceTemplateItemResponseCommandRepository.save(nullEntity))
                .willReturn(nullEntity);
        given(introduceTemplateItemResponseCommandMapper.toDTO(nullEntity))
                .willReturn(nullDto);

        // When
        IntroduceTemplateItemResponseCommandDTO result = introduceTemplateItemResponseCommandService.create(nullDto);

        // Then
        assertNotNull(result);
        assertEquals(3, result.getIntroduceId());
        assertEquals(3, result.getIntroduceTemplateItemId());
        assertNull(result.getContent());

        verify(introduceTemplateItemResponseCommandMapper).toEntity(nullDto);
        verify(introduceTemplateItemResponseCommandRepository).save(nullEntity);
        verify(introduceTemplateItemResponseCommandMapper).toDTO(nullEntity);
    }
} 