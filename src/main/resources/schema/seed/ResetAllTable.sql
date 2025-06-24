SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `answer`;
DROP TABLE IF EXISTS `applicant`;
DROP TABLE IF EXISTS `applicant_bookmark`;
DROP TABLE IF EXISTS `application`;
DROP TABLE IF EXISTS `application_item`;
DROP TABLE IF EXISTS `application_item_category`;
DROP TABLE IF EXISTS `application_job_test`;
DROP TABLE IF EXISTS `application_response`;
DROP TABLE IF EXISTS `approval`;
DROP TABLE IF EXISTS `approval_category`;
DROP TABLE IF EXISTS `approval_category_item`;
DROP TABLE IF EXISTS `approval_content`;
DROP TABLE IF EXISTS `approval_line`;
DROP TABLE IF EXISTS `attendance_category`;
DROP TABLE IF EXISTS `attendance_record`;
DROP TABLE IF EXISTS `department`;
DROP TABLE IF EXISTS `department_head`;
DROP TABLE IF EXISTS `dept_change_history`;
DROP TABLE IF EXISTS `grading_result`;
DROP TABLE IF EXISTS `header_menu_preferences`;
DROP TABLE IF EXISTS `interview`;
DROP TABLE IF EXISTS `interview_criteria`;
DROP TABLE IF EXISTS `interview_score`;
DROP TABLE IF EXISTS `interview_sheet`;
DROP TABLE IF EXISTS `interviewer`;
DROP TABLE IF EXISTS `introduce`;
DROP TABLE IF EXISTS `introduce_rating_result`;
DROP TABLE IF EXISTS `introduce_standard`;
DROP TABLE IF EXISTS `introduce_standard_item`;
DROP TABLE IF EXISTS `introduce_template`;
DROP TABLE IF EXISTS `introduce_template_item`;
DROP TABLE IF EXISTS `introduce_template_item_response`;
DROP TABLE IF EXISTS `job`;
DROP TABLE IF EXISTS `job_test`;
DROP TABLE IF EXISTS `job_test_evaluation_criteria`;
DROP TABLE IF EXISTS `job_test_evaluation_result`;
DROP TABLE IF EXISTS `job_test_question`;
DROP TABLE IF EXISTS `mail`;
DROP TABLE IF EXISTS `mail_template`;
DROP TABLE IF EXISTS `member`;
DROP TABLE IF EXISTS `member_edit`;
DROP TABLE IF EXISTS `position`;
DROP TABLE IF EXISTS `question`;
DROP TABLE IF EXISTS `question_grading_criteria`;
DROP TABLE IF EXISTS `question_option`;
DROP TABLE IF EXISTS `rank`;
DROP TABLE IF EXISTS `recruitment`;
DROP TABLE IF EXISTS `recruitment_process`;
DROP TABLE IF EXISTS `recruitment_request`;
DROP TABLE IF EXISTS `recruitment_template`;
DROP TABLE IF EXISTS `recruitment_template_copy`;
DROP TABLE IF EXISTS `recruitment_template_item`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `side_menu_preferences`;
DROP TABLE IF EXISTS `schedule`;
DROP TABLE IF EXISTS `vacation`;
DROP TABLE IF EXISTS `welfare`;

SET FOREIGN_KEY_CHECKS = 1;



CREATE TABLE `answer` (
      `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '지원서별 문제 답안 id',
      `content`	LONGTEXT	NOT NULL COMMENT '답안 내용',
      `attempt`	INT	NOT NULL	DEFAULT 1 COMMENT '시도 번호',
      `is_correct`	TINYINT	NULL	DEFAULT 0 COMMENT '정답 여부',
      `score`	DOUBLE	NOT NULL	DEFAULT 0 COMMENT '점수',
      `application_job_test_id`	INT	NOT NULL COMMENT '지원서별 실무 테스트 id',
      `question_id`	INT	NOT NULL COMMENT '실무테스트 문제 id'
) COMMENT '지원서별 문제 답변';

CREATE TABLE `applicant` (
     `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
     `name`	VARCHAR(255)	NOT NULL COMMENT '이름',
     `phone`	VARCHAR(255)	NOT NULL COMMENT '연락처',
     `email`	VARCHAR(255)	NOT NULL COMMENT '이메일',
     `profile_url`	VARCHAR(255)	NULL COMMENT '사진',
     `birth`	VARCHAR(255)	NOT NULL COMMENT '생년월일',
     `address`	VARCHAR(255)	NOT NULL COMMENT '주소'
) COMMENT '지원자';

