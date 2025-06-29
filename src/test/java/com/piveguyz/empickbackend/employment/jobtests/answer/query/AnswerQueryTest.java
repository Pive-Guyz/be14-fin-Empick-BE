package com.piveguyz.empickbackend.employment.jobtests.answer.query;

import com.piveguyz.empickbackend.employment.jobtests.answer.query.dto.AnswerQueryDTO;
import com.piveguyz.empickbackend.employment.jobtests.answer.query.service.AnswerQueryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AnswerQueryTest {
    @Autowired
    AnswerQueryServiceImpl answerQueryService;

    @Test
    @DisplayName("실무테스트 ID로 답안 목록 조회")
    void findAnswerDtos() {
        // given
        int appJobtestId = 1;

        // when
        List<AnswerQueryDTO> dtos = answerQueryService.findAnswerWithApplicationJobtestId(appJobtestId);

        // then
        assertThat(dtos).isNotEmpty();
        assertThat(dtos.get(0).getApplicationJobTestId()).isEqualTo(appJobtestId);
    }
}