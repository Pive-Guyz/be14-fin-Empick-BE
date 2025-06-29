package com.piveguyz.empickbackend.approvals.approval.command.application.dto;

import lombok.*;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateApprovalCommandDTO {
    // 승인 요청서 등록 DTO (최초 생성)

    private Integer categoryId;
    private Integer writerId;
    private List<ApprovalContentDTO> contents;

    private Integer approvalId;   // 취소 대상 결재 id

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ApprovalContentDTO {
        private Integer itemId;
        private String content;
    }
}
