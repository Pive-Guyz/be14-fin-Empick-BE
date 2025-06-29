package com.piveguyz.empickbackend.employment.recruitmentProcess.command.application.dto;

import com.piveguyz.empickbackend.employment.recruitmentProcess.command.domain.enums.StepType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RecruitmentProcessCreateDTO {
	private StepType stepType;
	private int displayOrder;
}