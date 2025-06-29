package com.piveguyz.empickbackend.employment.mail.command.application.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.mail.command.application.dto.MailCommandDTO;
import com.piveguyz.empickbackend.employment.mail.command.application.service.MailCommandService;
import com.piveguyz.empickbackend.employment.mail.facade.MailFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "안내 메일 API", description = "안내 메일 관리")
@RestController
@RequestMapping("/api/v1/employment/mail")
@SecurityRequirement(name = "bearerAuth")
public class MailCommandController {
    private final MailCommandService mailCommandService;
    private final MailFacade mailFacade;

    @Autowired
    public MailCommandController(MailCommandService mailCommandService, MailFacade mailFacade) {
        this.mailCommandService = mailCommandService;
        this.mailFacade = mailFacade;
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
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2633", description = "메일의 제목을 입력하지 않았습니다."),
    })
    @PostMapping("/create")
    public ResponseEntity<CustomApiResponse<MailCommandDTO>> createMail(@RequestBody MailCommandDTO mailCommandDTO) {
        MailCommandDTO createdDTO = mailCommandService.createMail(mailCommandDTO);       // DB에 메일 저장
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, createdDTO));
    }

    @Operation(
            summary = "안내 메일 발송",
            description = """
    - 안내 메일을 발송합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2631", description = "메일의 내용을 입력하지 않았습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2632", description = "유효하지 않은 형태의 이메일입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2633", description = "메일의 제목을 입력하지 않았습니다."),
    })
    @PostMapping("/send")
    public ResponseEntity<CustomApiResponse<MailCommandDTO>> sendMail(@RequestBody MailCommandDTO mailCommandDTO) {
        MailCommandDTO sendedDTO = mailFacade.sendMail(mailCommandDTO);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, sendedDTO));

    }

    @Operation(
            summary = "실무 테스트 일정 안내 메일 발송",
            description = """
    - 실무 테스트 일정 안내 메일을 발송합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2631", description = "메일의 내용을 입력하지 않았습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2632", description = "유효하지 않은 형태의 이메일입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2633", description = "메일의 제목을 입력하지 않았습니다."),
    })
    @PostMapping("/send/jobtest/{applicationId}")
    public ResponseEntity<CustomApiResponse<MailCommandDTO>> sendJobtestMail(@PathVariable("applicationId") Integer applicationId,
                                                                             @RequestParam("senderId") Integer senderId) {
        MailCommandDTO sendedDTO = mailFacade.sendJobtestMail(applicationId, senderId);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, sendedDTO));
    }

    @Operation(
            summary = "면접 일정 안내 메일 발송",
            description = """
    - 면접 일정 안내 메일을 발송합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2631", description = "메일의 내용을 입력하지 않았습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2632", description = "유효하지 않은 형태의 이메일입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2633", description = "메일의 제목을 입력하지 않았습니다."),
    })
    @PostMapping("/send/interview/{applicationId}")
    public ResponseEntity<CustomApiResponse<MailCommandDTO>> sendInterviewMail(@PathVariable("applicationId") Integer applicationId,
                                                                               @RequestParam("senderId") Integer senderId) {
        MailCommandDTO sendedDTO = mailFacade.sendInterviewMail(applicationId, senderId);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, sendedDTO));
    }
}
