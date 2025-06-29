package com.piveguyz.empickbackend.employment.jobtests.jobtest.command;

import com.piveguyz.empickbackend.employment.jobtests.common.enums.JobtestDifficulty;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.CreateJobtestCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.CreateJobtestQuestionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.UpdateJobtestCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.service.JobtestCommandServiceImpl;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.aggregate.JobtestQuestionEntity;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.repository.ApplicationJobtestRepository;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.repository.JobtestQuestionRepository;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.repository.JobtestRepository;
import com.piveguyz.empickbackend.orgstructure.member.command.domain.repository.MemberRepository;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.aggregate.JobtestEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class JobtestCommandTest {
    @Autowired
    private JobtestCommandServiceImpl service;

    @Autowired
    private JobtestRepository jobtestRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ApplicationJobtestRepository applicationJobtestRepository;

    @Autowired
    private JobtestQuestionRepository jobtestQuestionRepository;

    @Test
    @DisplayName("실무테스트 생성")
    void createJobtest_success() {
        // given
        int memberId = 1;

        CreateJobtestCommandDTO dto = CreateJobtestCommandDTO.builder()
                .title("실무테스트 1")
                .difficulty(JobtestDifficulty.EASY)
                .testTime(60)
                .startedAt(LocalDateTime.now())
                .endedAt(LocalDateTime.now().plusDays(1))
                .createdMemberId(memberId)
                .jobtestQuestions(List.of(
                        CreateJobtestQuestionCommandDTO.builder().questionId(1).score(40).build(),
                        CreateJobtestQuestionCommandDTO.builder().questionId(2).score(60).build()
                ))
                .build();

        // when
        int createdId = service.createJobtest(dto);

        // then
        assertThat(jobtestRepository.findById(createdId)).isPresent();
    }

    @Test
    @DisplayName("실무테스트 수정")
    void updateJobtest_success() {
        // given
        int memberId = 1;
        int jobtestId = 6;

        jobtestQuestionRepository.saveAll(List.of(
                JobtestQuestionEntity.builder().jobTestId(jobtestId).questionId(1).score(10).optionNumber(1).build()
        ));

        // 업데이트 DTO
        UpdateJobtestCommandDTO updateDTO = UpdateJobtestCommandDTO.builder()
                .title("수정된 테스트 제목")
                .updatedMemberId(memberId)
                .jobtestQuestions(List.of(
                        CreateJobtestQuestionCommandDTO.builder().questionId(1).score(10).build(),
                        CreateJobtestQuestionCommandDTO.builder().questionId(2).score(90).build()
                ))
                .build();

        // when
        UpdateJobtestCommandDTO updated = service.updateJobtest(jobtestId, updateDTO);

        // then
        JobtestEntity result = jobtestRepository.findById(jobtestId).orElseThrow();
        assertThat(result.getTitle()).isEqualTo(updated.getTitle());
    }

    @Test
    @DisplayName("실무테스트 삭제")
    void deleteJobtest_success() {
        // given
        int jobtestId = 6;

        // when
        Integer deletedId = service.deleteJobtest(jobtestId);

        // then
        assertThat(deletedId).isEqualTo(jobtestId);
        assertThat(jobtestRepository.findById(deletedId)).isEmpty();
    }
}