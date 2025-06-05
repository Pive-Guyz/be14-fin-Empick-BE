package com.piveguyz.empickbackend.employment.recruitmentTemplate.query.dto;

import java.util.List;

import lombok.Data;

@Data
public class RecruitmentTemplateQueryDTO {
	private int id;
	private String name;           // 템플릿 제목
	private String departmentName; // 부서명
	private String memberName;     // 작성자 이름
	private String createdAt;
	private List<RecruitmentTemplateItemQueryDTO> items;  // 항목
}
