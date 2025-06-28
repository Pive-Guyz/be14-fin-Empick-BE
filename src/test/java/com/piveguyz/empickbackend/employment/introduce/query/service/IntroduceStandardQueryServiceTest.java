package com.piveguyz.empickbackend.employment.introduce.query.service;

import com.piveguyz.empickbackend.employment.introduce.query.dto.IntroduceStandardItemQueryDTO;
import com.piveguyz.empickbackend.employment.introduce.query.dto.IntroduceStandardQueryDTO;
import com.piveguyz.empickbackend.employment.introduce.query.mapper.IntroduceStandardMapper;
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
@DisplayName("자기소개서 표준 Query Service 테스트")
class IntroduceStandardQueryServiceTest {

    @Mock
    private IntroduceStandardMapper introduceStandardMapper;

    @InjectMocks
    private IntroduceStandardQueryServiceImp introduceStandardQueryService;

    private List<IntroduceStandardQueryDTO> mockStandardList;
    private IntroduceStandardQueryDTO mockStandard;
    private List<IntroduceStandardItemQueryDTO> mockStandardItemList;
    private IntroduceStandardItemQueryDTO mockStandardItem;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        mockStandard = new IntroduceStandardQueryDTO(
                1, // id
                "개발자를 위한 표준 자기소개서 양식", // content
                100, // memberId
                null // items
        );

        IntroduceStandardQueryDTO mockStandard2 = new IntroduceStandardQueryDTO(
                2, // id
                "디자이너를 위한 표준 자기소개서 양식", // content
                101, // memberId
                null // items
        );

        mockStandardList = Arrays.asList(mockStandard, mockStandard2);

        mockStandardItem = new IntroduceStandardItemQueryDTO(
                1, // id
                "지원 동기를 작성해주세요.", // content
                100, // memberId
                1 // introduceStandardId
        );

        IntroduceStandardItemQueryDTO mockStandardItem2 = new IntroduceStandardItemQueryDTO(
                2, // id
                "성장 과정을 작성해주세요.", // content
                100, // memberId
                1 // introduceStandardId
        );

        mockStandardItemList = Arrays.asList(mockStandardItem, mockStandardItem2);
    }

    @Test
    @DisplayName("자기소개서 표준 전체 조회 - 모든 표준 조회 시 성공")
    void testFindAllIntroduceStandard() {
        // Given
        given(introduceStandardMapper.findAllIntroduceStandard())
                .willReturn(mockStandardList);

        // When
        List<IntroduceStandardQueryDTO> result = introduceStandardQueryService.findAllIntroduceStandard();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("개발자를 위한 표준 자기소개서 양식", result.get(0).getContent());
        assertEquals("디자이너를 위한 표준 자기소개서 양식", result.get(1).getContent());

        verify(introduceStandardMapper).findAllIntroduceStandard();
    }

    @Test
    @DisplayName("자기소개서 표준 항목 전체 조회 - 모든 표준 항목 조회 시 성공")
    void testFindAllIntroduceStandardItem() {
        // Given
        given(introduceStandardMapper.findAllIntroduceStandardItem())
                .willReturn(mockStandardItemList);

        // When
        List<IntroduceStandardItemQueryDTO> result = introduceStandardQueryService.findAllIntroduceStandardItem();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("지원 동기를 작성해주세요.", result.get(0).getContent());
        assertEquals("성장 과정을 작성해주세요.", result.get(1).getContent());

        verify(introduceStandardMapper).findAllIntroduceStandardItem();
    }

    @Test
    @DisplayName("자기소개서 표준 ID로 조회 - 특정 ID의 표준 조회 시 성공")
    void testGetIntroduceStandardById() {
        // Given
        given(introduceStandardMapper.findIntroduceStandardById(1))
                .willReturn(mockStandard);

        // When
        IntroduceStandardQueryDTO result = introduceStandardQueryService.getIntroduceStandardById(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("개발자를 위한 표준 자기소개서 양식", result.getContent());
        assertEquals(100, result.getMemberId());

        verify(introduceStandardMapper).findIntroduceStandardById(1);
    }

    @Test
    @DisplayName("자기소개서 표준 항목 조회 - 표준 ID로 항목 조회 시 성공")
    void testGetIntroduceStandardItemByStandardId() {
        // Given
        given(introduceStandardMapper.findIntroduceStandardItemByStandardId(1))
                .willReturn(mockStandardItemList);

        // When
        List<IntroduceStandardItemQueryDTO> result = 
                introduceStandardQueryService.getIntroduceStandardItemByStandardId(1);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getIntroduceStandardId());
        assertEquals(1, result.get(1).getIntroduceStandardId());
        assertEquals(100, result.get(0).getMemberId());
        assertEquals(100, result.get(1).getMemberId());

        verify(introduceStandardMapper).findIntroduceStandardItemByStandardId(1);
    }

    @Test
    @DisplayName("자기소개서 표준 항목 조회 - 존재하지 않는 표준 ID로 조회 시 빈 리스트 반환")
    void testGetIntroduceStandardItemByStandardIdEmpty() {
        // Given
        given(introduceStandardMapper.findIntroduceStandardItemByStandardId(999))
                .willReturn(Collections.emptyList());

        // When
        List<IntroduceStandardItemQueryDTO> result = 
                introduceStandardQueryService.getIntroduceStandardItemByStandardId(999);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(introduceStandardMapper).findIntroduceStandardItemByStandardId(999);
    }

    @Test
    @DisplayName("자기소개서 표준 ID로 조회 - 존재하지 않는 ID 조회 시 null 반환")
    void testGetIntroduceStandardByIdNotFound() {
        // Given
        given(introduceStandardMapper.findIntroduceStandardById(999))
                .willReturn(null);

        // When
        IntroduceStandardQueryDTO result = introduceStandardQueryService.getIntroduceStandardById(999);

        // Then
        assertNull(result);

        verify(introduceStandardMapper).findIntroduceStandardById(999);
    }
} 