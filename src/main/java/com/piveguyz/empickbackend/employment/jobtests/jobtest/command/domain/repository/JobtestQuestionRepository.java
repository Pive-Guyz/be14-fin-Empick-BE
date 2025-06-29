package com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.repository;

import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.aggregate.JobtestQuestionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobtestQuestionRepository extends JpaRepository<JobtestQuestionEntity, Integer> {

    boolean existsByJobTestIdAndQuestionId(int jobtestId, int questionId);

    @Query("SELECT MAX(q.optionNumber) FROM JobtestQuestionEntity q WHERE q.jobTestId = :jobTestId")
    Integer findMaxOptionNumberByJobTestId(@Param("jobTestId") int jobTestId);

    Optional<JobtestQuestionEntity> findByJobTestIdAndQuestionId(int jobTestId, int questionId);

    boolean existsByQuestionId(int id);

    boolean existsByJobTestId(int jobTestId);

    @Modifying
    @Transactional
    @Query("DELETE FROM JobtestQuestionEntity j WHERE j.jobTestId = :jobTestId")
    void deleteByJobTestId(int jobTestId);

    boolean findByJobTestId(int jobTestId);
}