CREATE TABLE `application` (
       `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
       `recruitment_id`	INT	NOT NULL COMMENT '지원서 번호',
       `created_at`	DATETIME	DEFAULT CURRENT_TIMESTAMP NULL COMMENT '채용 공고 id',
       `status`	TINYINT	NOT NULL	DEFAULT 0 COMMENT '처리 상태',
       `applicant_id`	INT	NOT NULL COMMENT '지원자 id',
       `introduce_rating_result_id`	INT	NULL COMMENT '자기소개서 평가 결과표',
       `updated_at`	DATETIME	NULL COMMENT '수정 시각',
       `updated_by`	INT	NULL COMMENT '수정자 id'
) COMMENT '지원서';

CREATE TABLE `applicant_bookmark` (
        `member_id`	INT	NOT NULL,
        `applicant_id`	INT	NOT NULL
);

CREATE TABLE `application_item` (
        `id`	INT	NOT NULL	PRIMARY KEY AUTO_INCREMENT COMMENT '지원서 항목 번호',
        `is_required`	VARCHAR(4)	NOT NULL COMMENT '필수 여부',
        `application_item_category_id`	INT	NOT NULL COMMENT '항목 카테고리 ID',
        `recruitment_id`	INT	NOT NULL COMMENT '채용 공고 ID'
) COMMENT '공고별 지원서 항목';

CREATE TABLE `application_item_category` (
         `id`	INT	NOT NULL	PRIMARY KEY AUTO_INCREMENT  COMMENT '항목 카테고리 번호',
         `name`	VARCHAR(255)	NOT NULL COMMENT '항목 이름',
         `input_type`	TINYINT	NOT NULL COMMENT '입력 형태',
         `display_order`	INT	NOT NULL COMMENT '표시 순서',
         `application_item_category_id`	INT	NULL COMMENT '상위 카테고리'
) COMMENT '지원서 항목 카테고리';

CREATE TABLE `application_job_test` (
    `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '지원서별 실무테스트 ID',
    `evaluator_comment`	LONGTEXT	NULL COMMENT '평가자 코멘트',
    `submitted_at`	DATETIME	NULL COMMENT '제출일',
    `grading_total_score`	DOUBLE	NOT NULL	DEFAULT 0 COMMENT '채점 점수',
    `evaluation_score`	DOUBLE	NOT NULL	DEFAULT 0 COMMENT '평가 점수',
    `grading_status`	TINYINT	NOT NULL	DEFAULT 0 COMMENT '채점 상태',
    `evaluation_status`	TINYINT	NOT NULL	DEFAULT 0 COMMENT '평가 상태',
    `entry_code`	VARCHAR(255)	NULL COMMENT '입장 코드',
    `application_id`	INT	NOT NULL COMMENT '지원서 번호',
    `job_test_id`	INT	NOT NULL COMMENT '실무 테스트 ID',
    `grading_member_id`	INT	NULL COMMENT '채점자',
    `evaluation_member_id`	INT	NULL COMMENT '평가자'
);

CREATE TABLE `application_response` (
    `id`	INT	NULL PRIMARY KEY AUTO_INCREMENT,
    `application_id`	INT	NOT NULL,
    `application_item_id`	INT	NOT NULL,
    `content`	LONGTEXT	NOT NULL
);

CREATE TABLE `approval` (
    `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `category_id`	INT	NOT NULL,
    `writer_id`	INT	NOT NULL,
    `created_at`	DATETIME DEFAULT CURRENT_TIMESTAMP	NOT NULL,
    `status`	TINYINT	NOT NULL	DEFAULT 0,
    `first_approver_id`	INT	NULL,
    `second_approver_id`	INT	NULL,
    `third_approver_id`	INT	NULL,
    `fourth_approver_id`	INT	NULL,
    `first_approved_at`	DATETIME	NULL,
    `second_approved_at`	DATETIME	NULL,
    `third_approved_at`	DATETIME	NULL,
    `fourth_approved_at`	DATETIME	NULL,
    `approval_id`	INT	NULL
);

CREATE TABLE `approval_category` (
     `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
     `approval_category_id`	INT	NULL,
     `name`	VARCHAR(255)	NOT NULL
);

CREATE TABLE `approval_category_item` (
      `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT,
      `category_id`	INT	NOT NULL,
      `name`	VARCHAR(255)	NOT NULL,
      `input_type`	TINYINT	NOT NULL
);

CREATE TABLE `approval_content` (
        `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
        `approval_id`	INT	NOT NULL,
        `item_id`	INT	NOT NULL,
        `content`	LONGTEXT	NOT NULL
);

CREATE TABLE `approval_line` (
     `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT,
     `step_order`	INT	NOT NULL,
     `approval_category_id`	INT	NOT NULL,
     `position_id`	INT	NOT NULL
);

CREATE TABLE `attendance_category` (
       `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
       `status`	TINYINT	NOT NULL,
       `created_at`	DATETIME DEFAULT CURRENT_TIMESTAMP	NOT NULL,
       `updated_at`	DATETIME	NULL
);

