package com.piveguyz.empickbackend.employment.jobtests.answer.command;

import com.piveguyz.empickbackend.employment.jobtests.answer.command.application.dto.CreateAnswerCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.answer.command.application.service.AnswerCommandServiceImpl;
import com.piveguyz.empickbackend.employment.jobtests.answer.command.domain.aggregate.AnswerEntity;
import com.piveguyz.empickbackend.employment.jobtests.answer.command.domain.aggregate.enums.CorrectType;
import com.piveguyz.empickbackend.employment.jobtests.answer.command.domain.repository.AnswerRepository;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.repository.ApplicationJobtestRepository;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.repository.JobtestQuestionRepository;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.aggregate.QuestionEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AnswerCommandTest {

    @Autowired
    private AnswerCommandServiceImpl answerService;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ApplicationJobtestRepository applicationJobtestRepository;

    @Autowired
    private JobtestQuestionRepository jobtestQuestionRepository;

    @Test
    @DisplayName("실무테스트 답안 생성")
    void createAnswer_firstTime() {
        // given
        int appJobTestId = 1;
        int questionId = 10;
        String content = "정답";

        CreateAnswerCommandDTO dto = CreateAnswerCommandDTO.builder()
                .applicationJobTestId(appJobTestId)
                .questionId(questionId)
                .content(content)
                .build();

        // when
        var response = answerService.createAnswer(dto);

        // then
        AnswerEntity saved = answerRepository.findByApplicationJobTestIdAndQuestionId(response.getApplicationJobTestId(), response.getQuestionId()).orElseThrow();
        assertThat(saved.getAttempt()).isEqualTo(1);
        assertThat(saved.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("자동 채점 - 정답이면 CorrectType.CORRECT로 처리")
    void autoGrade_correctAnswer() {
        // given
        int appJobTestId = 1;
        int questionId = 1;

        QuestionEntity question = QuestionEntity.builder()
                .id(questionId)
                .answer("정답")
                .build();

        AnswerEntity answer = AnswerEntity.builder()
                .applicationJobTestId(appJobTestId)
                .questionId(questionId)
                .content("정답")
                .build();

        // when
        answerService.autoGrade(answer, question);

        // then
        assertThat(answer.getIsCorrect()).isEqualTo(CorrectType.CORRECT);
        assertThat(answer.getScore()).isGreaterThan(0);
    }

    @Test
    @DisplayName("자동 채점 - 오답이면 CorrectType.WRONG으로 처리")
    void autoGrade_wrongAnswer() {
        // given
        int appJobTestId = 1;
        int questionId = 1;

        QuestionEntity question = QuestionEntity.builder()
                .id(questionId)
                .answer("정답")
                .build();

        AnswerEntity answer = AnswerEntity.builder()
                .applicationJobTestId(appJobTestId)
                .questionId(questionId)
                .content("오답")
                .build();

        // when
        answerService.autoGrade(answer, question);

        // then
        assertThat(answer.getIsCorrect()).isEqualTo(CorrectType.WRONG);
        assertThat(answer.getScore()).isEqualTo(0);
    }

    @Test
    @DisplayName("답안 삭제")
    void deleteAnswer_success() {
        // given: 먼저 답안을 하나 저장
        AnswerEntity answer = AnswerEntity.builder()
                .applicationJobTestId(1)
                .questionId(1)
                .content("삭제할 답변")
                .attempt(1)
                .build();
        AnswerEntity saved = answerRepository.save(answer);
        int savedId = saved.getId();

        // when: 삭제 실행
        Integer deletedId = answerService.deleteAnswer(savedId);

        // then: 반환된 ID 일치하고, 실제 DB에는 존재하지 않아야 함
        assertThat(deletedId).isEqualTo(savedId);
        boolean exists = answerRepository.findById(savedId).isPresent();
        assertThat(exists).isFalse();
    }
}
