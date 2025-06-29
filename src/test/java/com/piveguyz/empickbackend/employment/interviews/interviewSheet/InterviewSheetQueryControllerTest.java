package com.piveguyz.empickbackend.employment.interviews.interviewSheet;

import com.piveguyz.empickbackend.employment.interviews.interviewSheet.query.dto.InterviewSheetQueryDTO;
import com.piveguyz.empickbackend.employment.interviews.interviewSheet.query.service.InterviewSheetQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class InterviewSheetQueryControllerTest {

    @Autowired
    private InterviewSheetQueryService service;

    @Test
    @DisplayName("✅ 전체 면접 평가표 조회")
    void findAllTest() {
        List<InterviewSheetQueryDTO> list = service.findAll();
        assertThat(list).isNotNull();
        assertThat(list.size()).isGreaterThan(0);
        System.out.println("📋 전체 평가표 개수: " + list.size());
    }

    @Test
    @DisplayName("✅ ID로 면접 평가표 조회")
    void findByIdTest() {
        InterviewSheetQueryDTO dto = service.findById(1); // 실제 존재하는 id로 바꿔줘
        assertThat(dto).isNotNull();
        System.out.println("📌 평가표 이름: " + dto.getName());
    }

    @Test
    @DisplayName("✅ 이름으로 면접 평가표 검색")
    void searchByNameTest() {
        List<InterviewSheetQueryDTO> list = service.searchByName("테스트"); // 일부 일치하는 이름으로
        assertThat(list).isNotNull();
        System.out.println("🔍 검색된 평가표 개수: " + list.size());
    }
}
