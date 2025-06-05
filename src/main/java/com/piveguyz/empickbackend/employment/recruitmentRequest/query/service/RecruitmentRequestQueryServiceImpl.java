package com.piveguyz.empickbackend.employment.recruitmentRequest.query.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.recruitmentRequest.query.dto.RecruitmentRequestQueryDTO;
import com.piveguyz.empickbackend.employment.recruitmentRequest.query.mapper.RecruitmentRequestQueryMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentRequestQueryServiceImpl implements RecruitmentRequestQueryService {
	private final RecruitmentRequestQueryMapper recruitmentRequestQueryMapper;

	@Override
	public List<RecruitmentRequestQueryDTO> getAll() {
		return recruitmentRequestQueryMapper.findAll();
	}

	@Override
	public RecruitmentRequestQueryDTO getById(int id) {
		RecruitmentRequestQueryDTO result = recruitmentRequestQueryMapper.findById(id);
		if (result == null) {
			throw new BusinessException(ResponseCode.EMPLOYMENT_REQUEST_NOT_FOUND);
		}
		return result;
	}
}
