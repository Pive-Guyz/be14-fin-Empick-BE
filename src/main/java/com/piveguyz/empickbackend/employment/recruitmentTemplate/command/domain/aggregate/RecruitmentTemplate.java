package com.piveguyz.empickbackend.employment.recruitmentTemplate.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recruitment_template")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruitmentTemplate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "is_deleted")
	private String isDeleted;

	@Column(name = "member_id")
	private int memberId;

	@Builder.Default
	@OneToMany(mappedBy = "recruitmentTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("displayOrder ASC")
	private List<RecruitmentTemplateItem> items = new ArrayList<>();

	public void addItem(RecruitmentTemplateItem item) {
		items.add(item);
		item.setRecruitmentTemplate(this);
	}

	public void clearItems() {
		this.items.clear();
	}

	public void update(String name) {
		this.name = name;
	}

	public void delete() {
		this.isDeleted = "Y";
	}
}
