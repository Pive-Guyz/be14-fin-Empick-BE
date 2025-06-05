package com.piveguyz.empickbackend.employment.jobtests.question.query.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.jobtests.question.query.dto.QuestionListQueryDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.query.dto.QuestionQueryDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.query.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionQueryServiceImpl implements QuestionQueryService {
    private final QuestionMapper questionMapper;

    public QuestionQueryServiceImpl(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Override
    public List<QuestionListQueryDTO> getAllQuestions() {
        return questionMapper.selectAllQuestion();
    }

    @Override
    public QuestionQueryDTO getQuestionById(int id) {
        QuestionQueryDTO dto = questionMapper.selectQuestionFullById(id);
        if (dto == null) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_QUESTION_NOT_FOUND);
        }
        return dto;
    }
}
