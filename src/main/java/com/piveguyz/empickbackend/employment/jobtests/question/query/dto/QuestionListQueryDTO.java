package com.piveguyz.empickbackend.employment.jobtests.question.query.dto;

import com.piveguyz.empickbackend.employment.jobtests.common.enums.JobtestDifficulty;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.aggregate.enums.QuestionType;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class QuestionListQueryDTO {
    // 문제 내용, 유형, 난이도, 출제자, 최종 수정자
    private int id;
    private String content;
    private QuestionType type;
    private JobtestDifficulty difficulty;
    private int createdMemberId;
    private int updatedMemberId;
}
