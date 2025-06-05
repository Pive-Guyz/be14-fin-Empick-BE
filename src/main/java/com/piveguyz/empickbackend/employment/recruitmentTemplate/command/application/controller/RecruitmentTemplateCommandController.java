package com.piveguyz.empickbackend.employment.recruitmentTemplate.command.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.recruitmentTemplate.command.application.dto.RecruitmentTemplateCommandDTO;
import com.piveguyz.empickbackend.employment.recruitmentTemplate.command.application.service.RecruitmentTemplateCommandService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "채용 공고 템플릿 커맨드 API", description = "채용 공고 템플릿 등록/수정/삭제")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employment/recruitment/template")
public class RecruitmentTemplateCommandController {
	private final RecruitmentTemplateCommandService recruitmentTemplateCommandService;

	@Operation(summary = "채용 공고 템플릿 등록", description = "템플릿 이름과 항목 리스트를 포함해 등록합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
		@ApiResponse(responseCode = "2014", description = "템플릿 항목이 하나 이상 필요합니다."),
		@ApiResponse(responseCode = "2015", description = "같은 이름의 템플릿이 이미 존재합니다."),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류입니다.")
	})
	@PostMapping
	public ResponseEntity<CustomApiResponse<Void>> create(@RequestBody RecruitmentTemplateCommandDTO dto) {
		recruitmentTemplateCommandService.createTemplate(dto);
		return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
			.body(CustomApiResponse.of(ResponseCode.SUCCESS));
	}

	@Operation(summary = "채용 공고 템플릿 수정", description = "템플릿 ID를 기반으로 이름 및 항목 내용을 수정합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
		@ApiResponse(responseCode = "2010", description = "요청한 템플릿을 찾을 수 없습니다."),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류입니다.")
	})
	@PutMapping("/{id}")
	public ResponseEntity<CustomApiResponse<Void>> update(@PathVariable int id, @RequestBody RecruitmentTemplateCommandDTO dto) {
		recruitmentTemplateCommandService.updateTemplate(id, dto);
		return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
			.body(CustomApiResponse.of(ResponseCode.SUCCESS));
	}

	@Operation(summary = "채용 공고 템플릿 삭제", description = "템플릿 ID를 기반으로 삭제 여부를 Y로 설정합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
		@ApiResponse(responseCode = "2010", description = "요청한 템플릿을 찾을 수 없습니다."),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류입니다.")
	})
	@PutMapping("/delete/{id}")
	public ResponseEntity<CustomApiResponse<Void>> delete(@PathVariable int id) {
		recruitmentTemplateCommandService.deleteTemplate(id);
		return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
			.body(CustomApiResponse.of(ResponseCode.SUCCESS));
	}
}
