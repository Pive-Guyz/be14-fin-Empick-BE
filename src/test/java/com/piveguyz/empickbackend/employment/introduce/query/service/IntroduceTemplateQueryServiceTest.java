package com.piveguyz.empickbackend.employment.introduce.query.service;

import com.piveguyz.empickbackend.employment.introduce.query.dto.IntroduceTemplateQueryDTO;
import com.piveguyz.empickbackend.employment.introduce.query.mapper.IntroduceTemplateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("자기소개서 템플릿 Query Service 테스트")
class IntroduceTemplateQueryServiceTest {

    @Mock
    private IntroduceTemplateMapper introduceTemplateMapper;

    @InjectMocks
    private IntroduceTemplateQueryServiceImp introduceTemplateQueryService;

    private List<IntroduceTemplateQueryDTO> mockTemplateList;
    private IntroduceTemplateQueryDTO mockTemplate;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        mockTemplate = IntroduceTemplateQueryDTO.builder()
                .id(1)
                .title("기본 자기소개서 템플릿")
                .memberId(100)
                .items(null)
                .build();

        IntroduceTemplateQueryDTO mockTemplate2 = IntroduceTemplateQueryDTO.builder()
                .id(2)
                .title("개발자 자기소개서 템플릿")
                .memberId(101)
                .items(null)
                .build();

        mockTemplateList = Arrays.asList(mockTemplate, mockTemplate2);
    }

    @Test
    @DisplayName("자기소개서 템플릿 전체 조회 - 모든 템플릿 조회 시 성공")
    void testFindAllIntroduceTemplate() {
        // Given
        given(introduceTemplateMapper.findAllIntroduceTemplate())
                .willReturn(mockTemplateList);

        // When
        List<IntroduceTemplateQueryDTO> result = introduceTemplateQueryService.findAllIntroduceTemplate();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("기본 자기소개서 템플릿", result.get(0).getTitle());
        assertEquals("개발자 자기소개서 템플릿", result.get(1).getTitle());
        assertEquals(100, result.get(0).getMemberId());
        assertEquals(101, result.get(1).getMemberId());

        verify(introduceTemplateMapper).findAllIntroduceTemplate();
    }

    @Test
    @DisplayName("자기소개서 템플릿 항목 전체 조회 - 모든 템플릿 항목 조회 시 성공")
    void testFindAllIntroduceTemplateItem() {
        // Given
        given(introduceTemplateMapper.findAllIntroduceTemplateItem())
                .willReturn(mockTemplateList);

        // When
        List<IntroduceTemplateQueryDTO> result = introduceTemplateQueryService.findAllIntroduceTemplateItem();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        verify(introduceTemplateMapper).findAllIntroduceTemplateItem();
    }

    @Test
    @DisplayName("자기소개서 템플릿 ID로 조회 - 특정 ID의 템플릿 조회 시 성공")
    void testGetIntroduceTemplateById() {
        // Given
        given(introduceTemplateMapper.findIntroduceTemplateById(1))
                .willReturn(mockTemplate);

        // When
        IntroduceTemplateQueryDTO result = introduceTemplateQueryService.getIntroduceTemplateById(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("기본 자기소개서 템플릿", result.getTitle());
        assertEquals(100, result.getMemberId());

        verify(introduceTemplateMapper).findIntroduceTemplateById(1);
    }

    @Test
    @DisplayName("자기소개서 템플릿 ID로 조회 - 존재하지 않는 ID 조회 시 null 반환")
    void testGetIntroduceTemplateByIdNotFound() {
        // Given
        given(introduceTemplateMapper.findIntroduceTemplateById(999))
                .willReturn(null);

        // When
        IntroduceTemplateQueryDTO result = introduceTemplateQueryService.getIntroduceTemplateById(999);

        // Then
        assertNull(result);

        verify(introduceTemplateMapper).findIntroduceTemplateById(999);
    }
} 