package com.piveguyz.empickbackend.employment.interviewSheetItem.command.domain.repository;

import com.piveguyz.empickbackend.employment.interviewSheet.command.domain.aggregate.InterviewSheetEntity;
import com.piveguyz.empickbackend.employment.interviewSheetItem.command.domain.aggregate.InterviewSheetItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface InterviewSheetItemRepository extends JpaRepository<InterviewSheetItemEntity, Integer> {
    boolean existsBySheetIdAndCriteriaId(Integer sheetId, Integer criteriaId);
}
