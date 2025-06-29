package com.piveguyz.empickbackend.employment.mail.query.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.mail.query.dto.MailQueryDTO;
import com.piveguyz.empickbackend.employment.mail.query.service.MailQueryService;
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
public class MailQueryController {
    private final MailQueryService mailQueryService;

    @Autowired
    public MailQueryController(MailQueryService mailQueryService) {
        this.mailQueryService = mailQueryService;
    }


    @Operation(
            summary = "안내 메일 전체 조회",
            description = """
    - 안내 메일을 모두 조회합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @GetMapping
    public ResponseEntity<CustomApiResponse<List<MailQueryDTO>>> findAll() {
        List<MailQueryDTO> mailQueryDTOList = mailQueryService.findAll();
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, mailQueryDTOList));
    }

    @Operation(
            summary = "안내 메일 id로 조회",
            description = """
    - 안내 메일을 id로 조회합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2630", description = "요청한 메일을 찾을 수 없습니다.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<MailQueryDTO>> findById(@PathVariable("id") Integer id){
        MailQueryDTO mailQueryDTO = mailQueryService.findById(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, mailQueryDTO));
    }

    @Operation(
            summary = "안내 메일 이메일로 검색",
            description = """
    - 안내 메일을 이메일로 검색합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2632", description = "유효하지 않은 형태의 이메일입니다.")
    })
    @GetMapping("/email")
    public ResponseEntity<CustomApiResponse<List<MailQueryDTO>>> findByEmail(@RequestParam("email") String email){
        List<MailQueryDTO> mailQueryDTOList = mailQueryService.findByEmail(email);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, mailQueryDTOList));
    }
}
