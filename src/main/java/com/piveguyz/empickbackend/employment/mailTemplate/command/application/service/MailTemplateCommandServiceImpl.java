package com.piveguyz.empickbackend.employment.mailTemplate.command.application.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.mailTemplate.command.application.dto.MailTemplateCommandDTO;
import com.piveguyz.empickbackend.employment.mailTemplate.command.application.mapper.MailTemplateCommandMapper;
import com.piveguyz.empickbackend.employment.mailTemplate.command.domain.aggregate.MailTemplateEntity;
import com.piveguyz.empickbackend.employment.mailTemplate.command.domain.repository.MailTemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailTemplateCommandServiceImpl implements MailTemplateCommandService {
    private final MailTemplateRepository mailTemplateRepository;
    private final MailTemplateCommandMapper mailTemplateCommandMapper;

    @Override
    public MailTemplateCommandDTO createTemplate(MailTemplateCommandDTO mailTemplateCommandDTO) {
        String title = mailTemplateCommandDTO.getTitle();
        String content = mailTemplateCommandDTO.getContent();
        if(title == null){
            throw new BusinessException(ResponseCode.EMPLOYMENT_MAIL_TEMPLATE_NO_TITLE);
        }
        if(content == null){
            throw new BusinessException(ResponseCode.EMPLOYMENT_MAIL_TEMPLATE_NO_CONTENT);
        }
        if(mailTemplateRepository.existsByTitle(title)){
            throw new BusinessException(ResponseCode.EMPLOYMENT_MAIL_TEMPLATE_DUPLICATE_TITLE);
        }

        MailTemplateEntity mailTemplateEntity = new MailTemplateEntity();
        mailTemplateEntity.setTitle(title);
        mailTemplateEntity.setContent(content);
        mailTemplateEntity.setIsDeleted("N");
        mailTemplateEntity.setMemberId(1);
        mailTemplateEntity.setUpdatedAt(LocalDateTime.now());
        MailTemplateEntity createdMailTemplateEntity = mailTemplateRepository.save(mailTemplateEntity);

        MailTemplateCommandDTO createdMailTemplateCommandDTO = mailTemplateCommandMapper.toDTO(createdMailTemplateEntity);
        return createdMailTemplateCommandDTO;
    }

    @Override
    public MailTemplateCommandDTO updateTemplate(Integer id, MailTemplateCommandDTO mailTemplateCommandDTO) {
        String title = mailTemplateCommandDTO.getTitle();
        String content = mailTemplateCommandDTO.getContent();
        if(title == null){
            throw new BusinessException(ResponseCode.EMPLOYMENT_MAIL_TEMPLATE_NO_TITLE);
        }
        if(content == null){
            throw new BusinessException(ResponseCode.EMPLOYMENT_MAIL_TEMPLATE_NO_CONTENT);
        }
        if (mailTemplateRepository.existsByTitleAndIdNot(title, id)) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_MAIL_TEMPLATE_DUPLICATE_TITLE);
        }
        MailTemplateEntity mailTemplateEntity = mailTemplateRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResponseCode.EMPLOYMENT_MAIL_TEMPLATE_NOT_FOUND));
        mailTemplateEntity.setTitle(title);
        mailTemplateEntity.setContent(content);
        mailTemplateEntity.setMemberId(mailTemplateCommandDTO.getMemberId());
        mailTemplateEntity.setUpdatedAt(LocalDateTime.now());
        mailTemplateEntity = mailTemplateRepository.save(mailTemplateEntity);
        mailTemplateCommandDTO = mailTemplateCommandMapper.toDTO(mailTemplateEntity);
        return mailTemplateCommandDTO;
    }

    @Override
    public MailTemplateCommandDTO deleteTemplate(Integer id) {
        MailTemplateEntity mailTemplateEntity = mailTemplateRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResponseCode.EMPLOYMENT_MAIL_TEMPLATE_NOT_FOUND));

        mailTemplateEntity.setIsDeleted("Y");
        MailTemplateEntity deletedMailTemplateEntity = mailTemplateRepository.save(mailTemplateEntity);
        MailTemplateCommandDTO deletedMailTemplateCommandDTO = mailTemplateCommandMapper.toDTO(deletedMailTemplateEntity);
        return deletedMailTemplateCommandDTO;
    }
}