CREATE TABLE `attendance_record` (
     `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
     `member_id`	INT	NOT NULL,
     `attendance_category_id`	INT	NOT NULL,
     `record_time`	DATETIME	NOT NULL,
     `status`	TINYINT	NOT NULL,
     `created_at`	DATETIME DEFAULT CURRENT_TIMESTAMP	NOT NULL,
     `deleted_at`	DATETIME	NULL,
     `updated_at`	DATETIME	NULL
);

CREATE TABLE `department` (
      `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
      `name`	VARCHAR(255)	NOT NULL,
      `code`	VARCHAR(255)	NOT NULL,
      `created_at`	DATETIME DEFAULT CURRENT_TIMESTAMP	NOT NULL,
      `updated_at`	DATETIME	NULL,
      `is_active`	BOOLEAN	NOT NULL	DEFAULT true,
      `description`	TEXT	NULL,
      `role_id`	INT	NULL
);

CREATE TABLE `department_head` (
       `department_id`	INT	NOT NULL,
       `member_id`	INT	NOT NULL,
       `role_id`	INT	NULL
);

CREATE TABLE `dept_change_history` (
       `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT,
       `member_id`	INT	NOT NULL,
       `dept_name`	VARCHAR(255)	NOT NULL,
       `position_name`	VARCHAR(255)	NULL,
       `job_name`	VARCHAR(255)	NULL,
       `rank_name`	VARCHAR(255)	NOT NULL,
       `work_start_at`	DATETIME	NOT NULL,
       `work_end_at`	DATETIME	NULL
);

CREATE TABLE `grading_result` (
      `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
      `evaluator_comment`	LONGTEXT	NULL,
      `is_satisfied`	VARCHAR(4)	NOT NULL	DEFAULT 'N',
      `answer_id`	INT	NOT NULL,
      `question_grading_criteria_id`	INT	NOT NULL
);

CREATE TABLE `header_menu_preferences` (
       `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
       `menu_name`	VARCHAR(255)	NOT NULL,
       `order`	INT	NOT NULL,
       `member_id`	INT	NOT NULL
);

CREATE TABLE `interview` (
     `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
     `application_id`	INT	NOT NULL,
     `sheet_id`	INT	NOT NULL,
     `datetime`	DATETIME	NOT NULL,
     `address`	LONGTEXT	NOT NULL,
     `score`	DOUBLE	NULL
);

CREATE TABLE `interview_criteria` (
      `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
      `sheet_id`	INT	NOT NULL,
      `title`	VARCHAR(255)	NOT NULL,
      `content`	LONGTEXT	NOT NULL,
      `weight`	DOUBLE	NOT NULL,
      `is_deleted`	VARCHAR(4)	NOT NULL	DEFAULT 'N',
      `member_id`	INT	NOT NULL,
      `updated_at`	DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `interview_score` (
       `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
       `interviewer_id`	INT	NOT NULL,
       `criteria_id`	INT	NOT NULL,
       `score`	INT	NULL,
       `review`	LONGTEXT	NULL
);

CREATE TABLE `interview_sheet` (
       `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
       `name`	VARCHAR(255)	NOT NULL,
       `is_deleted`	VARCHAR(4)	NOT NULL	DEFAULT 'N',
       `member_id`	INT	NOT NULL,
       `updated_at`	DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `interviewer` (
                               `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
                               `interview_id`	INT	NOT NULL,
                               `member_id`	INT	NOT NULL,
                               `score`	DOUBLE	NULL,
                               `review`	LONGTEXT	NULL
);

CREATE TABLE `introduce` (
     `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
     `content`	VARCHAR(255)	NOT NULL,
     `introduce_template_id`	INT	NOT NULL,
     `applicant_id`	INT	NOT NULL,
     `application_id`	INT	NOT NULL
);

CREATE TABLE `introduce_rating_result` (
       `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
       `content`	LONGTEXT	 NULL,
       `rating_score`	INT	 NULL,
       `updated_at`	DATETIME	NULL,
       `updated_by`	INT	NULL,
       `member_id`	INT	NOT NULL,
       `introduce_standard_id`	INT	NOT NULL,
       `introduce_id`	INT	NOT NULL
);

CREATE TABLE `introduce_standard` (
      `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
      `content`	VARCHAR(255)	NOT NULL,
      `member_id`	INT	NOT NULL
);

CREATE TABLE `introduce_standard_item` (
       `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
       `content`	VARCHAR(255)	NOT NULL,
       `member_id`	INT	NOT NULL,
       `introduce_standard_id`	INT	NULL
);

CREATE TABLE `introduce_template` (
      `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
      `title`	VARCHAR(255)	NOT NULL,
      `member_id`	INT	NOT NULL
);

CREATE TABLE `introduce_template_item` (
       `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
       `title`	VARCHAR(255)	NOT NULL,
       `member_id`	INT	NOT NULL,
       `introduce_template_id`	INT NULL
);

