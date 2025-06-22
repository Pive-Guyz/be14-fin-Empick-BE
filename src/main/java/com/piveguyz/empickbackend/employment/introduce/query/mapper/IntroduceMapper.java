package com.piveguyz.empickbackend.employment.introduce.query.mapper;

import com.piveguyz.empickbackend.employment.introduce.query.dto.IntroduceQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IntroduceMapper {
    List<IntroduceQueryDTO> findAllIntroduce();

    IntroduceQueryDTO findIntroduceById(Integer id);

    IntroduceQueryDTO findIntroduceByApplicationId(Integer applicationId);
}
