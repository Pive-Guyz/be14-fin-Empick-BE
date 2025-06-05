package com.piveguyz.empickbackend.employment.recruitmentRequest.query.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.piveguyz.empickbackend.employment.recruitmentRequest.query.dto.RecruitmentRequestQueryDTO;

@Mapper
public interface RecruitmentRequestQueryMapper {
	List<RecruitmentRequestQueryDTO> findAll();

	RecruitmentRequestQueryDTO findById(int id);
}