CREATE TABLE `introduce_template_item_response` (
    `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
    `introduce_id`	INT	NOT NULL,
    `introduce_template_item_id`	INT	NOT NULL,
    `content`	LONGTEXT	NOT NULL
);

CREATE TABLE `job` (
   `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
   `name`	VARCHAR(255)	NOT NULL,
   `code`	VARCHAR(255)	NOT NULL,
   `is_active`	BOOLEAN	NOT NULL	DEFAULT true,
   `created_at`	DATETIME DEFAULT CURRENT_TIMESTAMP	NOT NULL,
   `updated_at`	DATETIME	NULL ,
   `description`	TEXT	NULL,
   `role_id`	INT	NULL
);

CREATE TABLE `job_test` (
    `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
    `title`	VARCHAR(255)	NOT NULL,
    `difficulty`	TINYINT	NOT NULL,
    `test_time`	INT	NOT NULL,
    `started_at`	DATETIME	NULL,
    `ended_at`	DATETIME	NULL,
    `created_at`	DATETIME DEFAULT CURRENT_TIMESTAMP	NOT NULL,
    `updated_at`	DATETIME	NULL,
    `created_member_id`	INT	NOT NULL,
    `updated_member_id`	INT	NULL
);

CREATE TABLE `job_test_evaluation_criteria` (
    `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
    `content`	LONGTEXT	NOT NULL,
    `detail_content`	LONGTEXT	NULL,
    `score_weight`	DOUBLE	NOT NULL,
    `job_test_id`	INT	NOT NULL
);

CREATE TABLE `job_test_evaluation_result` (
      `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
      `evaluator_comment`	LONGTEXT	NULL,
      `score`	DOUBLE	NOT NULL	DEFAULT 0,
      `application_job_test_id`	INT	NOT NULL,
      `job_test_evaluation_criteria_id`	INT	NOT NULL
);

CREATE TABLE `job_test_question` (
     `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
     `score`	INT	NOT NULL,
     `option_number`	INT	NOT NULL,
     `job_test_id`	INT	NOT NULL,
     `question_id`	INT	NOT NULL
);

CREATE TABLE `mail` (
    `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
    `applicant_id`	INT	NULL,
    `email`	VARCHAR(255)	NOT NULL,
    `title`	VARCHAR(255)	NOT NULL,
    `content`	LONGTEXT	NOT NULL,
    `sender_id`	INT	NOT NULL,
    `sended_at`	DATETIME	NOT NULL
);

CREATE TABLE `mail_template` (
     `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
     `title`	LONGTEXT	NOT NULL,
     `content`	LONGTEXT	NOT NULL,
     `is_deleted`	VARCHAR(4)	NOT NULL,
     `member_id`	INT	NULL,
     `updated_at`	DATETIME	NULL
);

CREATE TABLE `member` (
      `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
      `password`	VARCHAR(255)	NOT NULL,
      `employee_number`	INT	NOT NULL,
      `name`	VARCHAR(255)	NOT NULL,
      `birth`	VARCHAR(255)	NULL,
      `phone`	VARCHAR(255)	NOT NULL,
      `picture_url`	VARCHAR(255)	NOT NULL,
      `email`	VARCHAR(255)	NOT NULL,
      `address`	VARCHAR(255)	NOT NULL,
      `vacation_count`	INT	NOT NULL	DEFAULT 0,
      `hire_at`	DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `resign_at`	DATETIME	NULL,
      `created_at`	DATETIME DEFAULT CURRENT_TIMESTAMP	NOT NULL,
      `created_member_id`	INT	NULL,
      `deleted_member_id`	INT	NULL,
      `updated_at`	DATETIME	NULL ,
      `updated_member_id`	INT	NULL,
      `last_login_at`	DATETIME	NULL,
      `status`	TINYINT	NOT NULL	DEFAULT 0,
      `department_id`	INT	NULL,
      `position_id`	INT	NULL,
      `job_id`	INT	NULL,
      `rank_id`	INT	NULL
);

CREATE TABLE `member_edit` (
       `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
       `member_id`	INT	NOT NULL,
       `reviewer_id`	INT	NULL,
       `target_field`	VARCHAR(255)	NOT NULL,
       `original_value`	VARCHAR(255)	NOT NULL,
       `requested_value`	VARCHAR(255)	NOT NULL,
       `field_type`	TINYINT	NOT NULL,
       `status`	TINYINT	NOT NULL,
       `requested_at`	DATETIME	NOT NULL,
       `reason`	TEXT	NOT NULL,
       `updated_at`	DATETIME	NULL,
       `reject_reason`	TEXT	NULL
);

