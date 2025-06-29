package com.piveguyz.empickbackend.employment.mail;

import com.piveguyz.empickbackend.employment.mail.command.application.dto.MailCommandDTO;
import com.piveguyz.empickbackend.employment.mail.command.application.service.MailCommandService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MailCommandControllerTest {

    @Autowired
    private MailCommandService mailCommandService;

    @Test
    @DisplayName("✅ 안내 메일 등록 테스트")
    void createMailTest() {
        MailCommandDTO dto = new MailCommandDTO();
        dto.setApplicantId(1); // 존재하는 applicantId로 수정
        dto.setEmail("test@example.com");
        dto.setTitle("[테스트] 등록 메일 제목");
        dto.setContent("메일 내용입니다.");
        dto.setSenderId(1);

        MailCommandDTO saved = mailCommandService.createMail(dto);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        System.out.println("📨 저장된 메일 ID: " + saved.getId());
    }

    @Test
    @DisplayName("✅ 간단한 텍스트 메일 전송 테스트")
    void sendSimpleMailTest() {
        MailCommandDTO dto = new MailCommandDTO();
        dto.setEmail("test@example.com");
        dto.setTitle("[테스트] 간단 메일 제목");
        dto.setContent("간단한 메일 내용입니다.");
        dto.setSenderId(1);
        dto.setSendedAt(LocalDateTime.now());

        MailCommandDTO result = mailCommandService.sendSimpleMail(dto);
        assertThat(result).isNotNull();
        System.out.println("✅ 텍스트 메일 전송 완료 to: " + result.getEmail());
    }

    @Test
    @DisplayName("✅ HTML 메일 전송 테스트")
    void sendHTMLMailTest() {
        MailCommandDTO dto = new MailCommandDTO();
        dto.setEmail("test@example.com");
        dto.setTitle("[테스트] HTML 메일 제목");
        dto.setContent("<p>HTML 메일 내용입니다.</p>");
        dto.setSenderId(1);

        MailCommandDTO result = mailCommandService.sendHTMLMail(dto);
        assertThat(result).isNotNull();
        System.out.println("✅ HTML 메일 전송 완료 to: " + result.getEmail());
    }
}
