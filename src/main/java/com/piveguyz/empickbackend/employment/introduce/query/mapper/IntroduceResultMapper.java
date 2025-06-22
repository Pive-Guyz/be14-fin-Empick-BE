package com.piveguyz.empickbackend.employment.introduce.query.mapper;

import com.piveguyz.empickbackend.employment.introduce.query.dto.IntroduceResultQueryDTO;

import java.util.List;

public interface IntroduceResultMapper {
    List<IntroduceResultQueryDTO> findAllIntroduceResult();

    IntroduceResultQueryDTO findById(Integer id);
}