CREATE TABLE `position` (
    `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
    `name`	VARCHAR(255)	NOT NULL,
    `is_active`	BOOLEAN	NOT NULL	DEFAULT true,
    `created_at`	DATETIME DEFAULT CURRENT_TIMESTAMP	NOT NULL,
    `updated_at`	DATETIME	NULL,
    `description`	TEXT	NULL,
    `role_id`	INT	NULL,
    `code`	VARCHAR(255)	NOT NULL
);

CREATE TABLE `question` (
    `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
    `content`	LONGTEXT	NOT NULL,
    `detail_content`	LONGTEXT	NULL,
    `type`	TINYINT	NOT NULL,
    `difficulty`	TINYINT	NOT NULL,
    `answer`	VARCHAR(255)	NULL,
    `created_at`	DATETIME DEFAULT CURRENT_TIMESTAMP	NOT NULL,
    `updated_at`	DATETIME	NULL,
    `created_member_id`	INT	NOT NULL,
    `updated_member_id`	INT	NULL
);

CREATE TABLE `question_grading_criteria` (
     `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
     `content`	LONGTEXT	NOT NULL,
     `detail_content`	LONGTEXT	NULL,
     `score_weight`	DOUBLE	NOT NULL,
     `question_id`	INT	NOT NULL
);

CREATE TABLE `question_option` (
   `id`	INt	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
   `option_number`	INT	NOT NULL,
   `content`	VARCHAR(255)	NOT NULL,
   `question_id`	INT	NOT NULL
);

CREATE TABLE `rank` (
    `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
    `name`	VARCHAR(255)	NOT NULL,
    `code`	VARCHAR(255)	NOT NULL,
    `is_active`	BOOLEAN	NOT NULL	DEFAULT true,
    `created_at`	DATETIME DEFAULT CURRENT_TIMESTAMP	NOT NULL,
    `updated_at`	DATETIME	NULL,
    `salary_band`	INT	NULL,
    `role_id`	INT	NULL
);

CREATE TABLE `recruitment` (
   `id`	INT	NOT NULL	PRIMARY KEY AUTO_INCREMENT,
   `title`	VARCHAR(255)	NOT NULL,
   `content`	LONGTEXT	NULL,
   `recruit_type`	TINYINT	NOT NULL,
   `status`	TINYINT	NOT NULL	DEFAULT 0,
   `image_url`	VARCHAR(255)	NULL,
   `started_at`	DATETIME	NOT NULL,
   `ended_at`	DATETIME	NOT NULL,
   `created_at`	DATETIME 	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
   `updated_at`	DATETIME	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
   `deleted_at`	DATETIME	NULL,
   `member_id`	INT	NOT NULL,
   `recruitment_template_id`	INT	NULL,
   `introduce_template_id`	INT	NOT NULL,
   `recruitment_request_id`	INT	NULL UNIQUE
);

CREATE TABLE `recruitment_process` (
   `id`	INT	NOT NULL	PRIMARY KEY AUTO_INCREMENT ,
   `step_type`	TINYINT	NOT NULL,
   `display_order`	INT	NOT NULL,
   `recruitment_id`	INT	NOT NULL
);

CREATE TABLE `recruitment_request` (
   `id`	INT	NOT NULL	PRIMARY KEY AUTO_INCREMENT ,
   `headcount`	INT	NOT NULL,
   `started_at`	DATETIME	NOT NULL,
   `ended_at`	DATETIME	NOT NULL,
   `qualification`	VARCHAR(255)	NULL,
   `preference`	VARCHAR(255)	NULL,
   `responsibility`	VARCHAR(255)	NULL,
   `employment_type`	VARCHAR(255)	NOT NULL,
   `work_location`	VARCHAR(255)	NOT NULL,
   `created_at`	DATETIME	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
   `member_id`	INT	NOT NULL,
   `department_id`	INT	NOT NULL,
   `job_id`	INT	NOT NULL
);

CREATE TABLE `recruitment_template` (
    `id`	INT	NOT NULL	PRIMARY KEY AUTO_INCREMENT ,
    `name`	VARCHAR(255)	NOT NULL,
    `created_at`	DATETIME	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
    `updated_at`	DATETIME	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
    `is_deleted`	VARCHAR(4)	NOT NULL	DEFAULT 'N',
    `member_id`	INT	NOT NULL
);

CREATE TABLE `role` (
    `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
    `code`	VARCHAR(255)	NOT NULL,
    `name`	VARCHAR(255)	NOT NULL,
    `description`	TEXT	NOT NULL,
    `role_type`	TINYINT	NOT NULL,
    `created_at`	DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`	DATETIME	NULL,
    `deleted_at`	DATETIME	NULL
);

CREATE TABLE `schedule` (
    `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
    `member_id`	INT	NOT NULL,
    `date`	DATETIME	NOT NULL,
    `reason`	VARCHAR(255)	NULL
);

CREATE TABLE `side_menu_preferences` (
     `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT ,
     `menu_name`	VARCHAR(255)	NOT NULL,
     `order`	INT	NOT NULL,
     `favorite`	VARCHAR(4)	NULL,
     `member_id`	INT	NOT NULL,
     `header_menu_id`	INT	NOT NULL
);

