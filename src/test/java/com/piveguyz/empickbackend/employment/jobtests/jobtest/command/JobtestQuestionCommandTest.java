package com.piveguyz.empickbackend.employment.jobtests.jobtest.command;

import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.CreateJobtestQuestionResponseDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.UpdateJobtestQuestionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.service.JobtestQuestionCommandServiceImpl;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.aggregate.QuestionEntity;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.aggregate.enums.QuestionType;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.repository.QuestionRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.piveguyz.empickbackend.employment.jobtests.common.enums.JobtestDifficulty;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.CreateJobtestQuestionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.aggregate.JobtestQuestionEntity;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.repository.JobtestQuestionRepository;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.repository.JobtestRepository;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.aggregate.JobtestEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class JobtestQuestionCommandTest {
    @Autowired
    private JobtestQuestionCommandServiceImpl service;

    @Autowired
    private JobtestRepository jobtestRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private JobtestQuestionRepository jobtestQuestionRepository;

    @Test
    @DisplayName("실무테스트에 문제 할당")
    void createJobtestQuestion_success() {
        // given
        int jobtestId = 1;

        int questionId = 34;

        CreateJobtestQuestionCommandDTO dto = CreateJobtestQuestionCommandDTO.builder()
                .questionId(questionId)
                .score(20)
                .build();

        // when
        CreateJobtestQuestionResponseDTO response = service.createJobtestQuestion(dto, jobtestId);

        // then
        assertThat(response.getQuestionId()).isEqualTo(questionId);
        assertThat(response.getJobTestId()).isEqualTo(jobtestId);
        assertThat(response.getScore()).isEqualTo(20);
    }

    @Test
    @DisplayName("실무테스트 문제 할당 정보 수정")
    void updateJobtestQuestion_success() {
        // given
        int jobtestId = 1;
        int questionId = 1;

        JobtestQuestionEntity saved = jobtestQuestionRepository.save(
                JobtestQuestionEntity.builder()
                        .jobTestId(jobtestId)
                        .questionId(questionId)
                        .score(10)
                        .optionNumber(1)
                        .build()
        );

        UpdateJobtestQuestionCommandDTO dto = UpdateJobtestQuestionCommandDTO.builder()
                .score(50)
                .build();

        // when
        UpdateJobtestQuestionCommandDTO updated = service.updateJobtestQuestion(saved.getId(), dto);

        // then
        JobtestQuestionEntity reloaded = jobtestQuestionRepository.findById(saved.getId()).orElseThrow();
        assertThat(reloaded.getScore()).isEqualTo(50);
    }

    @Test
    @DisplayName("실무테스트 문제 할당 관계 삭제")
    void deleteJobtestQuestion_success() {
        // given
        int jobtestQuestionId = 1;

        // when
        int deletedId = service.deleteJobtestQuestion(jobtestQuestionId);

        // then
        assertThat(deletedId).isEqualTo(jobtestQuestionId);
        assertThat(jobtestQuestionRepository.findById(jobtestQuestionId)).isEmpty();
    }
}