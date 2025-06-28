package com.piveguyz.empickbackend.employment.introduce.query.service;

import com.piveguyz.empickbackend.employment.introduce.query.dto.IntroduceResultQueryDTO;
import com.piveguyz.empickbackend.employment.introduce.query.mapper.IntroduceResultMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("자기소개서 결과 Query Service 테스트")
class IntroduceResultQueryServiceTest {

    @Mock
    private IntroduceResultMapper introduceResultMapper;

    @InjectMocks
    private IntroduceResultQueryServiceImp introduceResultQueryService;

    private List<IntroduceResultQueryDTO> mockResultList;
    private IntroduceResultQueryDTO mockResult;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        mockResult = new IntroduceResultQueryDTO(
                1, // id
                100, // memberId
                "전반적으로 우수한 자기소개서입니다.", // content
                85, // ratingScore
                1, // introduceStandardId
                1, // introduceId
                100, // updatedBy
                LocalDateTime.now() // updatedAt
        );

        IntroduceResultQueryDTO mockResult2 = new IntroduceResultQueryDTO(
                2, // id
                101, // memberId
                "매우 인상적인 내용입니다.", // content
                92, // ratingScore
                1, // introduceStandardId
                2, // introduceId
                101, // updatedBy
                LocalDateTime.now() // updatedAt
        );

        mockResultList = Arrays.asList(mockResult, mockResult2);
    }

    @Test
    @DisplayName("자기소개서 결과 전체 조회 - 모든 결과 조회 시 성공")
    void testFindAllIntroduceResult() {
        // Given
        given(introduceResultMapper.findAllIntroduceResult())
                .willReturn(mockResultList);

        // When
        List<IntroduceResultQueryDTO> result = introduceResultQueryService.findAllIntroduceResult();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(85, result.get(0).getRatingScore());
        assertEquals(92, result.get(1).getRatingScore());
        assertEquals("전반적으로 우수한 자기소개서입니다.", result.get(0).getContent());
        assertEquals("매우 인상적인 내용입니다.", result.get(1).getContent());

        verify(introduceResultMapper).findAllIntroduceResult();
    }

    @Test
    @DisplayName("자기소개서 결과 ID로 조회 - 특정 ID의 결과 조회 시 성공")
    void testFindIntroduceResultById() {
        // Given
        given(introduceResultMapper.findById(1))
                .willReturn(mockResult);

        // When
        IntroduceResultQueryDTO result = introduceResultQueryService.findIntroduceResultById(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(1, result.getIntroduceId());
        assertEquals(85, result.getRatingScore());
        assertEquals("전반적으로 우수한 자기소개서입니다.", result.getContent());
        assertEquals(100, result.getMemberId());

        verify(introduceResultMapper).findById(1);
    }

    @Test
    @DisplayName("자기소개서 결과 ID로 조회 - 존재하지 않는 ID 조회 시 null 반환")
    void testFindIntroduceResultByIdNotFound() {
        // Given
        given(introduceResultMapper.findById(999))
                .willReturn(null);

        // When
        IntroduceResultQueryDTO result = introduceResultQueryService.findIntroduceResultById(999);

        // Then
        assertNull(result);

        verify(introduceResultMapper).findById(999);
    }

    @Test
    @DisplayName("자기소개서 결과 전체 조회 - 결과가 없는 경우 빈 리스트 반환")
    void testFindAllIntroduceResultEmpty() {
        // Given
        given(introduceResultMapper.findAllIntroduceResult())
                .willReturn(Collections.emptyList());

        // When
        List<IntroduceResultQueryDTO> result = introduceResultQueryService.findAllIntroduceResult();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(introduceResultMapper).findAllIntroduceResult();
    }

    @Test
    @DisplayName("자기소개서 결과 전체 조회 - 단일 결과 조회 시 성공")
    void testFindAllIntroduceResultSingle() {
        // Given
        List<IntroduceResultQueryDTO> singleResult = Arrays.asList(mockResult);
        given(introduceResultMapper.findAllIntroduceResult())
                .willReturn(singleResult);

        // When
        List<IntroduceResultQueryDTO> result = introduceResultQueryService.findAllIntroduceResult();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(85, result.get(0).getRatingScore());
        assertEquals(100, result.get(0).getMemberId());

        verify(introduceResultMapper).findAllIntroduceResult();
    }

    @Test
    @DisplayName("자기소개서 결과 ID로 조회 - 높은 점수 결과 조회 시 성공")
    void testFindIntroduceResultByIdHighScore() {
        // Given
        IntroduceResultQueryDTO highScoreResult = new IntroduceResultQueryDTO(
                3, // id
                102, // memberId
                "완벽한 자기소개서입니다.", // content
                98, // ratingScore
                1, // introduceStandardId
                3, // introduceId
                102, // updatedBy
                LocalDateTime.now() // updatedAt
        );

        given(introduceResultMapper.findById(3))
                .willReturn(highScoreResult);

        // When
        IntroduceResultQueryDTO result = introduceResultQueryService.findIntroduceResultById(3);

        // Then
        assertNotNull(result);
        assertEquals(98, result.getRatingScore());
        assertEquals("완벽한 자기소개서입니다.", result.getContent());
        assertTrue(result.getRatingScore() > 95); // 높은 점수 검증

        verify(introduceResultMapper).findById(3);
    }
} 