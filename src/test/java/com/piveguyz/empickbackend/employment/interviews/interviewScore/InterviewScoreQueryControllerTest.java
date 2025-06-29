package com.piveguyz.empickbackend.employment.interviews.interviewScore;

import com.piveguyz.empickbackend.employment.interviews.interviewScore.query.dto.InterviewScoreQueryDTO;
import com.piveguyz.empickbackend.employment.interviews.interviewScore.query.service.InterviewScoreQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class InterviewScoreQueryControllerTest {

    @Autowired
    private InterviewScoreQueryService service;

    @Test
    @DisplayName("✅ 전체 면접 평가 점수 조회")
    void findAllTest() {
        List<InterviewScoreQueryDTO> list = service.findAll();
        assertThat(list).isNotNull();
        assertThat(list.size()).isGreaterThan(0);
        System.out.println("📊 총 점수 개수: " + list.size());
    }

    @Test
    @DisplayName("✅ ID로 평가 점수 조회")
    void findByIdTest() {
        InterviewScoreQueryDTO dto = service.findById(1); // 실제 존재하는 점수 ID로 바꿔야 함
        assertThat(dto).isNotNull();
        System.out.println("📌 조회된 점수 ID: " + dto.getId());
    }

    @Test
    @DisplayName("✅ 면접 담당자 ID로 평가 점수 조회")
    void findByInterviewerIdTest() {
        List<InterviewScoreQueryDTO> list = service.findByInterviewerId(1); // 실제 interviewerId로 바꿔야 함
        assertThat(list).isNotNull();
        System.out.println("👨‍🏫 평가한 점수 개수: " + list.size());
    }
}