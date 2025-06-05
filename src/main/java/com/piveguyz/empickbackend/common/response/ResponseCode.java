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


    //  면접 - 2500 ~ 2599
    EMPLOYMENT_INTERVIEW_CRITERIA_NOT_FOUND(false, HttpStatus.BAD_REQUEST, 2500, "존재하지 않는 면접 기준입니다."),


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
