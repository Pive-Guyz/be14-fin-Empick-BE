package com.piveguyz.empickbackend.employment.mail.command.application.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.mail.command.application.dto.MailCommandDTO;
import com.piveguyz.empickbackend.employment.mail.command.application.mapper.MailCommandMapper;
import com.piveguyz.empickbackend.employment.mail.command.domain.aggregate.MailEntity;
import com.piveguyz.empickbackend.employment.mail.command.domain.repository.MailRepository;
import com.piveguyz.empickbackend.employment.mail.query.dto.InterviewMailQueryDTO;
import com.piveguyz.empickbackend.employment.mail.query.dto.JobTestMailQueryDTO;
import com.piveguyz.empickbackend.employment.mail.query.mapper.MailMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailCommandServiceImpl implements MailCommandService {
    private final MailRepository mailRepository;
    private final MailCommandMapper mailCommandMapper;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final MailMapper mailMapper;

    @Override
    public MailCommandDTO createMail(MailCommandDTO dto) {
        String title = dto.getTitle();
        if (title == null || title.isEmpty()) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_MAIL_NO_TITLE);
        }
        String content = dto.getContent();
        if (content == null || content.isEmpty()) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_MAIL_NO_CONTENT);
        }
        String email = dto.getEmail();
        if (!email.contains("@")) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_MAIL_INADEQUATE_EMAIL);
        }
        MailEntity entity = new MailEntity();
        entity.setApplicantId(dto.getApplicantId());
        entity.setEmail(email);
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setSenderId(dto.getSenderId());
        entity.setSendedAt(LocalDateTime.now());
        MailEntity savedEntity = mailRepository.save(entity);
        return mailCommandMapper.toDTO(savedEntity);
    }

    @Async
    public MailCommandDTO sendSimpleMail(MailCommandDTO dto) {
        String email = dto.getEmail();
        if(!email.contains("@")) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_MAIL_INADEQUATE_EMAIL);
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.getEmail());
        message.setFrom("noreply@example.com");
        message.setSubject(dto.getTitle());
        message.setText(dto.getContent());
        javaMailSender.send(message);
        return dto;
    }

    @Override
    public MailCommandDTO sendHTMLMail(MailCommandDTO dto) {
        String email = dto.getEmail();
        if(!email.contains("@")) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_MAIL_INADEQUATE_EMAIL);
        }
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//            mimeMessageHelper.setFrom("noreply@example.com");
//            mimeMessageHelper.setTo(dto.getEmail());
//            mimeMessageHelper.setSubject(dto.getTitle());
//            mimeMessageHelper.setText(dto.getContent(), true);

            Context context = new Context();
            context.setVariable("subject", dto.getTitle());
            context.setVariable("message", dto.getContent());
            String base64Image = getBase64EncodedImage("static/images/empick-logo.png");
            context.setVariable("logoImage", base64Image);
            String htmlContent = templateEngine.process("email-template", context);

            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(dto.getTitle());
            mimeMessageHelper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);

            MailCommandDTO sendedDTO = new MailCommandDTO();
            sendedDTO.setEmail(email);
            sendedDTO.setTitle(dto.getTitle());
            sendedDTO.setContent(htmlContent);
            sendedDTO.setSenderId(dto.getSenderId());
            sendedDTO.setSendedAt(LocalDateTime.now());
            return sendedDTO;
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MailCommandDTO sendJobtestMail(Integer applicationId, Integer senderId) {
        JobTestMailQueryDTO dto = mailMapper.findForJobTestMail(applicationId);
        Integer recruitmentId = dto.getRecruitmentId();
        String recruitmentTitle = dto.getRecruitmentTitle();
        Integer applicantId = dto.getApplicantId();
        String applicantName = dto.getApplicantName();
        String email = dto.getEmail();
        Integer applicationJobTestId = dto.getApplicationJobTestId();
        String entryCode = dto.getEntryCode();
        Integer jobTestId = dto.getJobTestId();
        String jobTestTitle = dto.getJobTestTitle();
        Integer testTime = dto.getTestTime();
        LocalDateTime startedAt = dto.getStartedAt();
        LocalDateTime endedAt = dto.getEndedAt();
        Integer problemCount = dto.getProblemCount();
        if(applicantId == null) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_APPLICANT_NOT_FOUND);
        }
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            Context context = new Context();
            context.setVariable("recruitmentTitle", recruitmentTitle);
            context.setVariable("applicantName", applicantName);
            context.setVariable("entryCode", entryCode);
            context.setVariable("jobtestId", jobTestId);
            context.setVariable("jobTestTitle", jobTestTitle);
            context.setVariable("testTime", testTime);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 (E) a h시 mm분")
                    .withLocale(Locale.KOREAN);
            String startTime = startedAt.format(formatter);
            String endTime = endedAt.format(formatter);
            context.setVariable("startedAt", startTime);
            context.setVariable("endedAt", endTime);
            context.setVariable("problemCount", problemCount);

            String base64Image = getBase64EncodedImage("static/images/empick-logo.png");
            context.setVariable("logoImage", base64Image);

            String htmlContent = templateEngine.process("jobtest-mail-template", context);
            String title = "실무 테스트 일정 안내 메일";

            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(title);
            mimeMessageHelper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);

            MailCommandDTO sendedDTO = new MailCommandDTO();
            sendedDTO.setApplicantId(applicantId);
            sendedDTO.setEmail(email);
            sendedDTO.setTitle(title);
            String content = "[Empick] 실무 테스트 안내드립니다\n" +
                    applicantName + "님, 안녕하세요.\n" +
                    "\n" +
                    "\n" +
                    "지원해주신 " + recruitmentTitle + " 포지션에 대한 서류 전형에 합격하셨음을 알려드립니다. 축하드립니다!\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "다음 단계인 실무 테스트가 아래와 같이 진행될 예정입니다.\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "시작 시간: " + startedAt + "\n" +
                    "종료 시간: " + endedAt + "\n" +
                    "문항 수: " + problemCount + "\n" +
                    "입장 코드: " + entryCode + "\n" +
                    "https://www.empick.shop/employment/jobtest/exam/" + jobTestId + "/enter" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "테스트 관련 자세한 정보는 아래 버튼을 통해 확인해주세요.\n" +
                    "\n" +
                    "\n" +
                    "테스트 상세 안내 보기\n" +
                    "\n" +
                    "\n" +
                    "본 메일은 발신 전용입니다. 문의사항은 채용 담당자에게 연락해 주세요.\n";
            sendedDTO.setContent(content);
            sendedDTO.setSenderId(senderId);
            sendedDTO.setSendedAt(LocalDateTime.now());
            return sendedDTO;
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MailCommandDTO sendInterviewMail(Integer applicationId, Integer senderId) {
        InterviewMailQueryDTO dto = mailMapper.findForInterviewMail(applicationId);
        Integer recruitmentId = dto.getRecruitmentId();
        String recruitmentTitle = dto.getRecruitmentTitle();
        Integer applicantId = dto.getApplicantId();
        String applicantName = dto.getApplicantName();
        String email = dto.getEmail();
        Integer interviewId = dto.getInterviewId();
        LocalDateTime interviewDateTime = dto.getInterviewDatetime();
        String interviewAddress = dto.getInterviewAddress();
        if(applicantId == null) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_APPLICANT_NOT_FOUND);
        }
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 (E) a h시 mm분")
                    .withLocale(Locale.KOREAN);
            String formattedDateTime = interviewDateTime.format(formatter);

            Context context = new Context();
            context.setVariable("recruitmentTitle", recruitmentTitle);
            context.setVariable("applicantName", applicantName);
            context.setVariable("interviewDateTimeFormatted", formattedDateTime);
            context.setVariable("zoomUrl", interviewAddress);

            String base64Image = getBase64EncodedImage("static/images/empick-logo.png");
            context.setVariable("logoImage", base64Image);

            String htmlContent = templateEngine.process("interview-mail-template", context);
            String title = "면접 일정 안내 메일";

            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(title);
            mimeMessageHelper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
            System.out.println("Thymeleaf 템플릿 이메일 전송 성공!");

            MailCommandDTO sendedDTO = new MailCommandDTO();
            sendedDTO.setApplicantId(applicantId);
            sendedDTO.setEmail(email);
            sendedDTO.setTitle(title);
            String content = "[Empick] 면접 일정 안내드립니다\n" +
                    "\n" +
                    "\n" +
                    "안녕하세요, " + applicantName + "님.\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "귀하께서 지원하신 " + recruitmentTitle + " 전형의 채용 공고의 실무 테스트 전형에 합격하셨습니다.\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "다음 단계인 면접은 아래 일시에 ZOOM을 통해 비대면으로 진행됩니다.\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "면접 일시: " + formattedDateTime + "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "아래 버튼을 클릭하여 면접 시간에 맞춰 입장해 주세요.\n" +
                    "\n" +
                    "\n" +
                    interviewAddress + "ZOOM 면접 입장하기\n" +
                    "\n" +
                    "\n" +
                    "본 메일은 발신 전용입니다.\n" +
                    "면접 관련 문의는 채용 담당자에게 연락 부탁드립니다.\n";
            sendedDTO.setContent(content);
            sendedDTO.setSenderId(senderId);
            sendedDTO.setSendedAt(LocalDateTime.now());
            return sendedDTO;
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getBase64EncodedImage(String imagePath) throws IOException {
        InputStream is = new ClassPathResource(imagePath).getInputStream();
        byte[] imageBytes = is.readAllBytes();
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}