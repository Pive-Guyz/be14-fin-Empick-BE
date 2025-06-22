package com.piveguyz.empickbackend.employment.introduce.query.service;

import com.piveguyz.empickbackend.employment.introduce.query.dto.IntroduceResultQueryDTO;

import java.util.List;

public interface IntroduceResultQueryService {
    List<IntroduceResultQueryDTO> findAllIntroduceResult();

    IntroduceResultQueryDTO findIntroduceResultById(Integer id);
}
