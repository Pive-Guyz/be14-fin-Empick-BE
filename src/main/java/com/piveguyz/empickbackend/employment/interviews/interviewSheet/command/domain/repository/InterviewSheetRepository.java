package com.piveguyz.empickbackend.employment.interviews.interviewSheet.command.domain.repository;

import com.piveguyz.empickbackend.employment.interviews.interviewSheet.command.domain.aggregate.InterviewSheetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterviewSheetRepository extends JpaRepository<InterviewSheetEntity, Integer> {
    InterviewSheetEntity findByIdAndIsDeleted(Integer id, String n);

    boolean existsByName(String name);

    boolean existsByNameAndIdNotAndIsDeleted(String name, Integer id, String isDeleted);

    Optional<InterviewSheetEntity> findByName(String sheetName);
}
