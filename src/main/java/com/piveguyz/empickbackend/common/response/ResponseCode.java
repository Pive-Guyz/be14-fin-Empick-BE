package com.piveguyz.empickbackend.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 성공/에러 메세지 코드 관리
 */

@Getter
@RequiredArgsConstructor
public enum ResponseCode {

    // 모든 성공 코드
    SUCCESS(true, HttpStatus.OK, 200, "요청이 성공적으로 처리되었습니다."),

    // 클라이언트 오류 (4xx)
    BAD_REQUEST(false, HttpStatus.BAD_REQUEST, 400, "잘못된 요청입니다."),
    UNAUTHORIZED(false, HttpStatus.UNAUTHORIZED, 401, "인증이 필요합니다."),
    FORBIDDEN(false, HttpStatus.FORBIDDEN, 403, "접근 권한이 없습니다."),
    NOT_FOUND(false, HttpStatus.NOT_FOUND, 404, "요청한 리소스를 찾을 수 없습니다."),
    VALIDATION_FAIL(false, HttpStatus.BAD_REQUEST, 405, "유효성 검증에 실패했습니다."),

    // 서버 오류 (5xx)
    INTERNAL_SERVER_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 내부 오류입니다."),

    // 인사 오류 - 1000 ~ 1999
    MEMBER_NOT_FOUND(false, HttpStatus.NOT_FOUND, 1000, "사원 정보를 찾을 수 없습니다."),
    MEMBER_RESIGNED(false, HttpStatus.NOT_ACCEPTABLE,1001, "삭제된 사원 정보입니다."),
    MEMBER_STATUS_SUSPENDED(false, HttpStatus.NOT_ACCEPTABLE, 1004, "사원 상태가 차단되었습니다."),


    // 채용 오류 - 2000 ~ 2999
    // 채용 공고 - 2000 ~ 2099
    // 1) 채용 요청서
    EMPLOYMENT_REQUEST_NOT_FOUND(false, HttpStatus.NOT_FOUND, 2000, "채용 요청서를 찾을 수 없습니다."),
    EMPLOYMENT_REQUEST_MISSING_DEPARTMENT(false, HttpStatus.BAD_REQUEST, 2001, "부서를 선택하지 않았습니다."),
    EMPLOYMENT_REQUEST_INVALID_HEADCOUNT(false, HttpStatus.BAD_REQUEST, 2002, "모집 인원은 1명 이상이어야 합니다."),
    EMPLOYMENT_REQUEST_INVALID_DATE_RANGE(false, HttpStatus.BAD_REQUEST, 2003, "시작일은 마감일보다 이전이어야 합니다."),
    EMPLOYMENT_REQUEST_MISSING_EMPLOYMENT_TYPE(false, HttpStatus.BAD_REQUEST, 2004, "고용 형태를 입력해야 합니다."),
    EMPLOYMENT_REQUEST_MISSING_QUALIFICATION(false, HttpStatus.BAD_REQUEST, 2005, "자격 요건을 입력해야 합니다."),
    EMPLOYMENT_REQUEST_MISSING_RESPONSIBILITY(false, HttpStatus.BAD_REQUEST, 2006, "담당 업무를 입력해야 합니다."),
    EMPLOYMENT_REQUEST_ALREADY_EXISTS(false, HttpStatus.CONFLICT, 2007, "해당 기간 내 중복된 채용 요청이 존재합니다."),

    // 2) 채용 템플릿
    EMPLOYMENT_TEMPLATE_NOT_FOUND(false, HttpStatus.NOT_FOUND, 2010, "요청한 템플릿을 찾을 수 없습니다."),
    EMPLOYMENT_TEMPLATE_NO_NAME(false, HttpStatus.BAD_REQUEST, 2011, "템플릿 이름을 입력하지 않았습니다."),
    EMPLOYMENT_TEMPLATE_ALREADY_DELETED(false, HttpStatus.GONE, 2012, "이미 삭제된 템플릿입니다."),
    EMPLOYMENT_TEMPLATE_UNAUTHORIZED_ACCESS(false, HttpStatus.FORBIDDEN, 2013, "해당 템플릿에 대한 접근 권한이 없습니다."),
    EMPLOYMENT_TEMPLATE_NO_ITEMS(false, HttpStatus.BAD_REQUEST, 2014, "템플릿 항목이 하나 이상 필요합니다."),
    EMPLOYMENT_TEMPLATE_DUPLICATE_NAME(false, HttpStatus.CONFLICT, 2015, "같은 이름의 템플릿이 이미 존재합니다."),

    //  지원자 - 2100 ~ 2199


    //  지원서 - 2200 ~ 2299


    //  자기소개서 - 2300 ~ 2399


