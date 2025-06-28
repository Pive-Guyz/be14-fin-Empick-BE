package com.piveguyz.empickbackend.approvals.approval.command.application.service;

import com.piveguyz.empickbackend.approvals.approval.command.application.dto.*;
import com.piveguyz.empickbackend.approvals.approval.command.domain.aggregate.ApprovalContentEntity;
import com.piveguyz.empickbackend.approvals.approval.command.domain.aggregate.ApprovalEntity;
import com.piveguyz.empickbackend.approvals.approval.command.domain.aggregate.ApprovalLineEntity;
import com.piveguyz.empickbackend.approvals.approval.command.domain.repository.*;
import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.orgstructure.member.command.domain.aggregate.MemberEntity;
import com.piveguyz.empickbackend.orgstructure.member.command.domain.repository.MemberRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("결재 Command Service 테스트")
class ApprovalCommandServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ApprovalRepository approvalRepository;
    @Mock
    private ApprovalCategoryItemRepository approvalCategoryItemRepository;
    @Mock
    private ApprovalContentRepository approvalContentRepository;
    @Mock
    private ApprovalLineRepository approvalLineRepository;

    @InjectMocks
    private ApprovalCommandServiceImpl approvalCommandService;

    private CreateApprovalCommandDTO mockCreateDto;
    private ApprovalEntity mockApprovalEntity;
    private MemberEntity mockWriter;
    private List<ApprovalLineEntity> mockApprovalLines;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        // 결재 생성 DTO 설정
        CreateApprovalCommandDTO.ApprovalContentDTO content1 = 
                new CreateApprovalCommandDTO.ApprovalContentDTO();
        content1.setItemId(1);
        content1.setContent("휴가 사유입니다.");
        
        CreateApprovalCommandDTO.ApprovalContentDTO content2 = 
                new CreateApprovalCommandDTO.ApprovalContentDTO();
        content2.setItemId(2);
        content2.setContent("2024-01-01 ~ 2024-01-03");

        mockCreateDto = CreateApprovalCommandDTO.builder()
                .categoryId(1)
                .writerId(100)
                .contents(Arrays.asList(content1, content2))
                .build();

        // 작성자 설정
        mockWriter = MemberEntity.builder()
                .id(100)
                .name("김작성자")
                .departmentId(10)
                .positionId(1)
                .build();

        // 결재라인 설정
        ApprovalLineEntity line1 = new ApprovalLineEntity();
        line1.setApprovalCategoryId(1);
        line1.setPositionId(2);
        line1.setStepOrder(1);

        ApprovalLineEntity line2 = new ApprovalLineEntity();
        line2.setApprovalCategoryId(1);
        line2.setPositionId(3);
        line2.setStepOrder(2);

        mockApprovalLines = Arrays.asList(line1, line2);

        // 결재 엔티티 설정
        mockApprovalEntity = ApprovalEntity.builder()
                .categoryId(1)
                .writerId(100)
                .firstApproverId(101)
                .secondApproverId(102)
                .thirdApproverId(null)
                .fourthApproverId(null)
                .approvalId(null)
                .build();
    }

    @Test
    @DisplayName("결재 생성 - 정상적인 데이터로 생성 시 성공")
    void testCreateApproval() {
        // Given
        given(approvalCategoryItemRepository.existsByIdAndCategoryId(1, 1)).willReturn(true);
        given(approvalCategoryItemRepository.existsByIdAndCategoryId(2, 1)).willReturn(true);
        given(memberRepository.findById(100)).willReturn(Optional.of(mockWriter));
        given(approvalLineRepository.findByApprovalCategoryIdOrderByStepOrderAsc(1))
                .willReturn(mockApprovalLines);
        
        // 결재자들 설정
        MemberEntity approver1 = MemberEntity.builder().id(101).departmentId(10).positionId(2).build();
        MemberEntity approver2 = MemberEntity.builder().id(102).departmentId(10).positionId(3).build();
        
        given(memberRepository.findFirstByDepartmentIdAndPositionId(10, 2))
                .willReturn(Optional.of(approver1));
        given(memberRepository.findFirstByDepartmentIdAndPositionId(10, 3))
                .willReturn(Optional.of(approver2));
        
        // save 후 id가 설정된 entity 반환하도록 mock 설정
        given(approvalRepository.save(any(ApprovalEntity.class))).willAnswer(invocation -> {
            ApprovalEntity savedEntity = invocation.getArgument(0);
            savedEntity.setId(1); // 자동 생성된 ID 시뮬레이션
            return savedEntity;
        });

        // When
        Integer result = approvalCommandService.createApproval(mockCreateDto);

        // Then
        assertNotNull(result);
        assertEquals(1, result);

        verify(approvalRepository).save(any(ApprovalEntity.class));
        verify(approvalContentRepository).saveAll(any(List.class));
    }

    @Test
    @DisplayName("결재 생성 실패 - 카테고리 ID가 없는 경우 예외 발생")
    void testCreateApprovalMissingCategory() {
        // Given
        CreateApprovalCommandDTO invalidDto = CreateApprovalCommandDTO.builder()
                .categoryId(null)
                .writerId(100)
                .contents(Arrays.asList())
                .build();

        // When & Then
        assertThrows(BusinessException.class, () -> {
            approvalCommandService.createApproval(invalidDto);
        });

        verify(approvalRepository, never()).save(any(ApprovalEntity.class));
    }

    @Test
    @DisplayName("결재 생성 실패 - 작성자 ID가 없는 경우 예외 발생")
    void testCreateApprovalMissingWriter() {
        // Given
        CreateApprovalCommandDTO invalidDto = CreateApprovalCommandDTO.builder()
                .categoryId(1)
                .writerId(null)
                .contents(Arrays.asList())
                .build();

        // When & Then
        assertThrows(BusinessException.class, () -> {
            approvalCommandService.createApproval(invalidDto);
        });

        verify(approvalRepository, never()).save(any(ApprovalEntity.class));
    }

    @Test
    @DisplayName("결재 생성 실패 - 내용이 없는 경우 예외 발생")
    void testCreateApprovalMissingContent() {
        // Given
        CreateApprovalCommandDTO invalidDto = CreateApprovalCommandDTO.builder()
                .categoryId(1)
                .writerId(100)
                .contents(null)
                .build();

        // When & Then
        assertThrows(BusinessException.class, () -> {
            approvalCommandService.createApproval(invalidDto);
        });

        verify(approvalRepository, never()).save(any(ApprovalEntity.class));
    }

    @Test
    @DisplayName("결재 생성 실패 - 존재하지 않는 작성자인 경우 예외 발생")
    void testCreateApprovalWriterNotFound() {
        // Given
        given(approvalCategoryItemRepository.existsByIdAndCategoryId(anyInt(), anyInt())).willReturn(true);
        given(memberRepository.findById(100)).willReturn(Optional.empty());

        // When & Then
        assertThrows(BusinessException.class, () -> {
            approvalCommandService.createApproval(mockCreateDto);
        });

        verify(approvalRepository, never()).save(any(ApprovalEntity.class));
    }

    @Test
    @DisplayName("결재 승인 - 정상적인 승인 요청 시 성공")
    void testApprove() {
        // Given
        ApprovalEntity pendingApproval = ApprovalEntity.builder()
                .categoryId(1)
                .writerId(100)
                .firstApproverId(101)
                .secondApproverId(null)
                .thirdApproverId(null)
                .fourthApproverId(null)
                .approvalId(null)
                .build();
        pendingApproval.setStatus(0); // 진행중

        ApproveRequestDTO approveDto = ApproveRequestDTO.builder()
                .approverId(101)
                .build();

        given(approvalRepository.findById(1)).willReturn(Optional.of(pendingApproval));
        given(memberRepository.existsById(101)).willReturn(true);

        // When
        assertDoesNotThrow(() -> {
            approvalCommandService.approve(1, approveDto);
        });

        // Then
        verify(approvalRepository).findById(1);
        verify(approvalRepository).save(any(ApprovalEntity.class));
    }

    @Test
    @DisplayName("결재 반려 - 정상적인 반려 요청 시 성공")
    void testReject() {
        // Given
        ApprovalEntity pendingApproval = ApprovalEntity.builder()
                .categoryId(1)
                .writerId(100)
                .firstApproverId(101)
                .secondApproverId(null)
                .thirdApproverId(null)
                .fourthApproverId(null)
                .approvalId(null)
                .build();
        pendingApproval.setStatus(0); // 진행중

        RejectRequestDTO rejectDto = RejectRequestDTO.builder()
                .approverId(101)
                .rejectReason("내용이 부족합니다.")
                .build();

        given(approvalRepository.findById(1)).willReturn(Optional.of(pendingApproval));

        // When
        assertDoesNotThrow(() -> {
            approvalCommandService.reject(1, rejectDto);
        });

        // Then
        verify(approvalRepository).findById(1);
        verify(approvalRepository).save(any(ApprovalEntity.class));
    }
} 