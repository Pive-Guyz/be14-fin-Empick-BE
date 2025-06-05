package com.piveguyz.empickbackend.employment.jobtests.question.query.service;

import com.piveguyz.empickbackend.employment.jobtests.question.query.dto.QuestionListQueryDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.query.dto.QuestionQueryDTO;

import java.util.List;

public interface QuestionQueryService {
    List<QuestionListQueryDTO> getAllQuestions();

    QuestionQueryDTO getQuestionById(int id);
}
