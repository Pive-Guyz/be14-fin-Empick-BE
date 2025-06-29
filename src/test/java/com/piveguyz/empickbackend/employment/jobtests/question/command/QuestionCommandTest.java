package com.piveguyz.empickbackend.employment.jobtests.question.command;

import com.piveguyz.empickbackend.employment.jobtests.answer.command.domain.repository.AnswerRepository;
import com.piveguyz.empickbackend.employment.jobtests.common.enums.JobtestDifficulty;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.repository.JobtestQuestionRepository;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.dto.CreateQuestionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.dto.CreateQuestionOptionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.dto.DeleteQuestionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.dto.UpdateQuestionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.service.QuestionCommandServiceImpl;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.aggregate.QuestionEntity;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.aggregate.QuestionOptionEntity;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.aggregate.enums.QuestionType;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.repository.QuestionOptionRepository;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.repository.QuestionRepository;
import com.piveguyz.empickbackend.orgstructure.member.command.domain.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class QuestionCommandTest {
    @Autowired
    private QuestionCommandServiceImpl service;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private QuestionOptionRepository questionOptionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    @DisplayName("실무테스트 문제 등록")
    void createQuestion_success() {
        // given
        int memberId = 1;

        CreateQuestionCommandDTO dto = CreateQuestionCommandDTO.builder()
                .content("Spring이란?")
                .type(QuestionType.SUBJECTIVE)
                .answer("DI")
                .difficulty(JobtestDifficulty.EASY)
                .createdMemberId(memberId)
                .build();

        // when
        int createdId = service.createQuestion(dto);

        // then
        assertThat(questionRepository.findById(createdId)).isPresent();
    }

    @Test
    @DisplayName("실무테스트 문제 수정")
    void updateQuestion_multiple_success() {
        // given
        int memberId = 1;
        int questionId = 38;

        UpdateQuestionCommandDTO dto = UpdateQuestionCommandDTO.builder()
                .updatedMemberId(memberId)
                .difficulty(JobtestDifficulty.EASY)
                .build();

        // when
        UpdateQuestionCommandDTO result = service.updateQuestion(questionId, dto);

        // then
        QuestionEntity updated = questionRepository.findById(questionId).orElseThrow();
        assertThat(updated.getDifficulty()).isEqualTo(JobtestDifficulty.EASY);
    }

    @Test
    @DisplayName("실무테스트 문제 삭제")
    void deleteQuestion_success() {
        // given
        int questionId = 38;

        // when
        DeleteQuestionCommandDTO deleted = service.deleteQuestion(questionId);

        // then
        assertThat(deleted.getId()).isEqualTo(questionId);
        assertThat(questionRepository.findById(questionId)).isEmpty();
    }
    
}