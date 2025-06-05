package com.piveguyz.empickbackend.employment.mail.command.application.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.mail.command.application.dto.MailCommandDTO;
import com.piveguyz.empickbackend.employment.mail.command.application.mapper.MailCommandMapper;
import com.piveguyz.empickbackend.employment.mail.command.domain.aggregate.MailEntity;
import com.piveguyz.empickbackend.employment.mail.command.domain.repository.MailRepository;
import com.piveguyz.empickbackend.employment.mail.query.mapper.MailMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailCommandServiceImpl implements MailCommandService {
    private final MailRepository mailRepository;
    private final MailCommandMapper mailCommandMapper;

    @Override
    public MailCommandDTO createMail(MailCommandDTO mailCommandDTO) {
        String content = mailCommandDTO.getContent();
        if(content == null || content.isEmpty()){
            throw new BusinessException(ResponseCode.EMPLOYMENT_MAIL_NO_CONTENT);
        }
        String email = mailCommandDTO.getEmail();
        if(!email.contains("@")){
            throw new BusinessException(ResponseCode.EMPLOYMENT_MAIL_INADEQUATE_EMAIL);
        }
        MailEntity mailEntity = new MailEntity();
        mailEntity.setApplicantId(mailCommandDTO.getApplicantId());
        mailEntity.setEmail(mailCommandDTO.getEmail());
        mailEntity.setContent(mailCommandDTO.getContent());
        mailEntity.setSenderId(1);
        mailEntity.setSendedAt(LocalDateTime.now());
        MailEntity savedMailEntity = mailRepository.save(mailEntity);
        MailCommandDTO savedMailCommandDTO = mailCommandMapper.toDTO(savedMailEntity);
        return savedMailCommandDTO;
    }
}
