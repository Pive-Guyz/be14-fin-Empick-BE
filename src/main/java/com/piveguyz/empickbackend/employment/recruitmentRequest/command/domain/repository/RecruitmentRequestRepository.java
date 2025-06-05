package com.piveguyz.empickbackend.employment.recruitmentRequest.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.piveguyz.empickbackend.employment.recruitmentRequest.command.domain.aggregate.RecruitmentRequest;

public interface RecruitmentRequestRepository extends JpaRepository<RecruitmentRequest, Integer> {
}
