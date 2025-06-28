package com.piveguyz.empickbackend.approvals.approval.query.service;

import com.piveguyz.empickbackend.approvals.approval.query.dto.*;
import com.piveguyz.empickbackend.approvals.approval.query.mapper.ApprovalQueryMapper;
import com.piveguyz.empickbackend.approvals.approvalCategoryItem.query.dto.ApprovalCategoryItemQueryDTO;
import com.piveguyz.empickbackend.approvals.approvalCategoryItem.query.mapper.ApprovalCategoryItemQueryMapper;
import com.piveguyz.empickbackend.approvals.approvalContent.query.dto.ApprovalContentQueryDTO;
import com.piveguyz.empickbackend.approvals.approvalContent.query.mapper.ApprovalContentQueryMapper;
import com.piveguyz.empickbackend.common.enums.InputType;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("결재 상세 Query Service 테스트")
class ApprovalDetailQueryServiceTest {

    @Mock
    private ApprovalQueryMapper approvalQueryMapper;
    @Mock
    private ApprovalContentQueryMapper contentMapper;
    @Mock
    private ApprovalCategoryItemQueryMapper itemMapper;

    @InjectMocks
    private ApprovalDetailQueryServiceImpl approvalDetailQueryService;

    private ApprovalReceivedDetailQueryDTO mockReceivedDetail;
    private ApprovalRequestedDetailQueryDTO mockRequestedDetail;
    private List<ApprovalContentQueryDTO> mockContents;
    private List<ApprovalCategoryItemQueryDTO> mockItems;
    private List<ApprovalLineDetailDTO> mockApprovers;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        LocalDateTime now = LocalDateTime.now();

        // 받은 결재 상세 정보
        mockReceivedDetail = new ApprovalReceivedDetailQueryDTO();
        mockReceivedDetail.setApprovalId(1);
        mockReceivedDetail.setCategoryId(1);
        mockReceivedDetail.setWriterName("김작성자");
        mockReceivedDetail.setCreatedAt(now);

        // 요청한 결재 상세 정보
        mockRequestedDetail = new ApprovalRequestedDetailQueryDTO();
        mockRequestedDetail.setApprovalId(1);
        mockRequestedDetail.setCategoryId(1);
        mockRequestedDetail.setWriterName("김작성자");
        mockRequestedDetail.setCreatedAt(now);

        // 결재 내용
        mockContents = Arrays.asList(
                new ApprovalContentQueryDTO(1, 1, 1, "휴가 사유입니다."),
                new ApprovalContentQueryDTO(2, 1, 2, "2024-01-01 ~ 2024-01-03")
        );

        // 카테고리 항목
        mockItems = Arrays.asList(
                ApprovalCategoryItemQueryDTO.builder()
                        .id(1)
                        .categoryId(1)
                        .name("휴가 사유")
                        .inputType(InputType.TEXT)
                        .build(),
                ApprovalCategoryItemQueryDTO.builder()
                        .id(2)
                        .categoryId(1)
                        .name("휴가 기간")
                        .inputType(InputType.DATE)
                        .build()
        );

