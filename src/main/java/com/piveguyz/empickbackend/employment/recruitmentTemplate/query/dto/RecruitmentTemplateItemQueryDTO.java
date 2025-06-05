package com.piveguyz.empickbackend.employment.recruitmentTemplate.query.dto;

import lombok.Data;

@Data
public class RecruitmentTemplateItemQueryDTO {
	private int id;
	private String itemTitle;
	private String defaultContent;
	private int displayOrder;
}
