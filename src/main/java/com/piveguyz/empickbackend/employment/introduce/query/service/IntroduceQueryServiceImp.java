package com.piveguyz.empickbackend.employment.introduce.query.service;

import com.piveguyz.empickbackend.employment.introduce.query.dto.IntroduceQueryDTO;
import com.piveguyz.empickbackend.employment.introduce.query.mapper.IntroduceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IntroduceQueryServiceImp implements IntroduceQueryService {

    private final IntroduceMapper introduceMapper;

    @Override
    public List<IntroduceQueryDTO> findAllIntroduce() {
        return introduceMapper.findAllIntroduce();
    }

    @Override
    public IntroduceQueryDTO findIntroduceByApplicationId(Integer applicationId) {
        return introduceMapper.findIntroduceByApplicationId(applicationId);
    }

    @Override
    public IntroduceQueryDTO findIntroduceById(Integer id) {
        return introduceMapper.findIntroduceById(id);
    }



}
