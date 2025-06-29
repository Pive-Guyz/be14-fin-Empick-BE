package com.piveguyz.empickbackend.employment.interviews.interviewSheet;

import com.piveguyz.empickbackend.employment.interviews.interviewSheet.command.application.dto.InterviewSheetCommandDTO;
import com.piveguyz.empickbackend.employment.interviews.interviewSheet.command.application.service.InterviewSheetCommandService;
import com.piveguyz.empickbackend.employment.interviews.interviewSheet.command.domain.aggregate.InterviewSheetEntity;
import com.piveguyz.empickbackend.employment.interviews.interviewSheet.command.domain.repository.InterviewSheetRepository;
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
public class InterviewSheetCommandControllerTest {

    @Autowired
    private InterviewSheetRepository sheetRepository;

    @Autowired
    private InterviewSheetCommandService sheetService;

    @Test
    @DisplayName("면접 평가표 생성 또는 수정 테스트")
    void createOrUpdateSheetTest() {
        String sheetName = "유니크_평가표_" + System.currentTimeMillis();

        Optional<InterviewSheetEntity> existing = sheetRepository.findByName(sheetName);

        if (existing.isPresent()) {
            InterviewSheetEntity entity = existing.get();
            entity.setIsDeleted("N");
            entity.setUpdatedAt(LocalDateTime.now());
            sheetRepository.save(entity);

            assertThat(entity.getName()).isEqualTo(sheetName);
            System.out.println("✅ 기존 평가표 수정됨: " + entity.getName());
        } else {
            InterviewSheetCommandDTO dto = new InterviewSheetCommandDTO();
            dto.setName(sheetName);

            InterviewSheetCommandDTO created = sheetService.createSheet(dto);

            assertThat(created.getName()).isEqualTo(sheetName);
            System.out.println("✅ 새 평가표 생성됨: " + created.getName());
        }
    }

    @Test
    @DisplayName("면접 평가표 삭제 테스트")
    void deleteSheetTest() {
        InterviewSheetEntity entity = new InterviewSheetEntity();
        entity.setName("삭제 테스트 평가표_" + System.currentTimeMillis());
        entity.setIsDeleted("N");
        entity.setMemberId(1);
        entity.setUpdatedAt(LocalDateTime.now());

        sheetRepository.save(entity);

        Integer id = entity.getId();

        sheetService.deleteSheet(id);

        InterviewSheetEntity deleted = sheetRepository.findById(id).orElse(null);
        assertThat(deleted).isNotNull();
        assertThat(deleted.getIsDeleted()).isEqualTo("Y");
        System.out.println("✅ 평가표 삭제 처리됨(ID: " + id + ")");
    }
}
