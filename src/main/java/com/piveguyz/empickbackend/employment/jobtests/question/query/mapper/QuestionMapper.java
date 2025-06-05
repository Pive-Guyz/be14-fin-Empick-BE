package com.piveguyz.empickbackend.employment.jobtests.question.query.mapper;

import com.piveguyz.empickbackend.employment.jobtests.question.query.dto.QuestionListQueryDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.query.dto.QuestionQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionMapper {
    List<QuestionListQueryDTO> selectAllQuestion();

    QuestionQueryDTO selectQuestionFullById(@Param("id") int id);
}
