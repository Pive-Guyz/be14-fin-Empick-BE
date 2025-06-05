package com.piveguyz.empickbackend.employment.recruitmentTemplate.command.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.piveguyz.empickbackend.employment.recruitmentTemplate.command.domain.aggregate.RecruitmentTemplateItem;

public interface RecruitmentTemplateItemRepository extends JpaRepository<RecruitmentTemplateItem, Integer> {
}
