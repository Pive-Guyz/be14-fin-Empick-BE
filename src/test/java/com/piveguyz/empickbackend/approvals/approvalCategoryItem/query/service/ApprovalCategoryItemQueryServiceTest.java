package com.piveguyz.empickbackend.approvals.approvalCategoryItem.query.service;

import com.piveguyz.empickbackend.approvals.approvalCategoryItem.query.dto.ApprovalCategoryItemQueryDTO;
import com.piveguyz.empickbackend.approvals.approvalCategoryItem.query.mapper.ApprovalCategoryItemQueryMapper;
import com.piveguyz.empickbackend.common.enums.InputType;
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
@DisplayName("결재 카테고리 항목 Query Service 테스트")
class ApprovalCategoryItemQueryServiceTest {

    @Mock
    private ApprovalCategoryItemQueryMapper mapper;

    @InjectMocks
    private ApprovalCategoryItemQueryServiceImpl approvalCategoryItemQueryService;

    private List<ApprovalCategoryItemQueryDTO> mockItemList;
    private ApprovalCategoryItemQueryDTO mockItem;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        mockItem = ApprovalCategoryItemQueryDTO.builder()
                .id(1)
                .categoryId(1)
                .name("휴가 사유")
                .inputType(InputType.TEXT)
                .build();
        
        ApprovalCategoryItemQueryDTO mockItem2 = ApprovalCategoryItemQueryDTO.builder()
                .id(2)
                .categoryId(1)
                .name("휴가 기간")
                .inputType(InputType.DATE)
                .build();

        mockItemList = Arrays.asList(mockItem, mockItem2);
    }

    @Test
    @DisplayName("결재 카테고리 항목 전체 조회 - 모든 항목 조회 시 성공")
    void testFindAll() {
        // Given
        given(mapper.findAll()).willReturn(mockItemList);

        // When
        List<ApprovalCategoryItemQueryDTO> result = approvalCategoryItemQueryService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("휴가 사유", result.get(0).getName());
        assertEquals("휴가 기간", result.get(1).getName());
        assertEquals(InputType.TEXT, result.get(0).getInputType());
        assertEquals(InputType.DATE, result.get(1).getInputType());

        verify(mapper).findAll();
    }

    @Test
    @DisplayName("결재 카테고리 항목 ID로 조회 - 특정 ID의 항목 조회 시 성공")
    void testFindById() {
        // Given
        given(mapper.findById(1)).willReturn(mockItem);

        // When
        ApprovalCategoryItemQueryDTO result = approvalCategoryItemQueryService.findById(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(1, result.getCategoryId());
        assertEquals("휴가 사유", result.getName());
        assertEquals(InputType.TEXT, result.getInputType());

        verify(mapper).findById(1);
    }

    @Test
    @DisplayName("결재 카테고리 항목 ID로 조회 실패 - 존재하지 않는 ID인 경우 예외 발생")
    void testFindByIdNotFound() {
        // Given
        given(mapper.findById(999)).willReturn(null);

        // When & Then
        assertThrows(BusinessException.class, () -> {
            approvalCategoryItemQueryService.findById(999);
        });

        verify(mapper).findById(999);
    }

    @Test
    @DisplayName("카테고리 ID로 항목 조회 - 특정 카테고리의 모든 항목 조회 시 성공")
    void testFindByCategoryId() {
        // Given
        given(mapper.findByCategoryId(1)).willReturn(mockItemList);

        // When
        List<ApprovalCategoryItemQueryDTO> result = approvalCategoryItemQueryService.findByCategoryId(1);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getCategoryId());
        assertEquals(1, result.get(1).getCategoryId());

        verify(mapper).findByCategoryId(1);
    }

    @Test
    @DisplayName("결재 카테고리 항목 전체 조회 - 항목이 없는 경우 빈 리스트 반환")
    void testFindAllEmpty() {
        // Given
        given(mapper.findAll()).willReturn(Collections.emptyList());

        // When
        List<ApprovalCategoryItemQueryDTO> result = approvalCategoryItemQueryService.findAll();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(mapper).findAll();
    }

    @Test
    @DisplayName("카테고리 ID로 항목 조회 - 해당 카테고리에 항목이 없는 경우 빈 리스트 반환")
    void testFindByCategoryIdEmpty() {
        // Given
        given(mapper.findByCategoryId(999)).willReturn(Collections.emptyList());

        // When
        List<ApprovalCategoryItemQueryDTO> result = approvalCategoryItemQueryService.findByCategoryId(999);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(mapper).findByCategoryId(999);
    }
} 