DROP TABLE IF EXISTS application_response;
DROP TABLE IF EXISTS applicant;
DROP TABLE IF EXISTS application;
DROP TABLE IF EXISTS applicant_bookmark;

CREATE TABLE applicant
(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '지원자 ID',
    name VARCHAR(255) NOT NULL COMMENT '이름',
    phone VARCHAR(255) NOT NULL COMMENT '연락처',
    email VARCHAR(255) NOT NULL COMMENT '이메일',
    profile_url VARCHAR(255) NOT NULL COMMENT '사진',
    birth VARCHAR(255) NOT NULL COMMENT '생년월일',
    address VARCHAR(255) NOT NULL COMMENT '주소'
)
    COMMENT = '지원자 테이블';

CREATE TABLE application
(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '지원서 번호',
    recruitment_id INT NOT NULL COMMENT '채용 공고 id',
    created_at DATETIME NOT NULL COMMENT '작성일자' DEFAULT CURRENT_TIMESTAMP,
    status TINYINT NOT NULL COMMENT '처리상태: 0(서류제외대기중), 1(서류합격), 2(실무합격), 3(1차면접합격), 4(2차면접합격), 5(최종합격), 6(불합격)',
    applicant_id INT NOT NULL COMMENT '지원자 id',
    introduce_rating_result_id INT NULL COMMENT '자기소개서 평가 결과 id',
    interview_id INT NULL COMMENT '면접 id',
    updated_at DATETIME NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '수정시각',
    updated_by INT NULL COMMENT '수정자 (member_id)',

    FOREIGN KEY (recruitment_id) REFERENCES recruitment(id),
    FOREIGN KEY (applicant_id) REFERENCES applicant(id),
    FOREIGN KEY (introduce_rating_result_id) REFERENCES introduce_rating_result(id),
    FOREIGN KEY (interview_id) REFERENCES interview(id),
    FOREIGN KEY (updated_by) REFERENCES member(id)

);

CREATE TABLE application_response
(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '지원서 항목별 내용 번호',
    application_id INT NOT NULL COMMENT '지원서 번호',
    application_item_id INT NOT NULL COMMENT '공고별 지원서 항목 번호',
    content LONGTEXT NOT NULL COMMENT '지원자가 작성한 내용',

    FOREIGN KEY (application_id) REFERENCES application(id),
    FOREIGN KEY (application_item_id) REFERENCES application_item(id)
);

CREATE TABLE applicant_bookmark
(
    member_id INT NOT NULL COMMENT '사용자 ID',
    applicant_id INT NOT NULL COMMENT '지원자 ID',
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (applicant_id) REFERENCES applicant(id)
)
    COMMENT = '북마크 지원자 테이블';

