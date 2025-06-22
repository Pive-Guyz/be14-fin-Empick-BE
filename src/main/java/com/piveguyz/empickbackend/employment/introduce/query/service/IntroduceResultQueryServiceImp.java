package com.piveguyz.empickbackend.employment.introduce.query.service;

import com.piveguyz.empickbackend.employment.introduce.query.dto.IntroduceResultQueryDTO;
import com.piveguyz.empickbackend.employment.introduce.query.mapper.IntroduceResultMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public
class IntroduceResultQueryServiceImp implements IntroduceResultQueryService {

    private final IntroduceResultMapper introduceResultMapper;

    @Override
    public List<IntroduceResultQueryDTO> findAllIntroduceResult() {
        return introduceResultMapper.findAllIntroduceResult();
    }

    @Override
    public IntroduceResultQueryDTO findIntroduceResultById(Integer id) {
        return introduceResultMapper.findById(id);
    }


}
