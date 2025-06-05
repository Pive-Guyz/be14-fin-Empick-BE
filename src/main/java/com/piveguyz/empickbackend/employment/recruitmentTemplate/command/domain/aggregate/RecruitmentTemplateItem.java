package com.piveguyz.empickbackend.employment.recruitmentTemplate.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recruitment_template_item")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruitmentTemplateItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "item_title")
	private String itemTitle;

	@Column(name = "default_content")
	private String defaultContent;

	@Column(name = "display_order")
	private int displayOrder;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recruitment_template_id")
	private RecruitmentTemplate recruitmentTemplate;

}
