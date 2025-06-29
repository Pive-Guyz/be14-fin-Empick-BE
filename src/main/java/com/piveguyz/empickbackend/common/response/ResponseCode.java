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
    CREATED(true, HttpStatus.CREATED, 201, "리소스가 성공적으로 생성되었습니다."),

    // 클라이언트 오류 (4xx)
    BAD_REQUEST(false, HttpStatus.BAD_REQUEST, 400, "잘못된 요청입니다."),
    UNAUTHORIZED(false, HttpStatus.UNAUTHORIZED, 401, "인증이 필요합니다."),
    FORBIDDEN(false, HttpStatus.FORBIDDEN, 403, "접근 권한이 없습니다."),
    NOT_FOUND(false, HttpStatus.NOT_FOUND, 404, "요청한 리소스를 찾을 수 없습니다."),
    VALIDATION_FAIL(false, HttpStatus.BAD_REQUEST, 405, "유효성 검증에 실패했습니다."),
    INVALID_PARAMETER(false, HttpStatus.BAD_REQUEST, 400, "잘못된 매개변수입니다."),

    // 서버 오류 (5xx)
    INTERNAL_SERVER_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 내부 오류입니다."),
    DATABASE_CONNECTION_ERROR(false, HttpStatus.SERVICE_UNAVAILABLE, 503, "DB 연결에 실패했습니다."),

    // 인사 오류 - 1000 ~ 1999
    MEMBER_NOT_FOUND(false, HttpStatus.NOT_FOUND, 1000, "사원 정보를 찾을 수 없습니다."),
    MEMBER_RESIGNED(false, HttpStatus.NOT_ACCEPTABLE, 1001, "삭제된 사원 정보입니다."),
    MEMBER_STATUS_SUSPENDED(false, HttpStatus.NOT_ACCEPTABLE, 1004, "사원 상태가 차단되었습니다."),
    MEMBER_EMAIL_DUPLICATED(false, HttpStatus.CONFLICT , 1005, "이미 존재하는 이메일입니다."),
    MEMBER_CREATED_MEMBER_ID_REQUIRED(false, HttpStatus.BAD_REQUEST, 1006, "입사처리자가 지정되지 않았습니다."),
    MEMBER_CREATED_MEMBER_NOT_FOUND(false, HttpStatus.NOT_FOUND, 1007, "입사처리자를 찾을 수 없습니다."),
    MEMBER_CREATED_MEMBER_NO_PERMISSION(false, HttpStatus.FORBIDDEN, 1008, "입사처리자는 ROLE_HR_ACCESS 권한이 있어야 합니다."),
    NO_HR_PERMISSION(false, HttpStatus.FORBIDDEN, 1008, "ROLE_HR_ACCESS 권한이 있어야 합니다."),
    MEMBER_PROFILE_IMAGE_NOT_FOUND(false, HttpStatus.NOT_FOUND, 1009 , "사원의 프로필 사진을 찾을 수 없습니다." ),
    MEMBER_ID_INVALID(false, HttpStatus.BAD_REQUEST, 1010, "유효하지 않은 사원 ID 입니다."),
    DUPLICATE_EDIT_REQUEST(false, HttpStatus.CONFLICT, 1011, "같은 필드에 대해 이미 대기중인 수정 요청이 존재합니다."),
    MEMBER_PROFILE_IMAGE_REQUIRED(false, HttpStatus.BAD_REQUEST, 1012, "프로필 이미지는 필수입니다."),
    EDIT_PROPOSAL_NOT_FOUND(false, HttpStatus.NOT_FOUND, 1100, "변경 요청을 찾을 수 없습니다."),
    EDIT_PROPOSAL_ALREADY_REJECTED(false, HttpStatus.BAD_REQUEST, 1101, "이미 거절된 요청입니다. 변경할 수 없습니다."),
    EDIT_PROPOSAL_SELF_APPROVE_NOT_ALLOWED(false, HttpStatus.FORBIDDEN, 1102, "본인이 요청한 변경은 본인이 처리할 수 없습니다."),
    EDIT_PROPOSAL_INVALID_FIELD(false, HttpStatus.BAD_REQUEST, 1103, "유효하지 않은 필드입니다."),
    EDIT_PROPOSAL_APPROVED(true, HttpStatus.ACCEPTED, 1104, "변경 요청이 승인되었습니다."),
    EDIT_PROPOSAL_REJECTED(true, HttpStatus.NOT_ACCEPTABLE, 1105, "변경 요청이 거절되었습니다."),
    REQUIRED_LOGIN(true, HttpStatus.NOT_ACCEPTABLE, 1106, "로그인이 필요합니다."),
    ALREDY_RESIGNED(true, HttpStatus.NOT_ACCEPTABLE, 1107, "퇴사 처리된 사원입니다."),
    INVALID_STATUS_VALUE(true, HttpStatus.BAD_REQUEST, 1108, "요청된 상태 값이 유효하지 않습니다."),
    EDIT_PROPOSAL_MEMBER_MISMATCH(true, HttpStatus.BAD_REQUEST, 1109, "해당 제안이 사원 정보와 일치하지 않습니다."),
    MEMBER_UPDATE_PERMISSION_DENIED(false, HttpStatus.FORBIDDEN, 1110, "본인의 정보만 수정할 수 있습니다."),

    // 부서, 직책, 직무, 직급 관련 NOT_FOUND
    DEPARTMENT_NOT_FOUND(false, HttpStatus.NOT_FOUND, 1200, "요청한 부서를 찾을 수 없습니다."),
    POSITION_NOT_FOUND(false, HttpStatus.NOT_FOUND, 1201, "요청한 직책을 찾을 수 없습니다."),
    JOB_NOT_FOUND(false, HttpStatus.NOT_FOUND, 1202, "요청한 직무를 찾을 수 없습니다."),
    RANK_NOT_FOUND(false, HttpStatus.NOT_FOUND, 1203, "요청한 직급을 찾을 수 없습니다."),

    RANK_CODE_DUPLICATE(false, HttpStatus.CONFLICT, 1204, "이미 존재하는 직급 코드입니다."),
    RANK_CODE_INVALID_FORMAT(false, HttpStatus.BAD_REQUEST, 1205, "직급 코드 형식이 올바르지 않습니다."),
    RANK_NAME_DUPLICATE(false, HttpStatus.CONFLICT, 1206, "이미 존재하는 직급명입니다."),
    RANK_IN_USE(false, HttpStatus.CONFLICT, 1207, "해당 직급을 사용하는 사원이 있어 삭제할 수 없습니다."),

    POSITION_NAME_DUPLICATE(false, HttpStatus.CONFLICT, 1208, "이미 존재하는 직책명입니다."),
    POSITION_NAME_INVALID_FORMAT(false, HttpStatus.BAD_REQUEST, 1209, "직책명 형식이 올바르지 않습니다."),
    POSITION_IN_USE(false, HttpStatus.CONFLICT, 1210, "해당 직책을 사용하는 사원이 있어 삭제할 수 없습니다."),
    POSITION_DESCRIPTION_TOO_LONG(false, HttpStatus.BAD_REQUEST, 1211, "직책 설명이 너무 깁니다."),

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
    EMPLOYMENT_REQUEST_INVALID_JOB_ID(false, HttpStatus.BAD_REQUEST, 2008, "유효하지 않은 직무 ID입니다."),
    EMPLOYMENT_REQUEST_INVALID_DEPARTMENT_ID(false, HttpStatus.BAD_REQUEST, 2009, "유효하지 않은 부서 ID입니다."),

    // 2) 채용 템플릿
    EMPLOYMENT_TEMPLATE_NOT_FOUND(false, HttpStatus.NOT_FOUND, 2010, "요청한 템플릿을 찾을 수 없습니다."),
    EMPLOYMENT_TEMPLATE_NO_NAME(false, HttpStatus.BAD_REQUEST, 2011, "템플릿 이름을 입력하지 않았습니다."),
    EMPLOYMENT_TEMPLATE_ALREADY_DELETED(false, HttpStatus.GONE, 2012, "이미 삭제된 템플릿입니다."),
    EMPLOYMENT_TEMPLATE_UNAUTHORIZED_ACCESS(false, HttpStatus.FORBIDDEN, 2013, "해당 템플릿에 대한 접근 권한이 없습니다."),
    EMPLOYMENT_TEMPLATE_NO_ITEMS(false, HttpStatus.BAD_REQUEST, 2014, "템플릿 항목이 하나 이상 필요합니다."),
    EMPLOYMENT_TEMPLATE_DUPLICATE_NAME(false, HttpStatus.CONFLICT, 2015, "같은 이름의 템플릿이 이미 존재합니다."),

    // 3) 채용공고
    EMPLOYMENT_RECRUITMENT_NOT_FOUND(false, HttpStatus.NOT_FOUND, 2030, "요청한 채용 공고를 찾을 수 없습니다."),
    EMPLOYMENT_RECRUITMENT_INVALID_PERIOD(false, HttpStatus.BAD_REQUEST, 2031, "채용 기간이 유효하지 않습니다."),
    EMPLOYMENT_RECRUITMENT_ALREADY_DELETED(false, HttpStatus.GONE, 2032, "이미 삭제된 채용 공고입니다."),
    EMPLOYMENT_RECRUITMENT_UNAUTHORIZED_ACCESS(false, HttpStatus.FORBIDDEN, 2033, "채용 공고에 접근할 권한이 없습니다."),
    EMPLOYMENT_RECRUITMENT_CANNOT_MODIFY_PUBLISHED(false, HttpStatus.CONFLICT, 2034, "게시된 채용 공고는 수정할 수 없습니다."),
    EMPLOYMENT_RECRUITMENT_NO_TITLE(false, HttpStatus.BAD_REQUEST, 2035, "채용 공고 제목을 입력하지 않았습니다."),
    EMPLOYMENT_RECRUITMENT_NO_CONTENT(false, HttpStatus.BAD_REQUEST, 2036, "채용 공고 내용을 입력하지 않았습니다."),
    EMPLOYMENT_RECRUITMENT_NO_START_DATE(false, HttpStatus.BAD_REQUEST, 2037, "채용 시작일을 입력하지 않았습니다."),
    EMPLOYMENT_RECRUITMENT_NO_END_DATE(false, HttpStatus.BAD_REQUEST, 2038, "채용 마감일을 입력하지 않았습니다."),
    EMPLOYMENT_RECRUITMENT_INVALID_DATE_ORDER(false, HttpStatus.BAD_REQUEST, 2039, "마감일은 시작일보다 이후여야 합니다."),
    EMPLOYMENT_RECRUITMENT_NO_INTRODUCE_TEMPLATE(false, HttpStatus.BAD_REQUEST, 2040, "자기소개서 템플릿이 지정되지 않았습니다."),
    EMPLOYMENT_RECRUITMENT_NO_MEMBER_ID(false, HttpStatus.BAD_REQUEST, 2041, "작성자 정보가 누락되었습니다."),
    EMPLOYMENT_RECRUITMENT_DUPLICATE_TITLE(false, HttpStatus.CONFLICT, 2042, "같은 제목의 채용 공고가 이미 존재합니다."),
    EMPLOYMENT_RECRUITMENT_INVALID_STATUS_TRANSITION(false, HttpStatus.BAD_REQUEST, 2043, "잘못된 상태 전환입니다. 승인 대기 → 승인 → 게시 → 종료 순으로만 변경 가능합니다."),
    EMPLOYMENT_RECRUITMENT_HAS_APPLICATIONS(false, HttpStatus.BAD_REQUEST, 2044,"이미 지원자가 존재하는 채용공고는 삭제할 수 없습니다."),

    // 4) 지원서 항목
    EMPLOYMENT_APPLICATION_ITEM_CATEGORY_NOT_FOUND(false, HttpStatus.NOT_FOUND, 2050, "지원서 항목 카테고리를 찾을 수 없습니다."),
    EMPLOYMENT_APPLICATION_ITEM_CATEGORY_EMPTY(false, HttpStatus.NO_CONTENT, 2051, "지원서 항목 카테고리가 존재하지 않습니다."),
    EMPLOYMENT_APPLICATION_ITEM_DUPLICATED(false, HttpStatus.CONFLICT, 2052, "동일한 항목이 양식에 중복 포함되어 있습니다."),
    EMPLOYMENT_APPLICATION_ITEM_TEMPLATE_NOT_FOUND(false, HttpStatus.NOT_FOUND, 2053, "해당 채용공고에 연결된 지원서 양식을 찾을 수 없습니다."),

    // 5) 채용 프로세스
    EMPLOYMENT_RECRUITMENT_PROCESS_NOT_FOUND(false, HttpStatus.NOT_FOUND, 2060, "채용 프로세스를 찾을 수 없습니다."),
    EMPLOYMENT_RECRUITMENT_PROCESS_EMPTY(false, HttpStatus.NO_CONTENT, 2061, "채용 프로세스가 존재하지 않습니다."),
    EMPLOYMENT_RECRUITMENT_PROCESS_INVALID_STEP(false, HttpStatus.BAD_REQUEST, 2062, "유효하지 않은 전형 단계 코드입니다."),
    EMPLOYMENT_RECRUITMENT_PROCESS_DUPLICATED_ORDER(false, HttpStatus.CONFLICT, 2063, "채용 프로세스 순서(displayOrder)가 중복되었습니다."),
    EMPLOYMENT_RECRUITMENT_PROCESS_RECRUITMENT_NOT_FOUND(false, HttpStatus.NOT_FOUND, 2064, "채용 공고를 찾을 수 없습니다."),

    // 6) 채용 공고 템플릿 복사본
    EMPLOYMENT_TEMPLATE_COPY_NOT_FOUND(false, HttpStatus.NOT_FOUND, 2070, "채용 템플릿 복사본을 찾을 수 없습니다."),
    EMPLOYMENT_TEMPLATE_COPY_EMPTY(false, HttpStatus.BAD_REQUEST, 2071, "복사본이 비어 있습니다."),
    EMPLOYMENT_TEMPLATE_COPY_DUPLICATED_ORDER(false, HttpStatus.CONFLICT, 2072, "채용 템플릿 복사본 displayOrder가 중복되었습니다."),
    EMPLOYMENT_TEMPLATE_COPY_RECRUITMENT_NOT_FOUND(false, HttpStatus.NOT_FOUND, 2073, "해당 채용공고에 대한 복사본이 없습니다."),

    //  지원자 - 2100 ~ 2199
    EMPLOYMENT_APPLICANT_NOT_FOUND(false, HttpStatus.NOT_FOUND, 2100, "요청한 ID가 존재하지 않습니다."),
    EMPLOYMENT_APPLICANT_DUPLICATE_EMAIL(false, HttpStatus.CONFLICT, 2101, "이미 등록된 이메일입니다."),
    EMPLOYMENT_APPLICANT_DUPLICATE_PHONE(false, HttpStatus.CONFLICT, 2102, "이미 등록된 연락처입니다."),
    APPLICATION_RESPONSE_DUPLICATE(false, HttpStatus.CONFLICT, 2103, "이미 등록된 응답입니다."),
    APPLICATION_RESPONSE_EMPTY_CONTENT(false, HttpStatus.BAD_REQUEST, 2104, "응답 내용은 비워둘 수 없습니다."),
    EMPLOYMENT_APPLICATION_DUPLICATE_APPLICATION(false, HttpStatus.CONFLICT, 2105, "이미 해당 공고에 지원한 이력이 있습니다."),
    EMPLOYMENT_APPLICATION_NOT_FOUND(false,HttpStatus.NOT_FOUND, 2106, "지원서를 찾을 수 없습니다."),
    APPLICATION_STATUS_UPDATED(true, HttpStatus.OK, 2107, "지원서 상태가 성공적으로 변경되었습니다."),
    COMMAND_INVALID_ID_ON_CREATE(false, HttpStatus.BAD_REQUEST, 2105, "생성 요청에 ID를 포함할 수 없습니다."),
    DUPLICATE_TEMPLATE_TITLE(false, HttpStatus.CONFLICT, 2106, "이미 존재하는 템플릿 제목입니다."),
    INTRODUCE_TEMPLATE_NOT_FOUND(false, HttpStatus.NOT_FOUND, 2107, "요청한 자기소개서 템플릿 ID가 존재하지 않습니다."),
    INTRODUCE_TEMPLATE_ITEM_NOT_FOUND(false, HttpStatus.NOT_FOUND, 2108, "요청한 템플릿 항목 ID가 존재하지 않습니다."),
    DUPLICATE_TEMPLATE_ITEM_TITLE(false, HttpStatus.CONFLICT, 2110, "이미 존재하는 템플릿 항목명입니다."),
    DUPLICATE_INTRODUCE_FOR_TEMPLATE(false, HttpStatus.CONFLICT, 2112, "이미 해당 템플릿에 대한 지원자의 자기소개서가 존재합니다."),
    INTRODUCE_STANDARD_ITEM_NOT_FOUND(false, HttpStatus.NOT_FOUND, 2113, "해당 자기소개서 기준표 항목을 찾을 수 없습니다."),
    DUPLICATE_STANDARD_ITEM_CONTENT(false, HttpStatus.CONFLICT, 2114, "이미 존재하는 자기소개서 기준표 항목입니다."),
    DUPLICATE_STANDARD_ITEM_CONTENT_TITLE(false, HttpStatus.CONFLICT, 2115, "이미 존재하는 자기소개서 기준표 항목명입니다."),
    INTRODUCE_STANDARD_NOT_FOUND(false, HttpStatus.NOT_FOUND , 2116 ,"자기소개서 기준표 id를 찾을 수 없습니다" ),
    DUPLICATE_STANDARD_CONTENT(false,HttpStatus.CONFLICT ,2117 ,"중복된 내용입니다" ),
    INTRODUCE_RATING_RESULT_NOT_FOUND(false, HttpStatus.NOT_FOUND, 2113, "요청한 자기소개서 평가 결과 ID가 존재하지 않습니다."),



    //  지원서 - 2200 ~ 2299


    //  자기소개서 - 2300 ~ 2399


    //  실무테스트 - 2400 ~ 2499
    EMPLOYMENT_INVALID_DIFFICULTY(false, HttpStatus.NOT_FOUND, 2400, "유효하지 않은 난이도입니다."),
    EMPLOYMENT_INVALID_MEMBER(false, HttpStatus.BAD_REQUEST, 2402, "작성자 정보가 유효하지 않습니다."),
    EMPLOYMENT_INVALID_UPDATED_MEMBER(false, HttpStatus.BAD_REQUEST, 2403, "수정자 정보가 유효하지 않습니다."),
    EMPLOYMENT_INVALID_SATISFIED_VALUE(false, HttpStatus.BAD_REQUEST, 2404, "isSatisfied 값은 'Y' 또는 'N'이어야 합니다."),
    EMPLOYMENT_INVALID_JOBTEST_STATUS(false, HttpStatus.BAD_REQUEST, 2405, "유효하지 않은 실무테스트 처리 상태입니다."),
    EMPLOYMENT_INVALID_CORRECTED_VALUE(false, HttpStatus.BAD_REQUEST, 2406, "isCorrect 값은 'Y' 또는 'N'이어야 합니다."),

    //   1) 실무테스트 문제
    EMPLOYMENT_QUESTION_FAIL(false, HttpStatus.INTERNAL_SERVER_ERROR, 2410, "실무테스트 문제 등록에 실패했습니다."),
    EMPLOYMENT_QUESTION_DUPLICATE(false, HttpStatus.CONFLICT, 2411, "동일한 문제가 이미 등록되어 있습니다."),
    EMPLOYMENT_INVALID_QUESTION_TYPE(false, HttpStatus.BAD_REQUEST, 2412, "유효하지 않은 실무테스트 문제 유형입니다."),
    EMPLOYMENT_INVALID_QUESTION(false, HttpStatus.NOT_FOUND, 2413, "요청한 문제를 찾을 수 없습니다."),
    EMPLOYMENT_QUESTION_DELETE_CONFLICT(false, HttpStatus.CONFLICT, 2414, "이 문제는 다른 곳에서 사용 중이므로 삭제할 수 없습니다."),
    EMPLOYMENT_QUESTION_USED_IN_ANSWER(false, HttpStatus.BAD_REQUEST,2415, "해당 문제는 제출된 답변이 있어 삭제할 수 없습니다."),
    EMPLOYMENT_OPTION_COUNT_EXCEEDED(false, HttpStatus.BAD_REQUEST, 2416, "선택지는 최대 5개까지만 등록할 수 있습니다."),
    EMPLOYMENT_QUESTION_OPTION_NOT_FOUND(false, HttpStatus.NOT_FOUND, 2417, "선택지를 찾을 수 없습니다."),
    EMPLOYMENT_QUESTION_OPTION_MAX_NUMBER(false, HttpStatus.BAD_REQUEST, 2418, "선택지는 5번을 초과할 수 없습니다."),
    EMPLOYMENT_QUESTION_USED_IN_JOBTEST(false, HttpStatus.BAD_REQUEST, 2419, "해당 문제는 실무 테스트에서 사용 중입니다."),

    //   2) 실무테스트
    EMPLOYMENT_JOBTEST_DELETE_CONFLICT(false, HttpStatus.CONFLICT, 2420, "이 실무테스트는 다른 곳에서 사용중이므로 삭제할 수 없습니다."),
    EMPLOYMENT_JOBTEST_DUPLICATE(false, HttpStatus.CONFLICT, 2421, "동일한 이름의 실무테스트가 이미 등록되어 있습니다."),
    EMPLOYMENT_INVALID_JOBTEST(false, HttpStatus.NOT_FOUND, 2422, "요청한 실무테스트를 찾을 수 없습니다."),
    EMPLOYMENT_INVALID_JOBTEST_QUESTION(false, HttpStatus.NOT_FOUND, 2423, "이 실무테스트에는 해당 문제가 등록되어 있지 않습니다."),
    EMPLOYMENT_JOBTEST_QUESTION_DUPLICATE(false, HttpStatus.CONFLICT, 2424, "이 실무테스트에는 해당 문제가 이미 등록되어 있습니다."),
    EMPLOYMENT_JOBTEST_UPDATE_CONFLICT(false, HttpStatus.CONFLICT, 2425, "이 실무테스트는 다른 곳에서 사용중이므로 수정할 수 없습니다."),

    //   3) 지원서별 실무테스트
    EMPLOYMENT_INVALID_ENTRY_CODE(false, HttpStatus.BAD_REQUEST, 2300, "입장 코드는 5자리 숫자여야 합니다."),
    EMPLOYMENT_ENTRY_CODE_DUPLICATE(false, HttpStatus.CONFLICT, 2301, "중복된 입장 코드입니다."),
    EMPLOYMENT_JOBTEST_INVALID_ENTRY_CODE(false, HttpStatus.NOT_FOUND, 2302, "입장 코드가 올바르지 않습니다."),
    EMPLOYMENT_ENTRY_CODE_GENERATION_FAILED(false, HttpStatus.BAD_REQUEST, 2303, "입장 코드를 생성할 수 없습니다."),
    EMPLOYMENT_INVALID_APPLICATION_JOBTEST(false, HttpStatus.NOT_FOUND, 2304, "존재하지 않는 지원서별 실무테스트입니다."),
    EMPLOYMENT_INVALID_TIME(false, HttpStatus.BAD_REQUEST, 2305, "시험 시간이 아닙니다."),
    EMPLOYMENT_APPLICATION_JOBTEST_ALREADY_EXISTS(false, HttpStatus.CONFLICT, 2306, "해당 지원서에 이미 실무테스트가 할당되어 있습니다."),
    EMPLOYMENT_APPLICATION_JOBTEST_ALREADY_COMPLETE(false, HttpStatus.BAD_REQUEST, 2307, "이미 제출된 테스트입니다."),

    //   4) 실무테스트 답안
    EMPLOYMENT_INVALID_JOBTEST_ANSWER(false, HttpStatus.NOT_FOUND,2440, "요청한 답안을 찾을 수 없습니다."),

    //   5) 채점 기준
    EMPLOYMENT_INVALID_QUESTION_GRADING_CRITERIA(false, HttpStatus.NOT_FOUND, 2450, "요청한 실무테스트 문제 채점 기준을 찾을 수 없습니다."),

    //   6) 채점 결과

    //   7) 평가 기준
    EMPLOYMENT_INVALID_EVALUATION_CRITERIA(false, HttpStatus.BAD_REQUEST, 2470, "평가 기준이 유효하지 않습니다."),
    EMPLOYMENT_JOBTEST_EVALUATION_CRITERIA_OVER_WEIGHT(false, HttpStatus.BAD_REQUEST, 2471, "평가 기준의 가중치는 0 ~ 1을 벗어날 수 없습니다."),

    //   8) 평가 결과
    EMPLOYMENT_INVALID_EVALUATION_RESULT(false, HttpStatus.NOT_FOUND, 2480, "요청한 평가 결과를 찾을 수 없습니다."),


    //   9)




    

    //  면접 - 2500 ~ 2599
    EMPLOYMENT_INTERVIEW_CRITERIA_NOT_FOUND(false, HttpStatus.NOT_FOUND, 2500, "존재하지 않는 면접 기준입니다."),

    EMPLOYMENT_INTERVIEW_CRITERIA_NO_CONTENT(false, HttpStatus.BAD_REQUEST, 2510, "내용을 입력하지 않았습니다."),
    EMPLOYMENT_INTERVIEW_CRITERIA_NO_DETAIL_CONTENT(false, HttpStatus.BAD_REQUEST, 2511, "상세 내용을 입력하지 않았습니다."),
    EMPLOYMENT_INTERVIEW_CRITERIA_DUPLICATE_CONTENT(false, HttpStatus.BAD_REQUEST, 2512, "이미 존재하는 내용입니다."),

    EMPLOYMENT_INTERVIEW_SHEET_NOT_FOUND(false, HttpStatus.BAD_REQUEST, 2520, "존재하지 않습니다."),
    EMPLOYMENT_INTERVIEW_SHEET_NO_NAME(false, HttpStatus.BAD_REQUEST, 2521, "이름을 입력하지 않았습니다."),
    EMPLOYMENT_INTERVIEW_SHEET_DUPLICATE_NAME(false, HttpStatus.CONFLICT, 2522, "중복된 이름이 존재합니다."),

    EMPLOYMENT_INTERVIEW_SHEET_ITEM_NOT_FOUND(false, HttpStatus.BAD_REQUEST, 2530, "존재하지 않는 항목입니다."),
    EMPLOYMENT_INTERVIEW_SHEET_ITEM_DUPLICATE(false, HttpStatus.BAD_REQUEST, 2531, "이미 존재하는 항목입니다."),

    EMPLOYMENT_INTERVIEW_NOT_FOUND(false, HttpStatus.BAD_REQUEST, 2540, "존재하지 않습니다."),
    EMPLOYMENT_INTERVIEW_NO_SHEET(false, HttpStatus.BAD_REQUEST, 2541, "평가표가 등록되지 않았습니다."),
    EMPLOYMENT_INTERVIEW_NO_DATE(false, HttpStatus.BAD_REQUEST, 2542, "날짜가 등록되지 않았습니다."),
    EMPLOYMENT_INTERVIEW_NO_ADDRESS(false, HttpStatus.BAD_REQUEST, 2543, "주소가 등록되지 않았습니다."),
    EMPLOYMENT_INTERVIEW_IMPOSSIBLE_TIME(false, HttpStatus.BAD_REQUEST, 2544, "불가능한 시간입니다."),
    EMPLOYMENT_INTERVIEW_AlREADY_EXIST(false, HttpStatus.BAD_REQUEST, 2545, "이미 면접 정보가 존재합니다."),

    EMPLOYMENT_INTERVIEW_SCORE_NOT_FOUND(false, HttpStatus.BAD_REQUEST, 2550, "존재하지 않습니다."),
    EMPLOYMENT_INTERVIEW_SCORE_NO_SCORE(false, HttpStatus.BAD_REQUEST, 2551, "점수를 입력하지 않았습니다."),
    EMPLOYMENT_INTERVIEW_SCORE_NO_REVIEW(false, HttpStatus.BAD_REQUEST, 2552, "평가를 입력하지 않았습니다."),
    EMPLOYMENT_INTERVIEW_SCORE_NO_ITEM(false, HttpStatus.BAD_REQUEST, 2553, "해당하는 평가 항목이 존재하지 않습니다."),
    EMPLOYMENT_INTERVIEW_SCORE_ALREADY_EXIST(false, HttpStatus.BAD_REQUEST, 2554, "이미 평가 점수가 등록되어 있습니다."),

    EMPLOYMENT_INTERVIEWER_ALREADY_EXIST(false, HttpStatus.BAD_REQUEST, 2560, "이미 해당 면접 담당자가 존재합니다."),
    EMPLOYMENT_INTERVIEWER_NOT_FOUND(false, HttpStatus.BAD_REQUEST, 2561, "해당 면접 담당자가 존재하지 않습니다."),
    EMPLOYMENT_INTERVIEWER_ALREADY_SCORE_INPUT(false, HttpStatus.BAD_REQUEST, 2562, "면접 담당자가 이미 점수를 입력하였습니다."),


    //  안내 메일 - 2600 ~ 2699
    EMPLOYMENT_MAIL_TEMPLATE_DUPLICATE_TITLE(false, HttpStatus.CONFLICT, 2600, "이름이 중복된 템플릿이 존재합니다."),
    EMPLOYMENT_MAIL_TEMPLATE_NO_TITLE(false, HttpStatus.BAD_REQUEST, 2601, "제목을 입력하지 않았습니다."),
    EMPLOYMENT_MAIL_TEMPLATE_NO_CONTENT(false, HttpStatus.BAD_REQUEST, 2602, "내용을 입력하지 않았습니다."),
    EMPLOYMENT_MAIL_TEMPLATE_NOT_FOUND(false, HttpStatus.BAD_REQUEST, 2603, "존재하지 않는 템플릿입니다."),

    EMPLOYMENT_MAIL_NOT_FOUND(false, HttpStatus.BAD_REQUEST, 2630, "요청한 메일을 찾을 수 없습니다."),
    EMPLOYMENT_MAIL_NO_CONTENT(false, HttpStatus.BAD_REQUEST, 2631, "메일의 내용을 입력하지 않았습니다."),
    EMPLOYMENT_MAIL_INADEQUATE_EMAIL(false, HttpStatus.CONFLICT, 2632, "유효하지 않은 형태의 이메일입니다."),
    EMPLOYMENT_MAIL_NO_TITLE(false, HttpStatus.BAD_REQUEST, 2633, "메일의 제목을 입력하지 않았습니다."),

    // 인증 2700 ~ 2799
    INVALID_REFRESH_TOKEN(false, HttpStatus.UNAUTHORIZED, 2700, "유효하지 않은 Refresh Token입니다."),
    MEMBER_EMPLOYEE_NUMBER_DUPLICATE(false, HttpStatus.CONFLICT, 2701, "중복된 사번입니다."),



    // 결재 오류 3000~ 3999
    APPROVAL_APPROVER_NOT_FOUND(false, HttpStatus.NOT_FOUND, 3001, "결재자 정보가 존재하지 않습니다."),
    APPROVAL_CATEGORY_ITEM_MISMATCH(false, HttpStatus.BAD_REQUEST, 3002, "항목이 결재 유형에 속하지 않습니다."),
    APPROVAL_DUPLICATE_APPROVER_ORDER(false, HttpStatus.BAD_REQUEST, 3003, "결재 순서가 중복되었습니다."),
    APPROVAL_NOT_FOUND(false, HttpStatus.NOT_FOUND, 3004, "결재 문서를 찾을 수 없습니다."),
    APPROVAL_CANCEL_TARGET_NOT_FOUND(false, HttpStatus.NOT_FOUND, 3005, "취소 대상 결재 문서를 찾을 수 없습니다."),
    APPROVAL_NO_APPROVER(false, HttpStatus.BAD_REQUEST, 3006, "결재자가 최소 1명 이상 필요합니다."),
    APPROVAL_CONTENT_ITEM_MISSING(false, HttpStatus.BAD_REQUEST, 3007, "결재 문서 항목이 누락되었습니다."),
    APPROVAL_ALREADY_APPROVED(false, HttpStatus.BAD_REQUEST, 3008, "이미 승인 완료된 문서입니다."),
    APPROVAL_ALREADY_REJECTED(false, HttpStatus.BAD_REQUEST, 3009, "이미 반려된 문서입니다."),
    APPROVAL_ALREADY_CANCELED(false, HttpStatus.BAD_REQUEST, 3010, "이미 결재 취소된 문서입니다."),
    APPROVAL_NOT_YOUR_TURN(false, HttpStatus.BAD_REQUEST, 3011, "결재 차례가 아닙니다."),
    APPROVAL_REJECT_REASON_REQUIRED(false, HttpStatus.BAD_REQUEST, 3012, "반려 사유가 필요합니다."),
    APPROVAL_ALREADY_PROCESSED(false, HttpStatus.BAD_REQUEST, 3013, "이미 처리된 결재입니다."),
    APPROVAL_LINE_NOT_FOUND(false, HttpStatus.BAD_REQUEST, 3014, "결재 라인이 존재하지 않습니다.\n관리자에게 문의하세요."),
    APPROVAL_CATEGORY_MISSING(false, HttpStatus.BAD_REQUEST, 3015, "결재 유형이 존재하지 않습니다."),
    APPROVAL_WRITER_MISSING(false, HttpStatus.BAD_REQUEST, 3016, "결재 요청자가 존재하지 않습니다.");


    private final boolean success;
    private final HttpStatus httpStatus;        // HTTP 상태 코드
    private final int code;                     // 비지니스 로직
    private final String message;
}
