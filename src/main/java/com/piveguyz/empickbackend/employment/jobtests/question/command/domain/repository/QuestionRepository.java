package com.piveguyz.empickbackend.employment.jobtests.question.command.domain.repository;

import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.aggregate.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Integer> {

    boolean existsByContent(String content);
}
