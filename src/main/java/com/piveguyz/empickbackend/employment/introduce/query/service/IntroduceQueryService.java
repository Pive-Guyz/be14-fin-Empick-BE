package com.piveguyz.empickbackend.employment.introduce.query.service;

import com.piveguyz.empickbackend.employment.introduce.query.dto.IntroduceQueryDTO;

import java.util.List;

public interface IntroduceQueryService {
    List<IntroduceQueryDTO> findAllIntroduce();

    IntroduceQueryDTO findIntroduceByApplicationId(Integer applicationId);

    IntroduceQueryDTO findIntroduceById(Integer id);
}
