package com.piveguyz.empickbackend.employment.introduce.command.domain.repository;

import com.piveguyz.empickbackend.employment.introduce.command.domain.aggregate.IntroduceTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntroduceTemplateRepository extends JpaRepository<IntroduceTemplateEntity, Integer> {
    boolean existsByTitle(String title);
}
