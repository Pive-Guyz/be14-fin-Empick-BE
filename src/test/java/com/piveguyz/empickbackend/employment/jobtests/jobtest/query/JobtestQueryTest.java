package com.piveguyz.empickbackend.employment.jobtests.jobtest.query;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto.JobtestExamQueryDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto.JobtestQueryDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto.JobtestQuestionListQueryDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.mapper.JobtestMapper;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.service.JobtestQueryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
@Transactional
class JobtestQueryTest {
    @Autowired
    private JobtestQueryServiceImpl service;

    @Autowired
    private JobtestMapper jobtestMapper;

    @Test
    @DisplayName("전체 실무테스트 문제 목록 조회")
    void getAllJobTests_success() {
        // when
        List<JobtestQuestionListQueryDTO> results = service.getAllJobTests();

        // then
        assertThat(results).isNotNull();
        assertThat(results).isInstanceOf(List.class);
    }

    @Test
    @DisplayName("특정 실무테스트를 수행할 지원서 조회")
    void findJobTestWithApplications_success() {
        // given
        int jobtestId = 1;

        // when
        JobtestQueryDTO dto = service.findJobTestWithApplications(jobtestId);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(jobtestId);
    }

    @Test
    @DisplayName("실무테스트 응시 정보 조회")
    void enterJobtestExam_success() {
        // given
        int jobtestId = 1;
        int applicationJobTestId = 1;

        // when
        JobtestExamQueryDTO result = service.enterJobtestExam(jobtestId, applicationJobTestId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getJobtestId()).isEqualTo(jobtestId);
        assertThat(result.getApplicationJobTestId()).isEqualTo(applicationJobTestId);
    }

}