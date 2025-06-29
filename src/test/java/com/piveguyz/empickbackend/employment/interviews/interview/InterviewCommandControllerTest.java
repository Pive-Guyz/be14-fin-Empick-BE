package com.piveguyz.empickbackend.employment.interviews.interview;

import com.piveguyz.empickbackend.employment.interviews.interview.command.application.dto.InterviewCommandDTO;
import com.piveguyz.empickbackend.employment.interviews.interview.command.application.service.InterviewCommandService;
import com.piveguyz.empickbackend.employment.interviews.interview.command.domain.aggregate.InterviewEntity;
import com.piveguyz.empickbackend.employment.interviews.interview.command.domain.repository.InterviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class InterviewCommandControllerTest {

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private InterviewCommandService interviewCommandService;

    @Test
    @DisplayName("면접 생성 or 수정 테스트")
    void createOrUpdateInterviewTest() {
        // 이미 존재하면 삭제가 아닌 업데이트 (테스트 환경 고려)
        InterviewEntity existing = (InterviewEntity) interviewRepository.findByApplicationId(1).orElse(null);

        if (existing != null) {
            existing.setDatetime(LocalDateTime.now().plusDays(1));
            existing.setAddress("https://zoom.us/test");
            existing.setSheetId(1);
            interviewRepository.save(existing);
        } else {
            InterviewCommandDTO dto = new InterviewCommandDTO();
            dto.setApplicationId(1);
            dto.setSheetId(1);
            dto.setDatetime(LocalDateTime.now().plusDays(1));
            dto.setAddress("https://zoom.us/test");

            interviewCommandService.create(dto);
        }

        InterviewEntity interview = (InterviewEntity) interviewRepository.findByApplicationId(1)
                .orElseThrow(() -> new IllegalArgumentException("면접 없음"));

        assertThat(interview.getApplicationId()).isEqualTo(1);
        assertThat(interview.getAddress()).isEqualTo("https://zoom.us/test");
        System.out.println("면접 주소: " + interview.getAddress());
    }


    @Test
    @DisplayName("면접 삭제 테스트")
    void deleteInterviewTest() {
        InterviewEntity interview = new InterviewEntity();
        interview.setApplicationId(2);
        interview.setSheetId(1);
        interview.setDatetime(LocalDateTime.now().plusDays(3));
        interview.setAddress("https://to-be-deleted.com");
        interview.setScore(70.0);

        interviewRepository.save(interview);

        interviewCommandService.delete(interview.getId());

        boolean exists = interviewRepository.findById(interview.getId()).isPresent();

        assertThat(exists).isFalse();
        System.out.println("=== 테스트 결과 ===");
        System.out.println("삭제 여부: " + exists);
    }
}
