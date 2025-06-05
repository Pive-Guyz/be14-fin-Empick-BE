package com.piveguyz.empickbackend.employment.mail.query.controller;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.mail.query.dto.MailQueryDTO;
import com.piveguyz.empickbackend.employment.mail.query.service.MailQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/employment/mail")
public class MailQueryController {
    private final MailQueryService mailQueryService;

    @Autowired
    public MailQueryController(MailQueryService mailQueryService) {
        this.mailQueryService = mailQueryService;
    }

    @Operation(
            summary = "안내 메일 조회",
            description = """
    - 안내 메일을 조회합니다.
    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2630", description = "요청한 메일을 찾을 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2632", description = "유효하지 않은 형태의 이메일입니다.")
    })

    @GetMapping("/findAll")
    public ResponseEntity<CustomApiResponse<List<MailQueryDTO>>> findAll() {
        List<MailQueryDTO> mailQueryDTOList = mailQueryService.findAll();
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, mailQueryDTOList));
    }

    @GetMapping("/findById")
    public ResponseEntity<CustomApiResponse<MailQueryDTO>> findById(@RequestParam("id") Integer id){
        MailQueryDTO mailQueryDTO = mailQueryService.findById(id);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, mailQueryDTO));
    }

    @GetMapping("/findByEmail")
    public ResponseEntity<CustomApiResponse<List<MailQueryDTO>>> findByEmail(@RequestParam("email") String email){
        List<MailQueryDTO> mailQueryDTOList = mailQueryService.findByEmail(email);
        ResponseCode result = ResponseCode.SUCCESS;
        return ResponseEntity.status(result.getHttpStatus())
                .body(CustomApiResponse.of(result, mailQueryDTOList));
    }
}
