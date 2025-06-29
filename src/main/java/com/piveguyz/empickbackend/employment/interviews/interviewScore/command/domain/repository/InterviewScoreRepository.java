package com.piveguyz.empickbackend.employment.interviews.interviewScore.command.domain.repository;

import com.piveguyz.empickbackend.employment.interviews.interviewScore.command.domain.aggregate.InterviewScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterviewScoreRepository extends JpaRepository<InterviewScoreEntity, Integer> {

    boolean existsByInterviewerIdAndCriteriaId(Integer interviewerId, Integer criteriaId);

    Optional<InterviewScoreEntity> findByInterviewerIdAndCriteriaId(Integer interviewerId, Integer criteriaId);
}