CREATE TABLE `vacation` (
    `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `member_id`	INT	NOT NULL,
    `date`	DATETIME	NOT NULL,
    `reason`	VARCHAR(255)	NULL,
    `Field`	VARCHAR(5)	NULL,
    `acceptor_id`	INT	NULL
);

CREATE TABLE `welfare` (
   `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT,
   `title`	VARCHAR(255)	NOT NULL,
   `content`	VARCHAR(255)	NOT NULL,
   `picture_url`	VARCHAR(255)	NOT NULL
);



-- FOREIGN KEY 설정
ALTER TABLE answer
    ADD CONSTRAINT fk_answer_application_job_test FOREIGN KEY (application_job_test_id) REFERENCES application_job_test(id),
    ADD CONSTRAINT fk_answer_question FOREIGN KEY (question_id) REFERENCES question(id);

ALTER TABLE applicant_bookmark
    ADD CONSTRAINT fk_applicant_bookmark_member FOREIGN KEY (member_id) REFERENCES member(id),
    ADD CONSTRAINT fk_applicant_bookmark_applicant FOREIGN KEY (applicant_id) REFERENCES applicant(id);

ALTER TABLE application
    ADD CONSTRAINT fk_application_recruitment FOREIGN KEY (recruitment_id) REFERENCES recruitment(id),
    ADD CONSTRAINT fk_application_applicant FOREIGN KEY (applicant_id) REFERENCES applicant(id),
    ADD CONSTRAINT fk_application_introduce_rating_result FOREIGN KEY (introduce_rating_result_id) REFERENCES introduce_rating_result(id),
    ADD CONSTRAINT fk_application_updated_by FOREIGN KEY (updated_by) REFERENCES member(id);

ALTER TABLE application_item
    ADD CONSTRAINT fk_application_item_category FOREIGN KEY (application_item_category_id) REFERENCES application_item_category(id),
    ADD CONSTRAINT fk_application_item_recruitment FOREIGN KEY (recruitment_id) REFERENCES recruitment(id);

ALTER TABLE application_item_category
    ADD CONSTRAINT fk_app_item_cat_parent FOREIGN KEY (application_item_category_id) REFERENCES application_item_category(id);

ALTER TABLE application_job_test
    ADD CONSTRAINT fk_app_job_test_application FOREIGN KEY (application_id) REFERENCES application(id),
    ADD CONSTRAINT fk_app_job_test_job_test FOREIGN KEY (job_test_id) REFERENCES job_test(id),
    ADD CONSTRAINT fk_app_job_test_member FOREIGN KEY (evaluation_member_id) REFERENCES member(id),
    ADD CONSTRAINT fk_app_job_test_grading_member FOREIGN KEY (grading_member_id) REFERENCES member(id);

ALTER TABLE application_response
    ADD CONSTRAINT fk_app_response_application FOREIGN KEY (application_id) REFERENCES application(id),
    ADD CONSTRAINT fk_app_response_item FOREIGN KEY (application_item_id) REFERENCES application_item(id);

ALTER TABLE approval
    ADD CONSTRAINT fk_approval_category FOREIGN KEY (category_id) REFERENCES approval_category(id),
    ADD CONSTRAINT fk_approval_writer FOREIGN KEY (writer_id) REFERENCES member(id),
    ADD CONSTRAINT fk_approval_first FOREIGN KEY (first_approver_id) REFERENCES member(id),
    ADD CONSTRAINT fk_approval_second FOREIGN KEY (second_approver_id) REFERENCES member(id),
    ADD CONSTRAINT fk_approval_third FOREIGN KEY (third_approver_id) REFERENCES member(id),
    ADD CONSTRAINT fk_approval_fourth FOREIGN KEY (fourth_approver_id) REFERENCES member(id),
    ADD CONSTRAINT fk_approval_approval FOREIGN KEY (approval_id) REFERENCES approval(id);

ALTER TABLE approval_category
    ADD CONSTRAINT fk_approval_category_parent FOREIGN KEY (approval_category_id) REFERENCES approval_category(id);

ALTER TABLE approval_category_item
    ADD CONSTRAINT fk_approval_cat_item_category FOREIGN KEY (category_id) REFERENCES approval_category(id);

ALTER TABLE approval_content
    ADD CONSTRAINT fk_approval_content_approval FOREIGN KEY (approval_id) REFERENCES approval(id),
    ADD CONSTRAINT fk_approval_content_item FOREIGN KEY (item_id) REFERENCES approval_category_item(id);

ALTER TABLE approval_line
    ADD CONSTRAINT fk_approval_line_category FOREIGN KEY (approval_category_id) REFERENCES approval_category(id),
    ADD CONSTRAINT fk_approval_line_position FOREIGN KEY (position_id) REFERENCES `position`(id);

