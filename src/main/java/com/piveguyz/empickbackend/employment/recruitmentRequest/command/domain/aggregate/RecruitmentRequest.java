package com.piveguyz.empickbackend.employment.recruitmentRequest.command.domain.aggregate;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recruitment_request")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruitmentRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "headcount")
	private int headcount;

	@Column(name = "started_at")
	private LocalDateTime startedAt;

	@Column(name = "ended_at")
	private LocalDateTime endedAt;

	@Column(name = "qualification")
	private String qualification;

	@Column(name = "preference")
	private String preference;

	@Column(name = "responsibility")
	private String responsibility;

	@Column(name = "employment_type")
	private String employmentType;

	@Column(name = "work_location")
	private String workLocation;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "member_id")
	private int memberId;

	@Column(name = "department_id")
	private int departmentId;
}
