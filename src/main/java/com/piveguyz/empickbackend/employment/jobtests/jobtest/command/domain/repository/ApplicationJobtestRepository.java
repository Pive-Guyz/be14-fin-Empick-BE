package com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.repository;

import com.piveguyz.empickbackend.employment.jobtests.jobtest.command.domain.aggregate.ApplicationJobtestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationJobtestRepository extends JpaRepository<ApplicationJobtestEntity, Integer> {
    boolean existsByEntryCode(String entryCode);

    boolean existsByApplicationId(int applicationId);
    List<ApplicationJobtestEntity> findByJobTestId(int jobtestId);

    boolean existsByJobTestIdAndEntryCode(int jobTestId, String entryCode);

    ApplicationJobtestEntity findByEntryCode(String entryCode);

    boolean existsByJobTestId(int jobtestId);
}