ALTER TABLE attendance_record
    ADD CONSTRAINT fk_attendance_record_member FOREIGN KEY (member_id) REFERENCES member(id),
    ADD CONSTRAINT fk_attendance_record_category FOREIGN KEY (attendance_category_id) REFERENCES attendance_category(id);

ALTER TABLE department
    ADD CONSTRAINT fk_department_role FOREIGN KEY (role_id) REFERENCES role(id);

ALTER TABLE department_head
    ADD CONSTRAINT fk_department_head_department FOREIGN KEY (department_id) REFERENCES department(id),
    ADD CONSTRAINT fk_department_head_member FOREIGN KEY (member_id) REFERENCES member(id),
    ADD CONSTRAINT fk_department_head_role FOREIGN KEY (role_id) REFERENCES role(id);

ALTER TABLE dept_change_history
    ADD CONSTRAINT fk_dept_change_member FOREIGN KEY (member_id) REFERENCES member(id);

ALTER TABLE grading_result
    ADD CONSTRAINT fk_grading_result_answer FOREIGN KEY (answer_id) REFERENCES answer(id),
    ADD CONSTRAINT fk_grading_result_criteria FOREIGN KEY (question_grading_criteria_id) REFERENCES question_grading_criteria(id);

ALTER TABLE header_menu_preferences
    ADD CONSTRAINT fk_header_menu_member FOREIGN KEY (member_id) REFERENCES member(id);

ALTER TABLE interview
    ADD CONSTRAINT fk_interview_application FOREIGN KEY (application_id) REFERENCES application(id),
    ADD CONSTRAINT fk_interview_sheet FOREIGN KEY (sheet_id) REFERENCES interview_sheet(id);

ALTER TABLE interview_criteria
    ADD CONSTRAINT fk_interview_criteria_sheet FOREIGN KEY (sheet_id) REFERENCES interview_sheet(id),
    ADD CONSTRAINT fk_interview_criteria_member FOREIGN KEY (member_id) REFERENCES member(id);

ALTER TABLE interview_score
    ADD CONSTRAINT fk_interview_score_interviewer FOREIGN KEY (interviewer_id) REFERENCES interviewer(id),
    ADD CONSTRAINT fk_interview_score_criteria FOREIGN KEY (criteria_id) REFERENCES interview_criteria(id);

ALTER TABLE interview_sheet
    ADD CONSTRAINT fk_interview_sheet_member FOREIGN KEY (member_id) REFERENCES member(id);

ALTER TABLE interviewer
    ADD CONSTRAINT fk_interviewer_interview FOREIGN KEY (interview_id) REFERENCES interview(id),
    ADD CONSTRAINT fk_member_member FOREIGN KEY (member_id) REFERENCES member(id);

ALTER TABLE introduce
    ADD CONSTRAINT fk_introduce_template FOREIGN KEY (introduce_template_id) REFERENCES introduce_template(id),
    ADD CONSTRAINT fk_introduce_applicant FOREIGN KEY (applicant_id) REFERENCES applicant(id),
    ADD CONSTRAINT fk_introduce_application FOREIGN KEY (application_id) REFERENCES application(id);

ALTER TABLE introduce_rating_result
    ADD CONSTRAINT fk_rating_result_member FOREIGN KEY (member_id) REFERENCES member(id),
    ADD CONSTRAINT fk_rating_result_standard FOREIGN KEY (introduce_standard_id) REFERENCES introduce_standard(id),
    ADD CONSTRAINT fk_rating_result_introduce FOREIGN KEY (introduce_id) REFERENCES introduce(id),
    ADD CONSTRAINT fk_rating_result_updated_by FOREIGN KEY (updated_by) REFERENCES member(id);

ALTER TABLE introduce_standard
    ADD CONSTRAINT fk_standard_member FOREIGN KEY (member_id) REFERENCES member(id);

ALTER TABLE introduce_standard_item
    ADD CONSTRAINT fk_standard_item_member FOREIGN KEY (member_id) REFERENCES member(id),
    ADD CONSTRAINT fk_standard_item_standard FOREIGN KEY (introduce_standard_id) REFERENCES introduce_standard(id);

ALTER TABLE introduce_template
    ADD CONSTRAINT fk_template_member FOREIGN KEY (member_id) REFERENCES member(id);

ALTER TABLE introduce_template_item
    ADD CONSTRAINT fk_template_item_member FOREIGN KEY (member_id) REFERENCES member(id),
    ADD CONSTRAINT fk_template_item_template FOREIGN KEY (introduce_template_id) REFERENCES introduce_template(id);

ALTER TABLE introduce_template_item_response
    ADD CONSTRAINT fk_item_response_introduce FOREIGN KEY (introduce_id) REFERENCES introduce(id),
    ADD CONSTRAINT fk_item_response_item FOREIGN KEY (introduce_template_item_id) REFERENCES introduce_template_item(id);

ALTER TABLE job
    ADD CONSTRAINT fk_job_role FOREIGN KEY (role_id) REFERENCES role(id);

