package com.piveguyz.empickbackend.employment.mail;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.employment.mail.query.dto.MailQueryDTO;
import com.piveguyz.empickbackend.employment.mail.query.mapper.MailMapper;
import com.piveguyz.empickbackend.employment.mail.query.service.MailQueryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class MailQueryServiceTest {

    private MailMapper mailMapper;
    private MailQueryServiceImpl mailQueryService;

    @BeforeEach
    void setUp() {
        mailMapper = mock(MailMapper.class);
        mailQueryService = new MailQueryServiceImpl(mailMapper);
    }

    @Test
    @DisplayName("전체 메일 조회")
    void findAllTest() {
        MailQueryDTO mail = new MailQueryDTO();
        mail.setId(1);
        mail.setEmail("test@example.com");

        when(mailMapper.findAll()).thenReturn(List.of(mail));

        List<MailQueryDTO> result = mailQueryService.findAll();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getEmail()).isEqualTo("test@example.com");
    }

    @Test
    @DisplayName("ID로 메일 조회 - 성공")
    void findByIdSuccessTest() {
        MailQueryDTO dto = new MailQueryDTO();
        dto.setId(2);
        dto.setEmail("find@example.com");

        when(mailMapper.findById(2)).thenReturn(dto);

        MailQueryDTO result = mailQueryService.findById(2);

        assertThat(result.getEmail()).isEqualTo("find@example.com");
    }

    @Test
    @DisplayName("ID로 메일 조회 - 실패")
    void findByIdFailTest() {
        when(mailMapper.findById(99)).thenReturn(null);

        assertThatThrownBy(() -> mailQueryService.findById(99))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("메일");
    }

    @Test
    @DisplayName("이메일로 메일 조회 - 성공")
    void findByEmailSuccessTest() {
        String email = "search@example.com";
        MailQueryDTO dto = new MailQueryDTO();
        dto.setId(3);
        dto.setEmail(email);

        when(mailMapper.findByEmail(email)).thenReturn(List.of(dto));

        List<MailQueryDTO> result = mailQueryService.findByEmail(email);

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("이메일 형식 오류")
    void findByEmailFailTest() {
        String invalidEmail = "invalidEmailFormat";

        assertThatThrownBy(() -> mailQueryService.findByEmail(invalidEmail))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("이메일");
    }
}