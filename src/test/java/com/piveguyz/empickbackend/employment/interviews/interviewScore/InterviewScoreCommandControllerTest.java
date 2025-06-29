package com.piveguyz.empickbackend.employment.interviews.interviewScore;

import com.piveguyz.empickbackend.employment.interviews.interviewScore.command.application.dto.InterviewScoreCommandDTO;
import com.piveguyz.empickbackend.employment.interviews.interviewScore.command.application.service.InterviewScoreCommandService;
import com.piveguyz.empickbackend.employment.interviews.interviewScore.command.domain.aggregate.InterviewScoreEntity;
import com.piveguyz.empickbackend.employment.interviews.interviewScore.command.domain.repository.InterviewScoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class InterviewScoreCommandControllerTest {

    @Autowired
    private InterviewScoreRepository scoreRepository;

    @Autowired
    private InterviewScoreCommandService scoreService;

    @Test
    @DisplayName("면접 평가 점수 생성 또는 수정 테스트")
    void createOrUpdateInterviewScoreTest() {
        Integer interviewerId = 1;
        Integer criteriaId = 1;

        Optional<InterviewScoreEntity> existing = scoreRepository.findByInterviewerIdAndCriteriaId(interviewerId, criteriaId);

        if (existing.isPresent()) {
            InterviewScoreEntity entity = existing.get();
            entity.setScore(90);
            entity.setReview("수정된 리뷰");
            scoreRepository.save(entity);

            assertThat(entity.getScore()).isEqualTo(90);
            System.out.println("✅ 기존 점수 수정 완료");
        } else {
            InterviewScoreCommandDTO dto = new InterviewScoreCommandDTO();
            dto.setInterviewerId(interviewerId);
            dto.setCriteriaId(criteriaId);
            dto.setScore(85);
            dto.setReview("새로운 평가 등록");

            InterviewScoreCommandDTO saved = scoreService.create(dto);

            assertThat(saved.getScore()).isEqualTo(85);
            assertThat(saved.getReview()).contains("평가");
            System.out.println("✅ 새 점수 등록 완료");
        }
    }

    @Test
    @DisplayName("면접 평가 점수 삭제 테스트")
    void deleteInterviewScoreTest() {
        InterviewScoreEntity entity = new InterviewScoreEntity();
        entity.setInterviewerId(2); // 존재하는 interviewerId로 설정
        entity.setCriteriaId(2);    // 존재하는 criteriaId로 설정
        entity.setScore(75);
        entity.setReview("삭제 테스트");

        scoreRepository.save(entity);
        Integer id = entity.getId();

        scoreService.delete(id);

        Optional<InterviewScoreEntity> deleted = scoreRepository.findById(id);
        assertThat(deleted).isEmpty();
        System.out.println("✅ 삭제된 점수 ID: " + id);
    }
}
