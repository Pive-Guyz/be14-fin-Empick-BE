package com.piveguyz.empickbackend.employment.jobtests.jobtest.command;

import com.piveguyz.empickbackend.employment.applicant.command.domain.aggregate.ApplicationEntity;
import com.piveguyz.empickbackend.employment.applicant.command.domain.repository.ApplicationRepository;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.CreateApplicationJobtestCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.UpdateApplicationJobtestCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.service.ApplicationJobtestCommandServiceImpl;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.aggregate.ApplicationJobtestEntity;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.aggregate.JobtestEntity;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.aggregate.enums.JobtestStatus;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.repository.ApplicationJobtestRepository;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.repository.JobtestRepository;
import com.piveguyz.empickbackend.orgstructure.member.command.domain.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ApplicationJobtestCommandTest {
    @Autowired
    private ApplicationJobtestCommandServiceImpl service;

    @Autowired
    private ApplicationJobtestRepository applicationJobtestRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobtestRepository jobtestRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("실무테스트 지원서에 할당")
    void createApplicationJobtest_success() {
        // given
        int jobtestId = 1;
        int applicationId = 5;

        CreateApplicationJobtestCommandDTO dto = CreateApplicationJobtestCommandDTO.builder()
                .applicationId(applicationId)
                .jobtestId(jobtestId)
                .build();

        // when
        var result = service.createApplicaionJobtest(dto);

        // then
        assertThat(result.getApplicationId()).isEqualTo(applicationId);
        assertThat(result.getJobtestId()).isEqualTo(jobtestId);
    }

    @Test
    @DisplayName("지원서에서 실무테스트 할당 취소")
    void deleteApplicationJobtest_success() {
        // given
        int id = 4;

        // when
        int deletedId = service.deleteApplicationJobtest(id);

        // then
        assertThat(applicationJobtestRepository.findById(deletedId)).isEmpty();
    }

    @Test
    @DisplayName("지원서별 실무테스트 채점 상태 및 점수 수정")
    void updateGradingStatusAndScore_success() {
        // given
        int applicationJobTestId = 1;

        double score = 85.0;

        // when
        service.updateGradingStatusAndScore(applicationJobTestId, score);

        // then
        ApplicationJobtestEntity updated = applicationJobtestRepository.findById(applicationJobTestId).orElseThrow();
        assertThat(updated.getGradingTotalScore()).isEqualTo(score);
        assertThat(updated.getGradingStatus()).isEqualTo(JobtestStatus.COMPLETED);
    }

    @Test
    @DisplayName("실무테스트 답안 제출")
    void finishExam_success() {
        // given
        int applicationJobTestId = 1;

        // when
        service.finishExam(applicationJobTestId);

        // then
        ApplicationJobtestEntity updated = applicationJobtestRepository.findById(applicationJobTestId).orElseThrow();
        assertThat(updated.getSubmittedAt()).isNotNull();
        assertThat(updated.getSubmittedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }
}