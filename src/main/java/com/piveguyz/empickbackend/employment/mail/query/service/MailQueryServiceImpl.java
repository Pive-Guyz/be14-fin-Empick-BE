package com.piveguyz.empickbackend.employment.mail.query.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.mail.query.dto.MailQueryDTO;
import com.piveguyz.empickbackend.employment.mail.query.mapper.MailMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailQueryServiceImpl implements MailQueryService {
    private final MailMapper mailMapper;
    @Override
    public List<MailQueryDTO> findAll() {
        List<MailQueryDTO> mailQueryDTOList = mailMapper.findAll();
        if(mailQueryDTOList.isEmpty()){
            throw new BusinessException(ResponseCode.EMPLOYMENT_MAIL_NOT_FOUND);
        }
        return mailQueryDTOList;
    }

    @Override
    public MailQueryDTO findById(Integer id) {
        MailQueryDTO mailQueryDTO = mailMapper.findById(id);
        if(mailQueryDTO == null){
            throw new BusinessException(ResponseCode.EMPLOYMENT_MAIL_NOT_FOUND);
        }
        return mailQueryDTO;
    }

    @Override
    public List<MailQueryDTO> findByEmail(String email) {
        List<MailQueryDTO> mailQueryDTOList = mailMapper.findByEmail(email);
        if(!email.contains("@")){
            throw new BusinessException(ResponseCode.EMPLOYMENT_MAIL_INADEQUATE_EMAIL);
        }
        if(mailQueryDTOList.isEmpty()){
            throw new BusinessException(ResponseCode.EMPLOYMENT_MAIL_NOT_FOUND);
        }
        return mailQueryDTOList;
    }
}
