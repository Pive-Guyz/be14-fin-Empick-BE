package com.piveguyz.empickbackend.employment.recruitmentRequest.command.application.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.piveguyz.empickbackend.employment.recruitmentRequest.command.application.dto.RecruitmentRequestCommandDTO;
import com.piveguyz.empickbackend.employment.recruitmentRequest.command.domain.aggregate.RecruitmentRequest;
import com.piveguyz.empickbackend.employment.recruitmentRequest.command.domain.repository.RecruitmentRequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentRequestCommandServiceImpl implements RecruitmentRequestCommandService {

	private final RecruitmentRequestRepository recruitmentRequestRepository;

	@Override
	public void create(RecruitmentRequestCommandDTO dto) {
		RecruitmentRequest request = RecruitmentRequest.builder()
			.headcount(dto.getHeadcount())
			.startedAt(dto.getStartedAt())
			.endedAt(dto.getEndedAt())
			.qualification(dto.getQualification())
			.preference(dto.getPreference())
			.responsibility(dto.getResponsibility())
			.employmentType(dto.getEmploymentType())
			.workLocation(dto.getWorkLocation())
			.memberId(dto.getMemberId())
			.departmentId(dto.getDepartmentId())
			.createdAt(LocalDateTime.now())
			.build();

		recruitmentRequestRepository.save(request);
	}
}
