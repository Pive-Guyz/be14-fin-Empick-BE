package com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto;

import com.piveguyz.empickbackend.employment.jobtests.common.enums.JobtestDifficulty;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.aggregate.enums.QuestionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobtestQuestionQueryDTO {
    private int id;
    private int score;
    private String content;
    private int optionNumber;
    private QuestionType type;
    private JobtestDifficulty difficulty;
    private int questionId;
}
