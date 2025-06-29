package com.piveguyz.empickbackend.employment.interviews.interviewer.command.domain.repository;


import com.piveguyz.empickbackend.employment.interviews.interviewer.command.domain.aggregate.InterviewerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterviewerRepository extends JpaRepository<InterviewerEntity, Integer> {

    boolean existsByInterviewIdAndMemberId(Integer interviewId, Integer memberId);

    Optional<InterviewerEntity> findByInterviewIdAndMemberId(Integer interviewId, Integer memberId);
}
