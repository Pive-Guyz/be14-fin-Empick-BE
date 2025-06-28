package com.piveguyz.empickbackend.approvals.approval.query.service;

import com.piveguyz.empickbackend.approvals.approval.command.domain.enums.ApprovalStatus;
import com.piveguyz.empickbackend.approvals.approval.query.dto.ApprovalQueryDTO;
import com.piveguyz.empickbackend.approvals.approval.query.dto.ApprovalReceivedQueryDTO;
import com.piveguyz.empickbackend.approvals.approval.query.dto.ApprovalRequestedListQueryDTO;
import com.piveguyz.empickbackend.approvals.approval.query.mapper.ApprovalQueryMapper;
import com.piveguyz.empickbackend.common.exception.BusinessException;
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
@DisplayName("결재 Query Service 테스트")
class ApprovalQueryServiceTest {

    @Mock
    private ApprovalQueryMapper mapper;

    @InjectMocks
    private ApprovalQueryServiceImpl approvalQueryService;

    private List<ApprovalQueryDTO> mockApprovalList;
    private ApprovalQueryDTO mockApproval;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        
        mockApproval = new ApprovalQueryDTO(
                1, 1, 100, now, ApprovalStatus.IN_PROGRESS,
                101, 102, null, null,
                null, null, null, null
        );
        
        ApprovalQueryDTO mockApproval2 = new ApprovalQueryDTO(
                2, 2, 200, now, ApprovalStatus.APPROVED,
                201, 202, null, null,
                now.minusHours(1), now, null, null
        );

        mockApprovalList = Arrays.asList(mockApproval, mockApproval2);
    }

    @Test
    @DisplayName("결재 전체 조회 - 모든 결재 조회 시 성공")
    void testFindAll() {
        // Given
        given(mapper.findAll()).willReturn(mockApprovalList);

        // When
        List<ApprovalQueryDTO> result = approvalQueryService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals(ApprovalStatus.IN_PROGRESS, result.get(0).getStatus());
        assertEquals(ApprovalStatus.APPROVED, result.get(1).getStatus());

        verify(mapper).findAll();
    }

    @Test
    @DisplayName("결재 ID로 조회 - 특정 ID의 결재 조회 시 성공")
    void testFindById() {
        // Given
        given(mapper.findById(1)).willReturn(mockApproval);

        // When
        ApprovalQueryDTO result = approvalQueryService.findById(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(1, result.getCategoryId());
        assertEquals(100, result.getWriterId());
        assertEquals(ApprovalStatus.IN_PROGRESS, result.getStatus());

        verify(mapper).findById(1);
    }

    @Test
    @DisplayName("결재 ID로 조회 실패 - 존재하지 않는 ID인 경우 예외 발생")
    void testFindByIdNotFound() {
        // Given
        given(mapper.findById(999)).willReturn(null);

        // When & Then
        assertThrows(BusinessException.class, () -> {
            approvalQueryService.findById(999);
        });

        verify(mapper).findById(999);
    }

    @Test
    @DisplayName("카테고리 ID로 결재 조회 - 특정 카테고리의 모든 결재 조회 시 성공")
    void testFindByCategoryId() {
        // Given
        List<ApprovalQueryDTO> categoryApprovals = Arrays.asList(mockApproval);
        given(mapper.findByCategoryId(1)).willReturn(categoryApprovals);

        // When
        List<ApprovalQueryDTO> result = approvalQueryService.findByCategoryId(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getCategoryId());

        verify(mapper).findByCategoryId(1);
    }

    @Test
    @DisplayName("작성자 ID로 결재 조회 - 특정 작성자의 모든 결재 조회 시 성공")
    void testFindByWriterId() {
        // Given
        List<ApprovalQueryDTO> writerApprovals = Arrays.asList(mockApproval);
        given(mapper.findByWriterId(100)).willReturn(writerApprovals);

        // When
        List<ApprovalQueryDTO> result = approvalQueryService.findByWriterId(100);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(100, result.get(0).getWriterId());

        verify(mapper).findByWriterId(100);
    }

    @Test
    @DisplayName("받은 결재 조회 - 특정 회원이 받은 결재 조회 시 성공")
    void testFindReceivedApprovals() {
        // Given
        List<ApprovalReceivedQueryDTO> receivedApprovals = Arrays.asList();
        given(mapper.findReceivedApprovals(101)).willReturn(receivedApprovals);

        // When
        List<ApprovalReceivedQueryDTO> result = approvalQueryService.findReceivedApprovals(101);

        // Then
        assertNotNull(result);

        verify(mapper).findReceivedApprovals(101);
    }

    @Test
    @DisplayName("요청한 결재 조회 - 특정 회원이 요청한 결재 조회 시 성공")
    void testFindRequestedApprovals() {
        // Given
        List<ApprovalRequestedListQueryDTO> requestedApprovals = Arrays.asList();
        given(mapper.findRequestedApprovals(100)).willReturn(requestedApprovals);

        // When
        List<ApprovalRequestedListQueryDTO> result = approvalQueryService.findRequestedApprovals(100);

        // Then
        assertNotNull(result);

        verify(mapper).findRequestedApprovals(100);
    }

    @Test
    @DisplayName("결재 전체 조회 - 결재가 없는 경우 빈 리스트 반환")
    void testFindAllEmpty() {
        // Given
        given(mapper.findAll()).willReturn(Collections.emptyList());

        // When
        List<ApprovalQueryDTO> result = approvalQueryService.findAll();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(mapper).findAll();
    }
} 