    //  실무테스트 - 2400 ~ 2499
    EMPLOYMENT_INVALID_DIFFICULTY(false, HttpStatus.INTERNAL_SERVER_ERROR, 2400, "유효하지 않은 난이도입니다."),
    EMPLOYMENT_INVALID_TYPE(false, HttpStatus.INTERNAL_SERVER_ERROR, 2401, "유효하지 않은 실무 테스트 유형입니다."),

    //   1) 실무테스트 문제
    EMPLOYMENT_QUESTION_FAIL(false, HttpStatus.INTERNAL_SERVER_ERROR, 2410, "실무테스트 문제 등록에 실패했습니다."),
    EMPLOYMENT_QUESTION_DUPLICATE(false, HttpStatus.CONFLICT, 2411, "동일한 문제가 이미 등록되어 있습니다."),
    EMPLOYMENT_QUESTION_INVALID_MEMBER(false, HttpStatus.BAD_REQUEST, 2412, "작성자 정보가 유효하지 않습니다."),
    EMPLOYMENT_QUESTION_INVALID_UPDATED_MEMBER(false, HttpStatus.BAD_REQUEST, 2413, "수정자 정보가 유효하지 않습니다."),
    EMPLOYMENT_QUESTION_NOT_FOUND(false, HttpStatus.NOT_FOUND, 2414, "요청한 문제를 찾을 수 없습니다."),
    EMPLOYMENT_QUESTION_DELETE_CONFLICT(false, HttpStatus.CONFLICT, 2415, "이 문제는 다른 곳에서 사용 중이므로 삭제할 수 없습니다."),


    //  면접 일정 - 2500 ~ 2599
    EMPLOYMENT_INTERVIEW_CRITERIA_NOT_FOUND(false, HttpStatus.BAD_REQUEST, 2500, "존재하지 않는 면접 기준입니다."),
    EMPLOYMENT_INTERVIEW_CRITERIA_NO_CONTENT(false, HttpStatus.BAD_REQUEST, 2510, "내용을 입력하지 않았습니다."),
    EMPLOYMENT_INTERVIEW_CRITERIA_NO_DETAIL_CONTENT(false, HttpStatus.BAD_REQUEST, 2511, "상세 내용을 입력하지 않았습니다."),
    EMPLOYMENT_INTERVIEW_CRITERIA_DUPLICATE_CONTENT(false, HttpStatus.BAD_REQUEST, 2512, "이미 존재하는 내용입니다."),
    EMPLOYMENT_INTERVIEW_SHEET_NOT_FOUND(false, HttpStatus.BAD_REQUEST, 2520, "존재하지 않습니다."),
    EMPLOYMENT_INTERVIEW_SHEET_NO_NAME(false, HttpStatus.BAD_REQUEST, 2521, "이름을 입력하지 않았습니다."),
    EMPLOYMENT_INTERVIEW_SHEET_DUPLICATE_NAME(false, HttpStatus.CONFLICT, 2522, "중복된 이름이 존재합니다."),
    EMPLOYMENT_INTERVIEW_SHEET_ITEM_NOT_FOUND(false, HttpStatus.BAD_REQUEST, 2530, "존재하지 않는 항목입니다."),
    EMPLOYMENT_INTERVIEW_SHEET_ITEM_DUPLICATE(false, HttpStatus.BAD_REQUEST, 2531, "이미 존재하는 항목입니다."),


    //  안내 메일 - 2600 ~ 2699
    EMPLOYMENT_MAIL_TEMPLATE_DUPLICATE_TITLE(false, HttpStatus.CONFLICT, 2600, "이름이 중복된 템플릿이 존재합니다."),
    EMPLOYMENT_MAIL_TEMPLATE_NO_TITLE(false, HttpStatus.BAD_REQUEST, 2601, "제목을 입력하지 않았습니다."),
    EMPLOYMENT_MAIL_TEMPLATE_NO_CONTENT(false, HttpStatus.BAD_REQUEST, 2602, "내용을 입력하지 않았습니다."),
    EMPLOYMENT_MAIL_TEMPLATE_NOT_FOUND(false, HttpStatus.BAD_REQUEST, 2603, "존재하지 않는 템플릿입니다."),

    EMPLOYMENT_MAIL_NOT_FOUND(false, HttpStatus.BAD_REQUEST, 2630, "요청한 메일을 찾을 수 없습니다."),
    EMPLOYMENT_MAIL_NO_CONTENT(false, HttpStatus.BAD_REQUEST, 2631, "메일의 내용을 입력하지 않았습니다."),
    EMPLOYMENT_MAIL_INADEQUATE_EMAIL(false, HttpStatus.CONFLICT, 2632, "유효하지 않은 형태의 이메일입니다.");



    private final boolean success;
    private final HttpStatus httpStatus;        // HTTP 상태 코드
    private final int code;                     // 비지니스 로직
    private final String message;
}
