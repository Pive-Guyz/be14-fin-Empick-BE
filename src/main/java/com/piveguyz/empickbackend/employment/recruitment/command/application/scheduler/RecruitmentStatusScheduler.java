package com.piveguyz.empickbackend.employment.recruitment.command.application.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.piveguyz.empickbackend.employment.recruitment.command.domain.aggregate.Recruitment;
import com.piveguyz.empickbackend.employment.recruitment.command.domain.enums.RecruitmentStatus;
import com.piveguyz.empickbackend.employment.recruitment.command.domain.repository.RecruitmentRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RecruitmentStatusScheduler {

	private final RecruitmentRepository recruitmentRepository;

	@Scheduled(cron = "0 * * * * *") // 매분 0초에 실행
	@Transactional
	public void updateRecruitmentStatuses() {
		LocalDateTime now = LocalDateTime.now();

		// 시작일이 지났는데 아직 "대기" 상태인 공고 → "게시중"으로 변경
		List<Recruitment> toPublish = recruitmentRepository
			.findAllByStatusAndStartedAtBefore(RecruitmentStatus.WAITING, now);
		for (Recruitment recruitment : toPublish) {
			recruitment.setStatus(RecruitmentStatus.PUBLISHED);
		}

		// 마감일이 지난 공고 중 "게시중" 상태인 것 → "종료"로 변경
		List<Recruitment> toClose = recruitmentRepository
			.findAllByStatusAndEndedAtBefore(RecruitmentStatus.PUBLISHED, now);
		for (Recruitment recruitment : toClose) {
			recruitment.setStatus(RecruitmentStatus.CLOSED);
		}
	}
}
