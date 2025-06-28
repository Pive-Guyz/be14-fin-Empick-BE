package com.piveguyz.empickbackend.approvals.approval.query.service;

import com.piveguyz.empickbackend.approvals.approval.query.dto.ApprovalLineQueryDTO;
import com.piveguyz.empickbackend.approvals.approval.query.mapper.ApprovalQueryMapper;
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
@DisplayName("결재 라인 Service 테스트")
class ApprovalLineServiceTest {

    @Mock
    private ApprovalQueryMapper approvalQueryMapper;

    @InjectMocks
    private ApprovalLineServiceImpl approvalLineService;

    private List<ApprovalLineQueryDTO> mockApprovalLines;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        ApprovalLineQueryDTO line1 = new ApprovalLineQueryDTO(
                1, 2, "과장", 101, "김과장", "개발팀"
        );
        
        ApprovalLineQueryDTO line2 = new ApprovalLineQueryDTO(
                2, 3, "부장", 102, "박부장", "개발팀"
        );

        mockApprovalLines = Arrays.asList(line1, line2);
    }

    @Test
    @DisplayName("결재 라인 미리보기 - 카테고리와 작성자 ID로 결재 라인 조회 시 성공")
    void testGetApprovalLinePreview() {
        // Given
        given(approvalQueryMapper.selectApprovalLinePreview(1, 100))
                .willReturn(mockApprovalLines);

        // When
        List<ApprovalLineQueryDTO> result = approvalLineService.getApprovalLinePreview(1, 100);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("김과장", result.get(0).getMemberName());
        assertEquals("박부장", result.get(1).getMemberName());
        assertEquals("과장", result.get(0).getPositionName());
        assertEquals("부장", result.get(1).getPositionName());

        verify(approvalQueryMapper).selectApprovalLinePreview(1, 100);
    }

    @Test
    @DisplayName("결재 라인 미리보기 실패 - 결재 라인이 없는 경우 예외 발생")
    void testGetApprovalLinePreviewNotFound() {
        // Given
        given(approvalQueryMapper.selectApprovalLinePreview(999, 100))
                .willReturn(null);

        // When & Then
        assertThrows(BusinessException.class, () -> {
            approvalLineService.getApprovalLinePreview(999, 100);
        });

        verify(approvalQueryMapper).selectApprovalLinePreview(999, 100);
    }

    @Test
    @DisplayName("결재 라인 미리보기 실패 - 결재 라인이 빈 리스트인 경우 예외 발생")
    void testGetApprovalLinePreviewEmpty() {
        // Given
        given(approvalQueryMapper.selectApprovalLinePreview(1, 999))
                .willReturn(Collections.emptyList());

        // When & Then
        assertThrows(BusinessException.class, () -> {
            approvalLineService.getApprovalLinePreview(1, 999);
        });

        verify(approvalQueryMapper).selectApprovalLinePreview(1, 999);
    }

    @Test
    @DisplayName("결재 라인 미리보기 - 단일 결재자인 경우 성공")
    void testGetApprovalLinePreviewSingleApprover() {
        // Given
        ApprovalLineQueryDTO singleLine = new ApprovalLineQueryDTO(
                1, 2, "과장", 101, "김과장", "개발팀"
        );
        List<ApprovalLineQueryDTO> singleLineList = Arrays.asList(singleLine);
        
        given(approvalQueryMapper.selectApprovalLinePreview(2, 200))
                .willReturn(singleLineList);

        // When
        List<ApprovalLineQueryDTO> result = approvalLineService.getApprovalLinePreview(2, 200);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("김과장", result.get(0).getMemberName());
        assertEquals(1, result.get(0).getOrder());

        verify(approvalQueryMapper).selectApprovalLinePreview(2, 200);
    }
} 