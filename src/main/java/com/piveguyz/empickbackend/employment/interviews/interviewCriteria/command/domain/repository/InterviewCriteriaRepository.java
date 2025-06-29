package com.piveguyz.empickbackend.employment.interviews.interviewCriteria.command.domain.repository;


import com.piveguyz.empickbackend.employment.interviews.interviewCriteria.command.domain.aggregate.InterviewCriteriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterviewCriteriaRepository extends JpaRepository<InterviewCriteriaEntity, Integer> {
    boolean existsByContent(String content);

    boolean existsByContentAndIdNot(String content, Integer id);

    boolean existsByContentAndIsDeleted(String content, String n);

    boolean existsByContentAndIdNotAndIsDeleted(String content, Integer id, String n);

    InterviewCriteriaEntity findByIdAndIsDeleted(Integer id, String isDeleted);

    boolean existsByIdAndIsDeleted(Integer id, String n);

    Optional<InterviewCriteriaEntity> findByContentAndIsDeleted(String 커뮤니케이션, String n);

    Optional<InterviewCriteriaEntity> findBySheetIdAndTitle(Integer sheetId, String title);
}
