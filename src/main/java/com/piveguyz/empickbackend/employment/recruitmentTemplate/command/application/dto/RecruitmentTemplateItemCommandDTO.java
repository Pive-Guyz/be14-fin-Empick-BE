package com.piveguyz.empickbackend.employment.recruitmentTemplate.command.application.dto;

import lombok.Data;

@Data
public class RecruitmentTemplateItemCommandDTO {
	private String itemTitle;
	private String defaultContent;
	private int displayOrder;
}
