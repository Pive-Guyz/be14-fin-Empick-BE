package com.piveguyz.empickbackend.employment.jobtests.jobtest.query;

import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.application.dto.JobtestEntryRequestDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto.ApplicationJobtestAnswerPageDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto.ApplicationJobtestQueryDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.dto.ApplicationJobtestResponseDTO;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.mapper.ApplicationJobtestMapper;
import com.piveguyz.empickbackend.employment.jobtests.jobtest.query.service.ApplicationJobtestQueryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class ApplicationJobtestQueryTest {
    @Autowired
    private ApplicationJobtestQueryServiceImpl service;

    @Autowired
    private ApplicationJobtestMapper mapper;

    @Test
    @DisplayName("지원서별 실무테스트 목록 조회")
    void getAllApplicationJobTests_success() {
        // when
        List<ApplicationJobtestQueryDTO> results = service.getAllApplicationJobTests();

        // then
        assertThat(results).isNotNull();
        assertThat(results).isInstanceOf(List.class);
    }

    @Test
    @DisplayName("입장 코드 유효성 체크")
    void verifyEntryCode_success() {
        // given
        int jobtestId = 1;
        String validEntryCode = "ABC123";

        // 💡 테스트를 위한 insert 가 필요하거나 @Sql 로 세팅해야 합니다.
        JobtestEntryRequestDTO request = JobtestEntryRequestDTO.builder()
                .entryCode(validEntryCode)
                .build();

        // when
        int resultId = service.verifyEntryCode(jobtestId, request);

        // then
        assertThat(resultId).isPositive();
    }

    @Test
    @DisplayName("applicationId로 실무테스트 조회")
    void getApplicationJobtestByApplicationId_success() {
        // given
        int applicationId = 1;

        // when
        ApplicationJobtestResponseDTO result = service.getApplicationJobtestByApplicationId(applicationId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getApplicationId()).isEqualTo(applicationId);
    }

    @Test
    @DisplayName("applicationJobtestId로 실무테스트 답안 페이지 조회")
    void getApplicationJobtest_success() {
        // given
        int id = 1;

        // when
        ApplicationJobtestAnswerPageDTO dto = service.getApplicationJobtest(id);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto.getApplicationJobTestId()).isEqualTo(id);
    }
}