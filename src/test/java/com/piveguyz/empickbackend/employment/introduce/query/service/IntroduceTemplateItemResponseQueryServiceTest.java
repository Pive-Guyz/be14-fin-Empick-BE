package com.piveguyz.empickbackend.employment.introduce.query.service;

import com.piveguyz.empickbackend.employment.introduce.query.dto.IntroduceTemplateItemResponseQueryDTO;
import com.piveguyz.empickbackend.employment.introduce.query.mapper.IntroduceTemplateItemResponseQueryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("자기소개서 템플릿 항목 응답 Query Service 테스트")
class IntroduceTemplateItemResponseQueryServiceTest {

    @Mock
    private IntroduceTemplateItemResponseQueryMapper introduceTemplateItemResponseQueryMapper;

    @InjectMocks
    private IntroduceTemplateItemResponseQueryServiceImp introduceTemplateItemResponseQueryService;

    private List<IntroduceTemplateItemResponseQueryDTO> mockResponseList;
    private IntroduceTemplateItemResponseQueryDTO mockResponse;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        mockResponse = IntroduceTemplateItemResponseQueryDTO.builder()
                .id(1)
                .introduceId(1)
                .introduceTemplateItemId(1)
                .content("저는 개발에 대한 열정이 있습니다.")
                .build();

        IntroduceTemplateItemResponseQueryDTO mockResponse2 = IntroduceTemplateItemResponseQueryDTO.builder()
                .id(2)
                .introduceId(1)
                .introduceTemplateItemId(2)
                .content("팀워크를 중시하며 소통을 잘합니다.")
                .build();

        mockResponseList = Arrays.asList(mockResponse, mockResponse2);
    }

    @Test
    @DisplayName("자기소개서 템플릿 항목 응답 전체 조회 - 모든 응답 조회 시 성공")
    void testFindAllIntroduceTemplateItemResponse() {
        // Given
        given(introduceTemplateItemResponseQueryMapper.findAllIntroduceTemplateItemResponse())
                .willReturn(mockResponseList);

        // When
        List<IntroduceTemplateItemResponseQueryDTO> result = 
                introduceTemplateItemResponseQueryService.findAllIntroduceTemplateItemResponse();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("저는 개발에 대한 열정이 있습니다.", result.get(0).getContent());
        assertEquals("팀워크를 중시하며 소통을 잘합니다.", result.get(1).getContent());

        verify(introduceTemplateItemResponseQueryMapper).findAllIntroduceTemplateItemResponse();
    }

    @Test
    @DisplayName("자기소개서 템플릿 항목 응답 전체 조회 - 응답이 없는 경우 빈 리스트 반환")
    void testFindAllIntroduceTemplateItemResponseEmpty() {
        // Given
        given(introduceTemplateItemResponseQueryMapper.findAllIntroduceTemplateItemResponse())
                .willReturn(Collections.emptyList());

        // When
        List<IntroduceTemplateItemResponseQueryDTO> result = 
                introduceTemplateItemResponseQueryService.findAllIntroduceTemplateItemResponse();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(introduceTemplateItemResponseQueryMapper).findAllIntroduceTemplateItemResponse();
    }

    @Test
    @DisplayName("자기소개서 템플릿 항목 응답 전체 조회 - 단일 응답 조회 시 성공")
    void testFindAllIntroduceTemplateItemResponseSingle() {
        // Given
        List<IntroduceTemplateItemResponseQueryDTO> singleResponse = Arrays.asList(mockResponse);
        given(introduceTemplateItemResponseQueryMapper.findAllIntroduceTemplateItemResponse())
                .willReturn(singleResponse);

        // When
        List<IntroduceTemplateItemResponseQueryDTO> result = 
                introduceTemplateItemResponseQueryService.findAllIntroduceTemplateItemResponse();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(1, result.get(0).getIntroduceId());
        assertEquals(1, result.get(0).getIntroduceTemplateItemId());
        assertEquals("저는 개발에 대한 열정이 있습니다.", result.get(0).getContent());

        verify(introduceTemplateItemResponseQueryMapper).findAllIntroduceTemplateItemResponse();
    }
} 