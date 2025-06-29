package com.piveguyz.empickbackend.employment.jobtests.question.command;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.dto.CreateQuestionOptionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.dto.CreateQuestionOptionResponse;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.dto.UpdateQuestionOptionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.service.QuestionOptionCommandServiceImpl;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.aggregate.QuestionEntity;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.aggregate.QuestionOptionEntity;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.aggregate.enums.QuestionType;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.repository.QuestionOptionRepository;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class QuestionOptionCommandTest {
    @Autowired
    private QuestionOptionCommandServiceImpl service;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionOptionRepository questionOptionRepository;

    @Test
    @DisplayName("실무테스트 문제 선택지 등록")
    void createQuestionOption_success() {
        // given
        int questionId = 38;

        CreateQuestionOptionCommandDTO dto = CreateQuestionOptionCommandDTO.builder()
                .content("선택지 1")
                .build();

        // when
        CreateQuestionOptionResponse response = service.createQuestionOption(dto, questionId);

        // then
        assertThat(response.getQuestionId()).isEqualTo(questionId);
        assertThat(response.getOptionNumber()).isEqualTo(1);
    }

    @Test
    @DisplayName("실무테스트 문제 선택지 수정")
    void updateQuestionOption_success() {
        // given
        QuestionOptionEntity option = questionOptionRepository.save(
                QuestionOptionEntity.builder()
                        .questionId(1)
                        .content("기존 선택지")
                        .optionNumber(1)
                        .build()
        );

        UpdateQuestionOptionCommandDTO dto = UpdateQuestionOptionCommandDTO.builder()
                .content("수정된 선택지")
                .optionNumber(2)
                .build();

        // when
        UpdateQuestionOptionCommandDTO updated = service.updateQuestionOption(option.getId(), dto);

        // then
        QuestionOptionEntity result = questionOptionRepository.findById(option.getId()).orElseThrow();
        assertThat(result.getContent()).isEqualTo("수정된 선택지");
        assertThat(result.getOptionNumber()).isEqualTo(2);
    }

    @Test
    @DisplayName("실무테스트 문제에 할당된 모든 선택지 삭제")
    void deleteQuestionOption_success() {
        // given
        int questionId = 38;
        questionOptionRepository.saveAll(List.of(
                QuestionOptionEntity.builder().questionId(questionId).content("1").optionNumber(1).build(),
                QuestionOptionEntity.builder().questionId(questionId).content("2").optionNumber(2).build()
        ));

        // when
        service.deleteQuestionOption(questionId);

        // then
        int count = questionOptionRepository.countByQuestionId(questionId);
        assertThat(count).isEqualTo(0);
    }
}