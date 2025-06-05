package com.piveguyz.empickbackend.employment.recruitmentRequest.command.application.service;

import com.piveguyz.empickbackend.employment.recruitmentRequest.command.application.dto.RecruitmentRequestCommandDTO;

public interface RecruitmentRequestCommandService {
	void create(RecruitmentRequestCommandDTO dto);
}
