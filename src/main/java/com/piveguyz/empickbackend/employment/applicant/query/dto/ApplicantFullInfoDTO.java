package com.piveguyz.empickbackend.employment.applicant.query.dto;

import com.piveguyz.empickbackend.employment.applicant.command.domain.aggregate.ApplicationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ApplicantFullInfoDTO {
    // 지원자의 고유 식별자 (PK)
    private int applicantId;

    private Integer applicationId;

    // 지원자 이름
    private String name;

    // 지원자 연락처 (휴대폰 번호)
    private String phone;

    // 지원자 이메일 주소
    private String email;

    // 지원자 프로필 이미지 URL (선택적)
    private String profileUrl;

    // 지원자 생년월일 (yyyy-MM-dd 형식 문자열)
    private String birth;

    // 지원자 주소 (자택 또는 현재 거주지)
    private String address;

    // 해당 지원자가 지원한 채용 공고의 ID (recruitment 테이블 참조)
    private int recruitmentId;

    // 채용 공고 제목 (recruitment 테이블의 title 컬럼)
    private String recruitmentTitle;

    // 자기소개 평가 결과 ID (introduce_rating_result 테이블의 FK, nullable)
    private Integer introduceRatingResultId;

    private Integer jobId;

    private String jobName;

    // 지원서 제출 시각
    private LocalDateTime createdAt;

    // 지원자의 현재 전형 상태 (예: 서류합격, 불합격 등)
    private ApplicationStatus status;

    // 지원서 최종 수정 시각
    private LocalDateTime updatedAt;

    // 수정한 사원의 memberId (nullable, 시스템 처리일 수도 있음)
    private Integer updatedBy;

    // 실무테스트 할당 관련
    private Integer applicationJobtestId;
    private String applicationJobtestTitle;

}