ALTER TABLE job_test
    ADD CONSTRAINT fk_job_test_creator FOREIGN KEY (created_member_id) REFERENCES member(id),
    ADD CONSTRAINT fk_job_test_updater FOREIGN KEY (updated_member_id) REFERENCES member(id);

ALTER TABLE job_test_evaluation_criteria
    ADD CONSTRAINT fk_test_eval_criteria_test FOREIGN KEY (job_test_id) REFERENCES job_test(id);

ALTER TABLE job_test_evaluation_result
    ADD CONSTRAINT fk_test_eval_result_test FOREIGN KEY (application_job_test_id) REFERENCES application_job_test(id),
    ADD CONSTRAINT fk_test_eval_result_criteria FOREIGN KEY (job_test_evaluation_criteria_id) REFERENCES job_test_evaluation_criteria(id);

ALTER TABLE job_test_question
    ADD CONSTRAINT fk_test_question_test FOREIGN KEY (job_test_id) REFERENCES job_test(id),
    ADD CONSTRAINT fk_test_question_question FOREIGN KEY (question_id) REFERENCES question(id);

ALTER TABLE mail
    ADD CONSTRAINT fk_mail_applicant FOREIGN KEY (applicant_id) REFERENCES applicant(id),
    ADD CONSTRAINT fk_mail_sender FOREIGN KEY (sender_id) REFERENCES member(id);

ALTER TABLE mail_template
    ADD CONSTRAINT fk_mail_template_member FOREIGN KEY (member_id) REFERENCES member(id);

ALTER TABLE member
    ADD CONSTRAINT fk_member_department FOREIGN KEY (department_id) REFERENCES department(id),
    ADD CONSTRAINT fk_member_position FOREIGN KEY (position_id) REFERENCES `position`(id),
    ADD CONSTRAINT fk_member_job FOREIGN KEY (job_id) REFERENCES job(id),
    ADD CONSTRAINT fk_member_rank FOREIGN KEY (rank_id) REFERENCES `rank`(id);

ALTER TABLE member_edit
    ADD CONSTRAINT fk_member_edit_member FOREIGN KEY (member_id) REFERENCES member(id),
    ADD CONSTRAINT fk_member_edit_reviewer FOREIGN KEY (reviewer_id) REFERENCES member(id);

ALTER TABLE question
    ADD CONSTRAINT fk_question_creator FOREIGN KEY (created_member_id) REFERENCES member(id),
    ADD CONSTRAINT fk_question_updater FOREIGN KEY (updated_member_id) REFERENCES member(id);

ALTER TABLE question_grading_criteria
    ADD CONSTRAINT fk_qg_criteria_question FOREIGN KEY (question_id) REFERENCES question(id);

ALTER TABLE question_option
    ADD CONSTRAINT fk_q_option_question FOREIGN KEY (question_id) REFERENCES question(id);

ALTER TABLE recruitment
    ADD CONSTRAINT fk_recruitment_member FOREIGN KEY (member_id) REFERENCES member(id),
    ADD CONSTRAINT fk_recruitment_template FOREIGN KEY (recruitment_template_id) REFERENCES recruitment_template(id),
    ADD CONSTRAINT fk_recruitment_introduce_template FOREIGN KEY (introduce_template_id) REFERENCES introduce_template(id),
    ADD CONSTRAINT fk_recruitment_request FOREIGN KEY (recruitment_request_id) REFERENCES recruitment_request(id);

ALTER TABLE recruitment_process
    ADD CONSTRAINT fk_recruitment_process_recruitment FOREIGN KEY (recruitment_id) REFERENCES recruitment(id);

ALTER TABLE recruitment_request
    ADD CONSTRAINT fk_request_member FOREIGN KEY (member_id) REFERENCES member(id),
    ADD CONSTRAINT fk_request_dept FOREIGN KEY (department_id) REFERENCES department(id),
    ADD CONSTRAINT fk_request_job FOREIGN KEY (job_id) REFERENCES job(id);

ALTER TABLE recruitment_template
    ADD CONSTRAINT fk_template_creator FOREIGN KEY (member_id) REFERENCES member(id);

ALTER TABLE schedule
    ADD CONSTRAINT fk_schedule_member FOREIGN KEY (member_id) REFERENCES member(id);

ALTER TABLE side_menu_preferences
    ADD CONSTRAINT fk_side_menu_member FOREIGN KEY (member_id) REFERENCES member(id),
    ADD CONSTRAINT fk_side_menu_header FOREIGN KEY (header_menu_id) REFERENCES header_menu_preferences(id);

ALTER TABLE vacation
    ADD CONSTRAINT fk_vacation_member FOREIGN KEY (member_id) REFERENCES member(id),
    ADD CONSTRAINT fk_vacation_acceptor FOREIGN KEY (acceptor_id) REFERENCES member(id);
