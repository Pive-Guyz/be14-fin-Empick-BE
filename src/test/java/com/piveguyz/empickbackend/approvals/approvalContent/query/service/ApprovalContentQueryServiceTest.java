package com.piveguyz.empickbackend.approvals.approvalContent.query.service;

import com.piveguyz.empickbackend.approvals.approvalContent.query.dto.ApprovalContentQueryDTO;
import com.piveguyz.empickbackend.approvals.approvalContent.query.mapper.ApprovalContentQueryMapper;
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
@DisplayName("결재 내용 Query Service 테스트")
class ApprovalContentQueryServiceTest {

    @Mock
    private ApprovalContentQueryMapper mapper;

    @InjectMocks
    private ApprovalContentQueryServiceImpl approvalContentQueryService;

    private List<ApprovalContentQueryDTO> mockContentList;
    private ApprovalContentQueryDTO mockContent;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        mockContent = new ApprovalContentQueryDTO(1, 10, 1, "휴가 신청 내용입니다.");
        
        ApprovalContentQueryDTO mockContent2 = new ApprovalContentQueryDTO(2, 10, 2, "2024-01-01 ~ 2024-01-03");

        mockContentList = Arrays.asList(mockContent, mockContent2);
    }

    @Test
    @DisplayName("결재 내용 전체 조회 - 모든 결재 내용 조회 시 성공")
    void testFindAll() {
        // Given
        given(mapper.findAll()).willReturn(mockContentList);

        // When
        List<ApprovalContentQueryDTO> result = approvalContentQueryService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("휴가 신청 내용입니다.", result.get(0).getContent());
        assertEquals("2024-01-01 ~ 2024-01-03", result.get(1).getContent());

        verify(mapper).findAll();
    }

    @Test
    @DisplayName("결재 내용 ID로 조회 - 특정 ID의 결재 내용 조회 시 성공")
    void testFindById() {
        // Given
        given(mapper.findById(1)).willReturn(mockContent);

        // When
        ApprovalContentQueryDTO result = approvalContentQueryService.findById(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(10, result.getApprovalId());
        assertEquals("휴가 신청 내용입니다.", result.getContent());

        verify(mapper).findById(1);
    }

    @Test
    @DisplayName("결재 내용 ID로 조회 실패 - 존재하지 않는 ID인 경우 예외 발생")
    void testFindByIdNotFound() {
        // Given
        given(mapper.findById(999)).willReturn(null);

        // When & Then
        assertThrows(BusinessException.class, () -> {
            approvalContentQueryService.findById(999);
        });

        verify(mapper).findById(999);
    }

    @Test
    @DisplayName("결재 ID로 내용 조회 - 특정 결재의 모든 내용 조회 시 성공")
    void testFindByApprovalId() {
        // Given
        given(mapper.findByApprovalId(10)).willReturn(mockContentList);

        // When
        List<ApprovalContentQueryDTO> result = approvalContentQueryService.findByApprovalId(10);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(10, result.get(0).getApprovalId());
        assertEquals(10, result.get(1).getApprovalId());

        verify(mapper).findByApprovalId(10);
    }

    @Test
    @DisplayName("결재 내용 전체 조회 - 내용이 없는 경우 빈 리스트 반환")
    void testFindAllEmpty() {
        // Given
        given(mapper.findAll()).willReturn(Collections.emptyList());

        // When
        List<ApprovalContentQueryDTO> result = approvalContentQueryService.findAll();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(mapper).findAll();
    }

    @Test
    @DisplayName("결재 ID로 내용 조회 - 해당 결재에 내용이 없는 경우 빈 리스트 반환")
    void testFindByApprovalIdEmpty() {
        // Given
        given(mapper.findByApprovalId(999)).willReturn(Collections.emptyList());

        // When
        List<ApprovalContentQueryDTO> result = approvalContentQueryService.findByApprovalId(999);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(mapper).findByApprovalId(999);
    }
} 