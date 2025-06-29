package com.piveguyz.empickbackend.employment.interviews.interviewer;

import com.piveguyz.empickbackend.employment.interviews.interviewer.command.application.dto.InterviewerCommandDTO;
import com.piveguyz.empickbackend.employment.interviews.interviewer.command.application.service.InterviewerCommandService;
import com.piveguyz.empickbackend.employment.interviews.interviewer.command.domain.aggregate.InterviewerEntity;
import com.piveguyz.empickbackend.employment.interviews.interviewer.command.domain.repository.InterviewerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class InterviewerCommandControllerTest {

    @Autowired
    private InterviewerCommandService interviewerService;

    @Autowired
    private InterviewerRepository interviewerRepository;

    @Test
    @DisplayName("면접 담당자 등록 or 수정 테스트")
    void createOrUpdateInterviewerTest() {
        Integer interviewId = 1;
        Integer memberId = 1;

        Optional<InterviewerEntity> existing = interviewerRepository.findByInterviewIdAndMemberId(interviewId, memberId);

        if (existing.isPresent()) {
            InterviewerEntity entity = existing.get();
            entity.setReview("업데이트된 총평");
            interviewerRepository.save(entity);

            assertThat(entity.getReview()).isEqualTo("업데이트된 총평");
            System.out.println("✅ 기존 면접관 총평 수정됨: " + entity.getReview());
        } else {
            InterviewerCommandDTO dto = new InterviewerCommandDTO();
            dto.setInterviewId(interviewId);
            dto.setMemberId(memberId);

            InterviewerCommandDTO result = interviewerService.createInterviewer(dto);
            assertThat(result.getInterviewId()).isEqualTo(interviewId);
            System.out.println("✅ 면접관 새로 등록됨: " + result.getId());
        }
    }

    @Test
    @DisplayName("면접 담당자 총평 수정 테스트")
    void updateInterviewerReviewTest() {
        InterviewerEntity entity = new InterviewerEntity();
        entity.setInterviewId(1);
        entity.setMemberId(2);
        interviewerRepository.save(entity);

        Integer id = entity.getId();
        String newReview = "총평 입력 테스트";

        InterviewerCommandDTO updated = interviewerService.updateInterviewerReview(id, newReview);

        assertThat(updated.getReview()).isEqualTo(newReview);
        System.out.println("✅ 총평 수정됨: " + updated.getReview());
    }

    @Test
    @DisplayName("면접 담당자 삭제 테스트")
    void deleteInterviewerTest() {
        InterviewerEntity entity = new InterviewerEntity();
        entity.setInterviewId(1);
        entity.setMemberId(3);
        interviewerRepository.save(entity);

        Integer id = entity.getId();
        interviewerService.deleteInterviewer(id);

        boolean exists = interviewerRepository.findById(id).isPresent();
        assertThat(exists).isFalse();
        System.out.println("✅ 삭제됨: ID " + id);
    }
}
