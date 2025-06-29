package com.piveguyz.empickbackend.employment.interviews.interview;

import com.piveguyz.empickbackend.employment.interviews.interview.query.dto.InterviewQueryDTO;
import com.piveguyz.empickbackend.employment.interviews.interview.query.service.InterviewQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class InterviewQueryControllerTest {

    @Autowired
    private InterviewQueryService service;

    @Test
    @DisplayName("✅ 전체 면접 조회")
    void findAllTest() {
        List<InterviewQueryDTO> list = service.findAll();
        assertThat(list).isNotNull();
        assertThat(list.size()).isGreaterThan(0);
        System.out.println("📌 전체 면접 수: " + list.size());
    }

    @Test
    @DisplayName("✅ ID로 면접 조회")
    void findByIdTest() {
        InterviewQueryDTO dto = service.findById(1);  // DB에 존재하는 ID로 수정
        assertThat(dto).isNotNull();
        System.out.println("📌 조회된 면접: " + dto);
    }

    @Test
    @DisplayName("✅ 지원서 ID로 면접 조회")
    void findByApplicationIdTest() {
        InterviewQueryDTO dto = service.findByApplicationId(1);  // 실제 존재하는 application_id
        assertThat(dto).isNotNull();
        System.out.println("📌 지원서 ID로 조회된 면접: " + dto);
    }

    @Test
    @DisplayName("✅ 날짜로 면접 조회")
    void findByDateTest() {
        LocalDate date = LocalDate.of(2025, 7, 3);  // 실제 면접 날짜
        List<InterviewQueryDTO> list = service.findByDate(date);
        assertThat(list).isNotNull();
        System.out.println("📌 날짜로 조회된 면접 수: " + list.size());
    }

    @Test
    @DisplayName("✅ 면접 시간 가능 여부 확인")
    void checkAvailableTest() {
        LocalDateTime datetime = LocalDateTime.of(2025, 7, 4, 13, 0); // DB에 이미 존재하는 면접 시간이라면 false
        Boolean result = service.checkAvailable(datetime);
        assertThat(result).isNotNull();
        System.out.println("✅ 사용 가능한 시간인가? : " + result);
    }
}
