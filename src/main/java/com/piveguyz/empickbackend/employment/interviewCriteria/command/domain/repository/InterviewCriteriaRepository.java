package com.piveguyz.empickbackend.employment.interviewCriteria.command.domain.repository;


import com.piveguyz.empickbackend.employment.interviewCriteria.command.domain.aggregate.InterviewCriteriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewCriteriaRepository extends JpaRepository<InterviewCriteriaEntity, Integer> {
    boolean existsByContent(String content);

    boolean existsByContentAndIdNot(String content, Integer id);

    boolean existsByContentAndIsDeleted(String content, String n);

    boolean existsByContentAndIdNotAndIsDeleted(String content, Integer id, String n);

    InterviewCriteriaEntity findByIdAndIsDeleted(Integer id, String isDeleted);

    boolean existsByIdAndIsDeleted(Integer id, String n);
}
