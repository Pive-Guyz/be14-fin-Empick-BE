package com.piveguyz.empickbackend.employment.jobtests.question.command.application.dto;

import com.piveguyz.empickbackend.employment.jobtests.common.enums.JobtestDifficulty;
import com.piveguyz.empickbackend.employment.jobtests.grading.command.application.dto.CreateGradingCriteriaCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.aggregate.enums.QuestionType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class UpdateQuestionCommandDTO {
    private String content;
    private String detailContent;
    private QuestionType type;
    private JobtestDifficulty difficulty;
    private String answer;
    private int updatedMemberId;

    private List<CreateQuestionOptionCommandDTO> questionOptions;
    private List<CreateGradingCriteriaCommandDTO> gradingCriteria;

    @Builder
    public UpdateQuestionCommandDTO(String content, String detailContent, QuestionType type,
                                    JobtestDifficulty difficulty, String answer, int updatedMemberId) {
        this.content = content;
        this.detailContent = detailContent;
        this.type = type;
        this.difficulty = difficulty;
        this.answer = answer;
        this.updatedMemberId = updatedMemberId;
    }
}
