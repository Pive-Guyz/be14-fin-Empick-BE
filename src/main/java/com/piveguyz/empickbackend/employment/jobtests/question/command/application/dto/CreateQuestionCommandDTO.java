package com.piveguyz.empickbackend.employment.jobtests.question.command.application.dto;

import com.piveguyz.empickbackend.employment.jobtests.common.enums.JobtestDifficulty;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.aggregate.enums.QuestionType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class CreateQuestionCommandDTO {
    private String content;
    private String detailContent;
    private QuestionType type;
    private JobtestDifficulty difficulty;
    private String answer;

    private int createdMemberId;

    @Builder
    public CreateQuestionCommandDTO(String content, String detailContent, QuestionType type, JobtestDifficulty difficulty, String answer, int createdMemberId) {
        this.content = content;
        this.detailContent = detailContent;
        this.type = type;
        this.difficulty = difficulty;
        this.answer = answer;
        this.createdMemberId = createdMemberId;
    }
}
