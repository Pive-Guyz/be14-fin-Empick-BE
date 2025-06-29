package com.piveguyz.empickbackend.approvals.approval.command;

import com.piveguyz.empickbackend.approvals.approval.command.application.dto.ApproveRequestDTO;
import com.piveguyz.empickbackend.approvals.approval.command.application.dto.CreateApprovalCommandDTO;
import com.piveguyz.empickbackend.approvals.approval.command.application.dto.RejectRequestDTO;
import com.piveguyz.empickbackend.approvals.approval.command.application.service.ApprovalCommandServiceImpl;
import com.piveguyz.empickbackend.approvals.approval.command.domain.aggregate.ApprovalEntity;
import com.piveguyz.empickbackend.approvals.approval.command.domain.repository.ApprovalRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class ApprovalCommandTest {
    @Autowired
    private ApprovalCommandServiceImpl service;

    @Autowired
    private ApprovalRepository approvalRepository;

    private int writerId = 1;
    private int approverId = 1;
    private int categoryId = 101;
    private int itemId = 10101;

    @Test
    @DisplayName("결재 문서 등록")
    void createApproval_success() {
        CreateApprovalCommandDTO dto = CreateApprovalCommandDTO.builder()
                .categoryId(categoryId)
                .writerId(writerId)
                .contents(List.of(
                        CreateApprovalCommandDTO.ApprovalContentDTO.builder()
                                .itemId(itemId)
                                .content("내용")
                                .build()
                ))
                .build();

        Integer approvalId = service.createApproval(dto);

        assertThat(approvalRepository.findById(approvalId)).isPresent();
    }

    @Test
    @DisplayName("결재 문서 승인")
    void approve_success() {
        Integer approvalId = createSimpleApproval();

        ApproveRequestDTO dto = ApproveRequestDTO.builder()
                .approverId(approverId)
                .build();

        service.approve(approvalId, dto);

        ApprovalEntity updated = approvalRepository.findById(approvalId).orElseThrow();
        assertThat(updated.getStatus()).isIn(0, 1); // 진행 중 또는 완료
        assertThat(updated.getFirstApprovedAt()).isNotNull();
    }

    @Test
    @DisplayName("결재 문서 반려")
    void reject_success() {
        Integer approvalId = createSimpleApproval();

        RejectRequestDTO dto = RejectRequestDTO.builder()
                .approverId(approverId)
//                .reason("이유")
                .build();

        service.reject(approvalId, dto);

        ApprovalEntity updated = approvalRepository.findById(approvalId).orElseThrow();
        assertThat(updated.getStatus()).isEqualTo(-1);
    }

    // ================== 헬퍼 메서드 ==================

    private Integer createSimpleApproval() {
        CreateApprovalCommandDTO dto = CreateApprovalCommandDTO.builder()
                .categoryId(categoryId)
                .writerId(writerId)
                .contents(List.of(
                        CreateApprovalCommandDTO.ApprovalContentDTO.builder()
                                .itemId(itemId)
                                .content("내용")
                                .build()
                ))
                .build();

        return service.createApproval(dto);
    }
}