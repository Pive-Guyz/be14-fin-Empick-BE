package com.piveguyz.empickbackend.employment.interviews.interviewCriteria;

import com.piveguyz.empickbackend.employment.interviews.interviewCriteria.command.application.dto.InterviewCriteriaCommandDTO;
import com.piveguyz.empickbackend.employment.interviews.interviewCriteria.command.application.service.InterviewCriteriaCommandService;
import com.piveguyz.empickbackend.employment.interviews.interviewCriteria.command.domain.aggregate.InterviewCriteriaEntity;
import com.piveguyz.empickbackend.employment.interviews.interviewCriteria.command.domain.repository.InterviewCriteriaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class InterviewCriteriaCommandControllerTest {

    @Autowired
    private InterviewCriteriaCommandService criteriaService;

    @Autowired
    private InterviewCriteriaRepository criteriaRepository;

    @Test
    @DisplayName("면접 평가 기준 생성 or 수정 테스트")
    void createOrUpdateCriteriaTest() {
        Integer sheetId = 1;
        String title = "유니크 제목";
        String content = "커뮤니케이션_" + System.currentTimeMillis(); // ✅ 테스트 전용 content

        // ✅ content 값을 일관되게 사용해야 함
        Optional<InterviewCriteriaEntity> existing = criteriaRepository.findByContentAndIsDeleted(content, "N");

        if (existing.isPresent()) {
            InterviewCriteriaEntity entity = existing.get();
            entity.setWeight(22.0);
            entity.setUpdatedAt(LocalDateTime.now());
            criteriaRepository.save(entity);

            assertThat(entity.getWeight()).isEqualTo(22.0);
            System.out.println("✅ 기존 기준 수정됨: " + entity.getTitle());
            return;
        }

        InterviewCriteriaCommandDTO dto = new InterviewCriteriaCommandDTO();
        dto.setSheetId(sheetId);
        dto.setContent(content);
        dto.setTitle(title);
        dto.setWeight(0.8); // ✅ 0~1 사이만 유효
        dto.setIsDeleted("N");
        dto.setMemberId(1);
        dto.setUpdatedAt(LocalDateTime.now());

        criteriaService.createCriteria(dto);

        InterviewCriteriaEntity saved = criteriaRepository.findBySheetIdAndTitle(sheetId, title)
                .orElseThrow(() -> new IllegalArgumentException("기준 없음"));

        assertThat(saved.getContent()).isEqualTo(content); // ✅ 일치 확인
        assertThat(saved.getWeight()).isEqualTo(0.8);
        System.out.println("✅ 기준 생성됨: " + saved.getTitle());
    }

    @Test
    @DisplayName("면접 평가 기준 삭제 테스트")
    void deleteCriteriaTest() {
        // Given: 기준 생성
        InterviewCriteriaEntity entity = new InterviewCriteriaEntity();
        entity.setSheetId(1);
        entity.setTitle("삭제 테스트");
        entity.setContent("삭제 테스트용 기준_" + System.currentTimeMillis()); // 중복 방지
        entity.setWeight(0.8);
        entity.setIsDeleted("N");
        entity.setMemberId(1);
        entity.setUpdatedAt(LocalDateTime.now());

        criteriaRepository.save(entity);
        Integer id = entity.getId();

        // When: 삭제 서비스 호출
        criteriaService.deleteCriteria(id);

        // Then: 삭제 여부 확인 (Soft Delete → isDeleted = 'Y')
        InterviewCriteriaEntity deleted = criteriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("삭제된 기준 없음"));

        assertThat(deleted.getIsDeleted()).isEqualTo("Y");
        System.out.println("✅ 삭제된 기준 ID: " + id + ", isDeleted = " + deleted.getIsDeleted());
    }
}