package com.piveguyz.empickbackend.employment.jobtests.question.query.dto;

import com.piveguyz.empickbackend.employment.jobtests.common.enums.JobtestDifficulty;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.aggregate.enums.QuestionType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class QuestionQueryDTO {
    // 내용, 상세 내용, 유형, 난이도, 답안, 생성자, 생성일, 최종 수정자, 최종 수정일
    private int id;
    private String content;
    private String detailContent;
    private QuestionType type;
    private JobtestDifficulty difficulty;
    private String answer;
    private int createdMemberId;
    private Integer updatedMemberId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 문제 선지
    private List<QuestionOptionDTO> options;

    // 해당 문제를 사용하고 있는 실무 테스트 id
    private List<UsedJobtestDTO> usedJobTests;
}
