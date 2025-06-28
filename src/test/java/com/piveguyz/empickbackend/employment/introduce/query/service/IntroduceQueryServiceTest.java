package com.piveguyz.empickbackend.employment.introduce.query.service;

import com.piveguyz.empickbackend.employment.introduce.query.dto.IntroduceQueryDTO;
import com.piveguyz.empickbackend.employment.introduce.query.mapper.IntroduceMapper;
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
@DisplayName("자기소개서 Query Service 테스트")
class IntroduceQueryServiceTest {

    @Mock
    private IntroduceMapper introduceMapper;

    @InjectMocks
    private IntroduceQueryServiceImp introduceQueryService;

    private List<IntroduceQueryDTO> mockIntroduceList;
    private IntroduceQueryDTO mockIntroduce;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        mockIntroduce = new IntroduceQueryDTO(
                1, 
                "저는 열정적인 개발자입니다.", 
                10, 
                1, 
                100
        );

        IntroduceQueryDTO mockIntroduce2 = new IntroduceQueryDTO(
                2, 
                "창의적인 디자이너로 성장하고 싶습니다.", 
                20, 
                2, 
                101
        );

        mockIntroduceList = Arrays.asList(mockIntroduce, mockIntroduce2);
    }

    @Test
    @DisplayName("자기소개서 전체 조회 - 모든 자기소개서 조회 시 성공")
    void testFindAllIntroduce() {
        // Given
        given(introduceMapper.findAllIntroduce())
                .willReturn(mockIntroduceList);

        // When
        List<IntroduceQueryDTO> result = introduceQueryService.findAllIntroduce();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("저는 열정적인 개발자입니다.", result.get(0).getContent());
        assertEquals("창의적인 디자이너로 성장하고 싶습니다.", result.get(1).getContent());
        assertEquals(10, result.get(0).getApplicantId());
        assertEquals(20, result.get(1).getApplicantId());

        verify(introduceMapper).findAllIntroduce();
    }

    @Test
    @DisplayName("자기소개서 Application ID로 조회 - 특정 지원서의 자기소개서 조회 시 성공")
    void testFindIntroduceByApplicationId() {
        // Given
        given(introduceMapper.findIntroduceByApplicationId(100))
                .willReturn(mockIntroduce);

        // When
        IntroduceQueryDTO result = introduceQueryService.findIntroduceByApplicationId(100);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(100, result.getApplicationId());
        assertEquals("저는 열정적인 개발자입니다.", result.getContent());
        assertEquals(10, result.getApplicantId());
        assertEquals(1, result.getIntroduceTemplateId());

        verify(introduceMapper).findIntroduceByApplicationId(100);
    }

    @Test
    @DisplayName("자기소개서 ID로 조회 - 특정 ID의 자기소개서 조회 시 성공")
    void testFindIntroduceById() {
        // Given
        given(introduceMapper.findIntroduceById(1))
                .willReturn(mockIntroduce);

        // When
        IntroduceQueryDTO result = introduceQueryService.findIntroduceById(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(100, result.getApplicationId());
        assertEquals("저는 열정적인 개발자입니다.", result.getContent());
        assertEquals(10, result.getApplicantId());

        verify(introduceMapper).findIntroduceById(1);
    }

    @Test
    @DisplayName("자기소개서 Application ID로 조회 - 존재하지 않는 Application ID 조회 시 null 반환")
    void testFindIntroduceByApplicationIdNotFound() {
        // Given
        given(introduceMapper.findIntroduceByApplicationId(999))
                .willReturn(null);

        // When
        IntroduceQueryDTO result = introduceQueryService.findIntroduceByApplicationId(999);

        // Then
        assertNull(result);

        verify(introduceMapper).findIntroduceByApplicationId(999);
    }

    @Test
    @DisplayName("자기소개서 ID로 조회 - 존재하지 않는 ID 조회 시 null 반환")
    void testFindIntroduceByIdNotFound() {
        // Given
        given(introduceMapper.findIntroduceById(999))
                .willReturn(null);

        // When
        IntroduceQueryDTO result = introduceQueryService.findIntroduceById(999);

        // Then
        assertNull(result);

        verify(introduceMapper).findIntroduceById(999);
    }

    @Test
    @DisplayName("자기소개서 전체 조회 - 자기소개서가 없는 경우 빈 리스트 반환")
    void testFindAllIntroduceEmpty() {
        // Given
        given(introduceMapper.findAllIntroduce())
                .willReturn(Collections.emptyList());

        // When
        List<IntroduceQueryDTO> result = introduceQueryService.findAllIntroduce();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(introduceMapper).findAllIntroduce();
    }
} 