        // 결재자 목록
        mockApprovers = Arrays.asList(
                new ApprovalLineDetailDTO(1, 101, "김과장", "과장", "개발팀", true, now),
                new ApprovalLineDetailDTO(2, 102, "박부장", "부장", "개발팀", false, null)
        );
    }

    @Test
    @DisplayName("받은 결재 상세 조회 - 정상적인 결재 ID와 회원 ID로 조회 시 성공")
    void testGetReceivedApprovalDetail() {
        // Given
        given(approvalQueryMapper.findApprovalBasicDetail(1)).willReturn(mockReceivedDetail);
        given(contentMapper.findByApprovalId(1)).willReturn(mockContents);
        given(itemMapper.findByCategoryId(1)).willReturn(mockItems);
        given(approvalQueryMapper.findApproverDetails(1)).willReturn(mockApprovers);
        given(approvalQueryMapper.isMyTurn(1, 101)).willReturn(true);

        // When
        ApprovalReceivedDetailQueryDTO result = approvalDetailQueryService.getReceivedApprovalDetail(1, 101);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getApprovalId());
        assertEquals("김작성자", result.getWriterName());
        assertNotNull(result.getItems());
        assertEquals(2, result.getItems().size());
        assertNotNull(result.getApprovers());
        assertEquals(2, result.getApprovers().size());
        assertTrue(result.isMyTurn());

        verify(approvalQueryMapper).findApprovalBasicDetail(1);
        verify(contentMapper).findByApprovalId(1);
        verify(itemMapper).findByCategoryId(1);
        verify(approvalQueryMapper).findApproverDetails(1);
        verify(approvalQueryMapper).isMyTurn(1, 101);
    }

    @Test
    @DisplayName("받은 결재 상세 조회 실패 - 존재하지 않는 결재 ID인 경우 예외 발생")
    void testGetReceivedApprovalDetailNotFound() {
        // Given
        given(approvalQueryMapper.findApprovalBasicDetail(999)).willReturn(null);

        // When & Then
        assertThrows(BusinessException.class, () -> {
            approvalDetailQueryService.getReceivedApprovalDetail(999, 101);
        });

        verify(approvalQueryMapper).findApprovalBasicDetail(999);
    }

    @Test
    @DisplayName("요청한 결재 상세 조회 - 정상적인 결재 ID로 조회 시 성공")
    void testGetRequestedApprovalDetail() {
        // Given
        given(approvalQueryMapper.findRequestedApprovalBasicDetail(1)).willReturn(mockRequestedDetail);
        given(contentMapper.findByApprovalId(1)).willReturn(mockContents);
        given(itemMapper.findByCategoryId(1)).willReturn(mockItems);
        given(approvalQueryMapper.findApproverDetails(1)).willReturn(mockApprovers);

        // When
        ApprovalRequestedDetailQueryDTO result = approvalDetailQueryService.getRequestedApprovalDetail(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getApprovalId());
        assertEquals("김작성자", result.getWriterName());
        assertNotNull(result.getItems());
        assertEquals(2, result.getItems().size());
        assertNotNull(result.getApprovers());
        assertEquals(2, result.getApprovers().size());

        verify(approvalQueryMapper).findRequestedApprovalBasicDetail(1);
        verify(contentMapper).findByApprovalId(1);
        verify(itemMapper).findByCategoryId(1);
        verify(approvalQueryMapper).findApproverDetails(1);
    }

    @Test
    @DisplayName("요청한 결재 상세 조회 실패 - 존재하지 않는 결재 ID인 경우 예외 발생")
    void testGetRequestedApprovalDetailNotFound() {
        // Given
        given(approvalQueryMapper.findRequestedApprovalBasicDetail(999)).willReturn(null);

        // When & Then
        assertThrows(BusinessException.class, () -> {
            approvalDetailQueryService.getRequestedApprovalDetail(999);
        });

        verify(approvalQueryMapper).findRequestedApprovalBasicDetail(999);
    }

    @Test
    @DisplayName("받은 결재 상세 조회 - 내 차례가 아닌 경우 성공")
    void testGetReceivedApprovalDetailNotMyTurn() {
        // Given
        given(approvalQueryMapper.findApprovalBasicDetail(1)).willReturn(mockReceivedDetail);
        given(contentMapper.findByApprovalId(1)).willReturn(mockContents);
        given(itemMapper.findByCategoryId(1)).willReturn(mockItems);
        given(approvalQueryMapper.findApproverDetails(1)).willReturn(mockApprovers);
        given(approvalQueryMapper.isMyTurn(1, 102)).willReturn(false);

        // When
        ApprovalReceivedDetailQueryDTO result = approvalDetailQueryService.getReceivedApprovalDetail(1, 102);

        // Then
        assertNotNull(result);
        assertFalse(result.isMyTurn());

        verify(approvalQueryMapper).isMyTurn(1, 102);
    }
} 