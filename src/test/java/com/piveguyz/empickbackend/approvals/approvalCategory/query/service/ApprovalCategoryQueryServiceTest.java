package com.piveguyz.empickbackend.approvals.approvalCategory.query.service;

import com.piveguyz.empickbackend.approvals.approvalCategory.query.dto.ApprovalCategoryQueryDTO;
import com.piveguyz.empickbackend.approvals.approvalCategory.query.mapper.ApprovalCategoryQueryMapper;
import com.piveguyz.empickbackend.common.exception.BusinessException;
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
@DisplayName("결재 카테고리 Query Service 테스트")
class ApprovalCategoryQueryServiceTest {

    @Mock
    private ApprovalCategoryQueryMapper mapper;

    @InjectMocks
    private ApprovalCategoryQueryServiceImpl approvalCategoryQueryService;

    private List<ApprovalCategoryQueryDTO> mockCategoryList;
    private ApprovalCategoryQueryDTO mockCategory;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        mockCategory = new ApprovalCategoryQueryDTO(1, 1, "휴가 신청서");
        
        ApprovalCategoryQueryDTO mockCategory2 = new ApprovalCategoryQueryDTO(2, 2, "출장 신청서");

        mockCategoryList = Arrays.asList(mockCategory, mockCategory2);
    }

    @Test
    @DisplayName("결재 카테고리 전체 조회 - 모든 카테고리 조회 시 성공")
    void testFindAll() {
        // Given
        given(mapper.findAll()).willReturn(mockCategoryList);

        // When
        List<ApprovalCategoryQueryDTO> result = approvalCategoryQueryService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("휴가 신청서", result.get(0).getName());
        assertEquals("출장 신청서", result.get(1).getName());

        verify(mapper).findAll();
    }

    @Test
    @DisplayName("결재 카테고리 ID로 조회 - 특정 ID의 카테고리 조회 시 성공")
    void testFindById() {
        // Given
        given(mapper.findById(1)).willReturn(mockCategory);

        // When
        ApprovalCategoryQueryDTO result = approvalCategoryQueryService.findById(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(1, result.getApprovalCategoryId());
        assertEquals("휴가 신청서", result.getName());

        verify(mapper).findById(1);
    }

    @Test
    @DisplayName("결재 카테고리 ID로 조회 실패 - 존재하지 않는 ID인 경우 예외 발생")
    void testFindByIdNotFound() {
        // Given
        given(mapper.findById(999)).willReturn(null);

        // When & Then
        assertThrows(BusinessException.class, () -> {
            approvalCategoryQueryService.findById(999);
        });

        verify(mapper).findById(999);
    }

    @Test
    @DisplayName("카테고리 ID로 조회 - 특정 카테고리 ID의 카테고리들 조회 시 성공")
    void testFindByCategoryId() {
        // Given
        List<ApprovalCategoryQueryDTO> categories = Arrays.asList(mockCategory);
        given(mapper.findByCategoryId(1)).willReturn(categories);

        // When
        List<ApprovalCategoryQueryDTO> result = approvalCategoryQueryService.findByCategoryId(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getApprovalCategoryId());

        verify(mapper).findByCategoryId(1);
    }

    @Test
    @DisplayName("이름으로 카테고리 검색 - 특정 이름을 포함한 카테고리 검색 시 성공")
    void testSearchByName() {
        // Given
        List<ApprovalCategoryQueryDTO> searchResults = Arrays.asList(mockCategory);
        given(mapper.searchByName("휴가")).willReturn(searchResults);

        // When
        List<ApprovalCategoryQueryDTO> result = approvalCategoryQueryService.searchByName("휴가");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getName().contains("휴가"));

        verify(mapper).searchByName("휴가");
    }

    @Test
    @DisplayName("결재 카테고리 전체 조회 - 카테고리가 없는 경우 빈 리스트 반환")
    void testFindAllEmpty() {
        // Given
        given(mapper.findAll()).willReturn(Collections.emptyList());

        // When
        List<ApprovalCategoryQueryDTO> result = approvalCategoryQueryService.findAll();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(mapper).findAll();
    }

    @Test
    @DisplayName("이름으로 검색 - 검색 결과가 없는 경우 빈 리스트 반환")
    void testSearchByNameEmpty() {
        // Given
        given(mapper.searchByName("존재하지않음")).willReturn(Collections.emptyList());

        // When
        List<ApprovalCategoryQueryDTO> result = approvalCategoryQueryService.searchByName("존재하지않음");

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(mapper).searchByName("존재하지않음");
    }
} 