package com.piveguyz.empickbackend.employment.jobtests.question.query;

import com.piveguyz.empickbackend.employment.jobtests.question.query.dto.QuestionListQueryDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.query.dto.QuestionQueryDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.query.mapper.QuestionMapper;
import com.piveguyz.empickbackend.employment.jobtests.question.query.service.QuestionQueryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@Transactional
class QuestionQueryTest {
    @Autowired
    private QuestionQueryServiceImpl service;

    @Test
    @DisplayName("전체 문제 목록 조회")
    void getAllQuestions_success() {
        // when
        var result = service.getAllQuestions();

        // then
        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(List.class);
    }

    @Test
    @DisplayName("문제 ID로 상세 조회")
    void getQuestionById_success() {
        // given
        int questionId = 1;

        // when
        QuestionQueryDTO dto = service.getQuestionById(questionId);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(questionId);
    }
}