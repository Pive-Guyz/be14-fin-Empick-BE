package com.piveguyz.empickbackend.employment.interviews.interview.command.domain.repository;

import com.piveguyz.empickbackend.employment.interviews.interview.command.domain.aggregate.InterviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterviewRepository extends JpaRepository<InterviewEntity, Integer> {
    boolean existsByApplicationId(Integer applicationId);

    Optional<InterviewEntity> findTopByOrderByIdDesc();

    Optional<Object> findByApplicationId(int i);
}
