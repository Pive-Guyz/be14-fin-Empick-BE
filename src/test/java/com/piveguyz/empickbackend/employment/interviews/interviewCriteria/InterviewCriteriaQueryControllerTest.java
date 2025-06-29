package com.piveguyz.empickbackend.employment.interviews.interviewCriteria;

import com.piveguyz.empickbackend.employment.interviews.interviewCriteria.query.dto.InterviewCriteriaQueryDTO;
import com.piveguyz.empickbackend.employment.interviews.interviewCriteria.query.service.InterviewCriteriaQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class InterviewCriteriaQueryControllerTest {

    @Autowired
    private InterviewCriteriaQueryService service;

    @Test
    @DisplayName("✅ 전체 평가 기준 조회")
    void findAllTest() {
        List<InterviewCriteriaQueryDTO> list = service.findAll();
        assertThat(list).isNotNull();
        assertThat(list.size()).isGreaterThan(0);
        System.out.println("📌 전체 기준 수: " + list.size());
    }

    @Test
    @DisplayName("✅ ID로 평가 기준 조회")
    void findByIdTest() {
        InterviewCriteriaQueryDTO dto = service.findById(1);  // 실제 존재하는 ID로 수정
        assertThat(dto).isNotNull();
        System.out.println("📌 조회된 기준: " + dto.getTitle());
    }

    @Test
    @DisplayName("✅ 제목으로 평가 기준 검색")
    void searchByTitleTest() {
        List<InterviewCriteriaQueryDTO> list = service.searchByTitle("협업");
        assertThat(list).isNotNull();
        System.out.println("📌 '협업' 제목 포함 기준 수: " + list.size());
    }

    @Test
    @DisplayName("✅ 평가표 ID로 기준 조회")
    void findBySheetIdTest() {
        List<InterviewCriteriaQueryDTO> list = service.findBySheetId(1);  // 실제 존재하는 sheet_id
        assertThat(list).isNotNull();
        System.out.println("📌 해당 평가표의 기준 수: " + list.size());
    }
}