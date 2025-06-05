package com.piveguyz.empickbackend.employment.jobtests.question.query.dto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionOptionDTO {
    private int id;
    private int optionNumber;
    private String content;
}
