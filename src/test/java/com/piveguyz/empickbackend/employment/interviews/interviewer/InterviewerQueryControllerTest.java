package com.piveguyz.empickbackend.employment.interviews.interviewer;

import com.piveguyz.empickbackend.employment.interviews.interviewer.query.dto.InterviewerQueryDTO;
import com.piveguyz.empickbackend.employment.interviews.interviewer.query.service.InterviewerQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class InterviewerQueryControllerTest {

    @Autowired
    private InterviewerQueryService service;

    @Test
    @DisplayName("✅ 전체 면접 담당자 조회")
    void findAllTest() {
        List<InterviewerQueryDTO> list = service.findAll();
        assertThat(list).isNotNull();
        assertThat(list.size()).isGreaterThan(0);
        System.out.println("📌 전체 담당자 수: " + list.size());
    }

    @Test
    @DisplayName("✅ 면접 담당자 ID로 조회")
    void findByIdTest() {
        InterviewerQueryDTO dto = service.findById(1);  // 실제 존재하는 ID로 변경 필요
        assertThat(dto).isNotNull();
        System.out.println("📌 조회된 담당자 ID: " + dto.getId());
    }

    @Test
    @DisplayName("✅ 면접 ID로 담당자 리스트 조회")
    void findByInterviewIdTest() {
        List<InterviewerQueryDTO> list = service.findByInterviewId(1);  // 실제 존재하는 면접 ID로 변경
        assertThat(list).isNotNull();
        System.out.println("📌 해당 면접의 담당자 수: " + list.size());
    }

    @Test
    @DisplayName("✅ 면접ID + 멤버ID로 단일 담당자 조회")
    void findByInterviewMemberIdTest() {
        InterviewerQueryDTO dto = service.findByInterviewMemberId(1, 5);  // 실제 존재하는 interviewId & memberId 필요
        assertThat(dto).isNotNull();
        System.out.println("📌 interviewId: " + dto.getInterviewId() + ", memberId: " + dto.getMemberId());
    }
}