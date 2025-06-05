package com.piveguyz.empickbackend.employment.mail.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.mail.command.application.dto.MailCommandDTO;
import com.piveguyz.empickbackend.employment.mail.command.application.service.MailCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/employment/mail")
public class MailCommandController {
    private final MailCommandService mailCommandService;

    public MailCommandController(MailCommandService mailCommandService) {
        this.mailCommandService = mailCommandService;
    }

    @Operation(
            summary = "안내 메일 등록",
            description = """
    - 안내 메일을 관리합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2631", description = "메일의 내용을 입력하지 않았습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2632", description = "유효하지 않은 형태의 이메일입니다."),
    })

    @PostMapping("/create")
    public ResponseEntity<CustomApiResponse<MailCommandDTO>> createMail(@RequestBody MailCommandDTO mailCommandDTO) {
        MailCommandDTO createdMailCommandDTO = mailCommandService.createMail(mailCommandDTO);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, createdMailCommandDTO));
    }
}
