package com.piveguyz.empickbackend.employment.recruitmentTemplate.command.application.dto;

import java.util.List;

import lombok.Data;

@Data
public class RecruitmentTemplateCommandDTO {
	private int id;
	private String name;
	private int memberId;
	private List<RecruitmentTemplateItemCommandDTO> items;
}
