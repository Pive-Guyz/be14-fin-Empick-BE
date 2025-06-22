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
                             `profile_url`	VARCHAR(255)	NOT NULL COMMENT '사진',
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
                          `hire_at`	DATETIME	NOT NULL,
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



INSERT INTO role (id, code, name, description, role_type) VALUES
                                                              (1, 'ROLE_HR_ACCESS', '인사팀기능접근', '인사팀 전용 기능에 접근 가능', 0),
                                                              (2, 'ROLE_RECRUITMENT_PLAN_EDITOR', '채용계획서작성', '채용계획서 작성 기능에 접근 가능', 3),
                                                              (3, 'ROLE_APPROVAL_PROCESSOR', '결재처리권한', '결재처리권한을 갖을수 있음', 3),
                                                              (4, 'ROLE_RECRUITMENT_OPERATOR', '채용진행자', '채용 관련한 모든 기능에 접근 가능', 2),
                                                              (5, 'ROLE_USER', '일반사용자', '로그인을 위한 권한', 1),
                                                              (6, 'ROLE_INTERVIEWER', '면접관', '면접 평가 및 기록 권한이 있습니다.', 4),
                                                              (7, 'ROLE_ADMIN', '시스템관리자', '시스템 전반의 설정을 담당합니다.', 0),
                                                              (8, 'ROLE_DEVELOPER', '개발자', '개발 업무를 수행하는 권한입니다.', 0),
                                                              (9, 'ROLE_DESIGNER', '디자이너', 'UI/UX 또는 그래픽 관련 권한입니다.', 0),
                                                              (10, 'ROLE_INTERN', '인턴', '한시적인 인턴 직무 권한입니다.', 0);

INSERT INTO department (id, name, code, description, role_id) VALUES
                                                                  (1, '경영지원팀', 'DPT_MNG', '회사의 전반적인 운영과 지원을 담당합니다.', NULL),
                                                                  (2, '인사팀', 'DPT_HR', '채용 및 인사 관리를 담당합니다.', 1),
                                                                  (3, '재무팀', 'DPT_FIN', '예산, 회계 및 자산 관리를 담당합니다.', NULL),
                                                                  (4, '영업팀', 'DPT_SALES', '고객 관리 및 매출 증대를 담당합니다.', NULL),
                                                                  (5, '개발팀', 'DPT_DEV1', '웹/앱 개발 및 유지보수를 담당합니다.', NULL);

INSERT INTO `position` (id, `name`, `is_active`, `created_at`, `code`) VALUES
                                                                           (1, '팀원', 1, NOW(), 'POS_MEMBER'),
                                                                           (2, '팀장', 1, NOW(), 'POS_TEAM_LEADER'),
                                                                           (3, '부서장', 1, NOW(), 'POS_DEPT_HEAD'),
                                                                           (4, '본부장', 1, NOW(), 'POS_EXEC_DIRECTOR'),
                                                                           (5, 'CEO', 1, NOW(), 'POS_CEO');

INSERT INTO job (id, name, code, description) VALUES
                                                  (1, '프론트엔드 개발자', 'JOB_FE_DEV', '웹 UI 및 사용자 인터페이스 개발'),
                                                  (2, '백엔드 개발자', 'JOB_BE_DEV', '서버 및 API 개발'),
                                                  (3, '데브옵스 엔지니어', 'JOB_DEVOPS', '배포 및 시스템 운영 자동화'),
                                                  (4, '데이터 엔지니어', 'JOB_DATA_ENG', '데이터 수집 및 파이프라인 구축'),
                                                  (5, 'AI 엔지니어', 'JOB_AI', '머신러닝 모델 개발'),
                                                  (6, 'QA 엔지니어', 'JOB_QA', '테스트 및 품질 보증'),
                                                  (7, 'PM (프로젝트 매니저)', 'JOB_PM', '프로젝트 기획 및 일정 관리'),
                                                  (8, '디자이너', 'JOB_DESIGN', 'UI/UX 및 시각 디자인'),
                                                  (9, '마케팅 매니저', 'JOB_MKT', '시장 조사 및 마케팅 전략'),
                                                  (10, '기획자', 'JOB_PLANNER', '서비스 및 기능 기획');

INSERT INTO `rank` (id, `name`, `code`, `is_active`, `created_at`, `salary_band`, `role_id`) VALUES
                                                                                                 (1, '사원', 'RANK_JUNIOR', 1, NOW(), 1,5),
                                                                                                 (2, '주임', 'RANK_ASSISTANT', 1, NOW(), 2,5),
                                                                                                 (3, '대리', 'RANK_ASSOCIATE', 1, NOW(), 3,5),
                                                                                                 (4, '과장', 'RANK_SENIOR_ASSOCIATE', 1, NOW(), 4,5),
                                                                                                 (5, '차장', 'RANK_MANAGER', 1, NOW(), 5,5),
                                                                                                 (6, '부장', 'RANK_SENIOR_MANAGER', 1, NOW(), 6,5),
                                                                                                 (7, '이사', 'RANK_DIRECTOR', 1, NOW(), 7,5),
                                                                                                 (8, '상무', 'RANK_EXECUTIVE_DIRECTOR', 1, NOW(), 8,5),
                                                                                                 (9, '전무', 'RANK_VP', 1, NOW(), 9,5),
                                                                                                 (10, '사장', 'RANK_PRESIDENT', 1, NOW(), 10,5);



INSERT INTO member (
    id, `password`, `employee_number`, `name`, `birth`, `phone`, `picture_url`, `email`, `address`,
    `vacation_count`, `hire_at`, `resign_at`, `created_at`, `created_member_id`, `deleted_member_id`,
    `updated_at`, `updated_member_id`, `last_login_at`, `status`, `department_id`, `position_id`, `job_id`, `rank_id`
) VALUES
      (1, 'password123', 1001, '김철수', '1993-08-26', '010-8412-6774', 'https://picsum.photos/seed/1/200', 'user1@company.com', '서울시 강남구 테헤란로 5길',
       0, '2022-10-10', NULL, NOW(), NULL, NULL, NULL, NULL, NULL, 1, 1, 1, 8, 5),
      (2, 'password123', 1002, '이영희', '1993-07-18', '010-2139-8474', 'https://picsum.photos/seed/2/200', 'user2@company.com', '서울시 강남구 테헤란로 10길',
       0, '2023-01-22', NULL, NOW(), NULL, NULL, NULL, NULL, NULL, 1, 1, 2, 9, 7),
      (3, 'password123', 1003, '박민준', '1991-04-15', '010-5523-1133', 'https://picsum.photos/seed/3/200', 'user3@company.com', '서울시 서초구 반포대로 12',
       0, '2021-06-01', NULL, NOW(), NULL, NULL, NULL, NULL, NULL, 1, 1, 3, 4, 3),
      (4, 'password123', 1004, '최지우', '1995-09-07', '010-8745-9981', 'https://picsum.photos/seed/4/200', 'user4@company.com', '서울시 용산구 녹사평대로 42',
       0, '2020-03-15', NULL, NOW(), NULL, NULL, NULL, NULL, NULL, 1, 2, 1, 6, 2),
      (5, 'password123', 1005, '정예린', '1994-11-23', '010-3391-2020', 'https://picsum.photos/seed/5/200', 'user5@company.com', '서울시 마포구 독막로 28',
       0, '2023-09-10', NULL, NOW(), NULL, NULL, NULL, NULL, NULL, 1, 2, 2, 3, 4),
      (6, 'password123', 1006, '이승현', '1993-02-18', '010-3344-5566', 'https://picsum.photos/seed/6/200', 'user6@company.com', '서울시 강동구 천호대로 990',
       0, '2021-02-11', NULL, NOW(), NULL, NULL, NULL, NULL, NULL, 1, 2, 3, 2, 3),
      (7, 'password123', 1007, '김하늘', '1996-07-25', '010-7788-9900', 'https://picsum.photos/seed/7/200', 'user7@company.com', '서울시 은평구 불광로 18',
       0, '2022-07-30', NULL, NOW(), NULL, NULL, NULL, NULL, NULL, 1, 3, 1, 7, 2),
      (8, 'password123', 1008, '장도윤', '1989-10-02', '010-1122-3344', 'https://picsum.photos/seed/8/200', 'user8@company.com', '서울시 송파구 백제고분로 84',
       0, '2019-10-14', NULL, NOW(), NULL, NULL, NULL, NULL, NULL, 1, 3, 2, 8, 1),
      (9, 'password123', 1009, '윤서진', '1992-12-11', '010-6677-8899', 'https://picsum.photos/seed/9/200', 'user9@company.com', '서울시 광진구 능동로 45',
       0, '2020-01-01', NULL, NOW(), NULL, NULL, NULL, NULL, NULL, 1, 3, 3, 1, 6),
      (10, 'password123', 1010, '백승우', '1990-05-27', '010-2233-4455', 'https://picsum.photos/seed/10/200', 'user10@company.com', '서울시 강서구 공항대로 318',
       0, '2018-06-05', NULL, NOW(), NULL, NULL, NULL, NULL, NULL, 1, 4, 1, 9, 7),
      (11, 'password123', 1011, '김서연', '1992-03-15', '010-1111-2222', 'https://picsum.photos/seed/11/200', 'user11@company.com', '서울시 강북구 솔샘로 12',
       0, '2021-04-25', NULL, NOW(), NULL, NULL, NULL, NULL, NULL, 1, 4, 2, 5, 3),
      (12, 'password123', 1012, '이도윤', '1988-11-02', '010-3333-4444', 'https://picsum.photos/seed/12/200', 'user12@company.com', '서울시 서대문구 연희로 24',
       0, '2020-09-17', NULL, NOW(), NULL, NULL, NULL, NULL, NULL, 1, 4, 3, 6, 4),
      (13, 'password123', 1013, '박예린', '1995-06-18', '010-5555-6666', 'https://picsum.photos/seed/13/200', 'user13@company.com', '서울시 중랑구 망우로 78',
       0, '2022-02-10', NULL, NOW(), NULL, NULL, NULL, NULL, NULL, 1, 5, 1, 7, 2),
      (14, 'password123', 1014, '최민재', '1990-09-30', '010-7777-8888', 'https://picsum.photos/seed/14/200', 'user14@company.com', '서울시 성동구 왕십리로 14',
       0, '2021-07-14', NULL, NOW(), NULL, NULL, NULL, NULL, NULL, 1, 5, 2, 3, 5),
      (15, 'password123', 1015, '정하늘', '1993-12-11', '010-9999-0000', 'https://picsum.photos/seed/15/200', 'user15@company.com', '서울시 노원구 화랑로 56',
       0, '2019-10-22', NULL, NOW(), NULL, NULL, NULL, NULL, NULL, 1, 5, 3, 1, 1),
      (16, 'password123', 1016, '윤지후', '1996-08-27', '010-1212-3434', 'https://picsum.photos/seed/16/200', 'user16@company.com', '서울시 동작구 사당로 33',
       0, '2020-03-28', NULL, NOW(), NULL, NULL, NULL, NULL, NULL, 1, 1, 4, 9, 6),
      (17, 'password123', 1017, '한수아', '1994-01-20', '010-5656-7878', 'https://picsum.photos/seed/17/200', 'user17@company.com', '서울시 종로구 세종대로 17',
       0, '2023-01-05', NULL, NOW(), NULL, NULL, NULL, NULL, NULL, 1, 1, 5, 2, 2),
      (18, 'password123', 1018, '오태양', '1991-10-05', '010-9090-1010', 'https://picsum.photos/seed/18/200', 'user18@company.com', '서울시 구로구 디지털로 31길',
       0, '2022-05-09', NULL, NOW(), NULL, NULL, NULL, NULL, NULL, 1, 5, 1, 4, 3),

      (19, 'abc123', 226, '서민종', '1997-02-26', '010-2643-7581', 'https://picture.mjay1', 'tjalswhd1@naver.com', '서울시 중랑구 면목동',
       10, '2025-06-19', NULL, NOW(), NULL, NULL, NULL, NULL, NULL, 1, 5, 1, 2, 1),
      (20,'$2a$10$GtpAHNePOD/7xYOy8VE0Ue9OKkDxhgRE0CWlblSUVUPPQVMcGGQBW',100001,'테스트사원1','1995-05-15','010-1111-1111','profiles/1/100001.png','test1@example.com','서울시 강남구',
       10,'2025-06-02 05:04:15',NULL,'2025-06-02 05:04:15',NULL,NULL,'2025-06-12 16:51:06',1,NULL,1,5,3,1,6),
      (21,'$2a$10$PnX6PEMKWIDNF0Po3/XDruAzFEeTEBzIrBEAoeludKhMh8I3EtRM2',100004,'인사맨','1998-01-01','010-1234-1234','profiles/55/100004.png','insaman@naver.com','서울시 종로구',
       10,'2025-06-05 16:50:26',NULL,'2025-06-05 16:50:32',NULL,NULL,'2025-06-18 06:28:12',1,NULL,1,2,3,1,6);





INSERT INTO `applicant` (`id`, `name`, `phone`, `email`, `profile_url`, `birth`, `address`) VALUES
                                                                                                (1,'서민종', '010-2643-7581', 'tjalswhd1@naver.com', 'https://picsum.photos/seed/app1/200', '1997-02-26', '서울시 중랑구 면목동'),
                                                                                                (2,'이수민', '010-2345-6789', 'soomin.lee@example.com', 'https://picsum.photos/seed/app2/200', '1992-08-23', '서울시 마포구 월드컵북로 456'),
                                                                                                (3,'박준영', '010-3456-7890', 'joonyoung.park@example.com', 'https://picsum.photos/seed/app3/200', '1990-02-11', '서울시 송파구 올림픽로 789'),
                                                                                                (4,'최예린', '010-4567-8901', 'yerin.choi@example.com', 'https://picsum.photos/seed/app4/200', '1995-11-09', '서울시 서초구 반포대로 101'),
                                                                                                (5,'정우성', '010-5678-9012', 'woosung.jung@example.com', 'https://picsum.photos/seed/app5/200', '1988-12-25', '서울시 용산구 이태원로 202'),
                                                                                                (6,'한서준', '010-6789-0123', 'seojun.han@example.com', 'https://picsum.photos/seed/app6/200', '1996-03-03', '서울시 강동구 성내로 303'),
                                                                                                (7,'장하늘', '010-7890-1234', 'haneul.jang@example.com', 'https://picsum.photos/seed/app7/200', '1993-09-19', '서울시 은평구 불광로 404'),
                                                                                                (8,'신유진', '010-8901-2345', 'yujin.shin@example.com', 'https://picsum.photos/seed/app8/200', '1991-07-01', '서울시 노원구 동일로 505'),
                                                                                                (9,'백민재', '010-9012-3456', 'minjae.baek@example.com', 'https://picsum.photos/seed/app9/200', '1989-04-22', '서울시 강북구 도봉로 606'),
                                                                                                (10,'오지현', '010-0123-4567', 'jihyun.oh@example.com', 'https://picsum.photos/seed/app10/200', '1997-06-30', '서울시 중랑구 망우로 707');


-- 상위 카테고리 (approval_category_id = NULL)
INSERT INTO `approval_category` (`id`, `approval_category_id`, `name`) VALUES
                                                                           (1, NULL, '결재 관련'),
                                                                           (2, NULL, '인사 관련'),
                                                                           (3, NULL, '재무 관련'),
                                                                           (4, NULL, '채용 관련'),
                                                                           (5, NULL, '근태 관련'),
                                                                           (6, NULL, '일반 행정');


-- 하위 카테고리 (각 상위 카테고리를 참조)
INSERT INTO `approval_category` (`id`,`approval_category_id`, `name`) VALUES
                                                                          (101, 1, '결재 취소 신청서'),

                                                                          (201,2, '인사발령 요청서'),
                                                                          (202,2, '사직서'),
                                                                          (301,3, '지출 결의서'),
                                                                          (302,3, '법인카드 신청서'),
                                                                          (303,3, '급여 변경 요청서'),
                                                                          (304,4, '출장 신청서'),
                                                                          (305,4, '물품 구매 요청서'),
                                                                          (401,5, '채용 요청서'),
                                                                          (501,5, '휴가 신청서'),
                                                                          (502,5, '조퇴 신청서'),
                                                                          (601,4, '회의실 예약 요청서');


-- [101] 결재 취소 신청서
INSERT INTO `approval_category_item` (`id`,`category_id`, `name`, `input_type`) VALUES
                                                                                    (10101,101, '취소 신청자 이름', 0),
                                                                                    (10102,101, '부서', 0),
                                                                                    (10103,101, '취소 신청 양식', 0),
                                                                                    (10104,101, '신청 일자', 4),
                                                                                    (10105,101, '취소 사유', 1);


-- [201] 인사발령 요청서
INSERT INTO `approval_category_item` (`id`,`category_id`, `name`, `input_type`) VALUES
                                                                                    (20101,201, '발령 대상자 이름', 0),
                                                                                    (20102,201, '현재 부서', 0),
                                                                                    (20103,201, '변경 부서', 0),
                                                                                    (20104,201, '발령일자', 4),
                                                                                    (20105,201, '발령 사유', 1);



-- [202] 사직서
INSERT INTO `approval_category_item` (`id`,`category_id`, `name`, `input_type`) VALUES
                                                                                    (20201,202, '사직자 이름', 0),
                                                                                    (20202,202, '부서', 0),
                                                                                    (20203,202, '사직 희망일', 4),
                                                                                    (20204,202, '사유', 1),
                                                                                    (20205,202, '인수인계 계획', 1);

-- [301] 지출 결의서
INSERT INTO `approval_category_item` (`id`,`category_id`, `name`, `input_type`) VALUES
                                                                                    (30101,301, '결의 제목', 0),
                                                                                    (30102,301, '지출 금액', 5),
                                                                                    (30103,301, '지출 항목', 0),
                                                                                    (30104,301, '지출일자', 4),
                                                                                    (30105,301, '지출 내역 설명', 1);

-- [302] 법인카드 신청서
INSERT INTO `approval_category_item` (`id`,`category_id`, `name`, `input_type`) VALUES
                                                                                    (30201,302, '신청자 이름', 0),
                                                                                    (30202,302, '부서', 0),
                                                                                    (30203,302, '카드 사용 용도', 0),
                                                                                    (30204,302, '예상 사용 금액', 5),
                                                                                    (30205,302, '필요 사유', 1);

-- [303] 급여 변경 요청서
INSERT INTO `approval_category_item` (`id`,`category_id`, `name`, `input_type`) VALUES
                                                                                    (30301,303, '직원 이름', 0),
                                                                                    (30302,303, '현재 급여', 5),
                                                                                    (30303,303, '변경 급여', 5),
                                                                                    (30304,303, '변경 사유', 1),
                                                                                    (30305,303, '적용일', 4);

-- [304] 출장 신청서
INSERT INTO `approval_category_item` (`id`,`category_id`, `name`, `input_type`) VALUES
                                                                                    (30401,304, '출장자', 0),
                                                                                    (30402,304, '출장 지역', 0),
                                                                                    (30403,304, '출장 목적', 1),
                                                                                    (30404,304, '출장 시작일', 4),
                                                                                    (30405,304, '출장 종료일', 4);

-- [305] 물품 구매 요청서
INSERT INTO `approval_category_item` (`id`,`category_id`, `name`, `input_type`) VALUES
                                                                                    (30501,305, '신청자 이름', 0),
                                                                                    (30502,305, '물품 이름', 0),
                                                                                    (30503,305, '수량', 5),
                                                                                    (30504,305, '단가', 5),
                                                                                    (30505,305, '구매 목적', 1);

-- [401] 채용 요청서
INSERT INTO `approval_category_item` (`id`,`category_id`, `name`, `input_type`) VALUES
                                                                                    (40101,401, '부서', 5),
                                                                                    (40102,401, '직무', 5),
                                                                                    (40103,401, '모집 인원', 5),
                                                                                    (40104,401, '모집 시작일', 4),
                                                                                    (40105,401, '모집 마감일', 4),
                                                                                    (40106,401, '지원자격', 0),
                                                                                    (40107,401, '우대사항', 0),
                                                                                    (40108,401, '주요업무', 0),
                                                                                    (40109,401, '고용형태', 0),
                                                                                    (40110,401, '근무 지역', 0);

-- [501] 휴가 신청서
INSERT INTO `approval_category_item` (`id`,`category_id`, `name`, `input_type`) VALUES
                                                                                    (50101,501, '휴가 종류', 0),        -- TEXT
                                                                                    (50102,501, '시작일', 4),           -- DATE
                                                                                    (50103,501, '종료일', 4),
                                                                                    (50104,501, '총 휴가일 수', 5),     -- NUMBER
                                                                                    (50105,501, '사유', 1);             -- TEXTAREA

-- [601] 회의실 예약 요청서
INSERT INTO `approval_category_item` (`id`,`category_id`, `name`, `input_type`) VALUES
                                                                                    (60101,601, '예약자 이름', 0),
                                                                                    (60102,601, '회의 목적', 0),
                                                                                    (60103,601, '회의실 이름', 0),
                                                                                    (60104,601, '시작 시간', 4),
                                                                                    (60105,601, '종료 시간', 4);

INSERT INTO approval_line (step_order, approval_category_id, position_id) VALUES
-- 101. 결재 취소 신청서
(1, 101, 1), (2, 101, 2), (3, 101, 3),
-- 201. 인사발령 요청서
(1, 201, 1), (2, 201, 2), (3, 201, 3),
-- 202. 사직서
(1, 202, 1), (2, 202, 2), (3, 202, 3), (4, 202, 4),
-- 301. 지출 결의서
(1, 301, 1), (2, 301, 2), (3, 301, 3),
-- 302. 법인카드 신청서
(1, 302, 1), (2, 302, 2), (3, 302, 3),
-- 303. 급여 변경 요청서
(1, 303, 1), (2, 303, 2), (3, 303, 3), (4, 303, 4),
-- 304. 출장 신청서
(1, 304, 1), (2, 304, 2), (3, 304, 3),
-- 305. 물품 구매 요청서
(1, 305, 1), (2, 305, 2),
-- 401. 채용 요청서   (수정)
(1, 401, 2), (2, 401, 3),
-- 501. 휴가 신청서
(1, 501, 1), (2, 501, 2),
-- 502. 조퇴 신청서
(1, 502, 1), (2, 502, 2),
-- 601. 회의실 예약 요청서
(1, 601, 1), (2, 601, 2);




-- 채용 요청서 (category_id = 401), 작성자(writer_id)는 예시로 1번으로
INSERT INTO approval (
    id, category_id, writer_id, status,
    first_approver_id, second_approver_id, third_approver_id, fourth_approver_id
) VALUES (
             1, 401, 1, 0,   -- status = 0: 대기 상태
             2, 3, 4, 5  -- 결재선 예시
         );


INSERT INTO approval_content (id, approval_id, item_id, content) VALUES
                                                                     (1,1, 40101, '개발팀'),
                                                                     (2,1, 40102, '백엔드 개발자'),
                                                                     (3,1, 40103, '2'),
                                                                     (4,1, 40104, '신규 프로젝트 런칭으로 인한 인력 보강 필요'),
                                                                     (5,1, 40105, '2025-07-01');

INSERT INTO recruitment_request (
    headcount,
    started_at,
    ended_at,
    qualification,
    preference,
    responsibility,
    employment_type,
    work_location,
    member_id,
    department_id,
    job_id
) VALUES (
             2,
             '2025-07-01 09:00:00',
             '2025-07-31 18:00:00',
             '컴퓨터공학 전공자 또는 관련 자격증 소지자',
             'React, Vue.js 경험자 우대',
             '웹 서비스 프론트엔드 개발 및 유지보수',
             '정규직',
             '서울 강남구 본사',
             19,  -- 작성자(member_id)
             5,  -- 소속 부서(department_id)
             1   -- 모집 직무(job_id)
         );



INSERT INTO introduce_template (id, title, member_id) VALUES
                                                          (1,'신입 개발자 자기소개서 템플릿', 1),
                                                          (2,'경력직 마케터 자기소개서 템플릿', 1),
                                                          (3,'디자이너 포트폴리오 템플릿', 1);

-- Template 1: 신입 개발자
INSERT INTO introduce_template_item (id, title, member_id, introduce_template_id) VALUES
                                                                                      (1,'성장 배경 및 가치관', 1, 1),
                                                                                      (2,'개발을 시작하게 된 계기', 1, 1),
                                                                                      (3,'협업 경험과 느낀 점', 1, 1);

-- Template 2: 경력직 마케터
INSERT INTO introduce_template_item (id, title, member_id, introduce_template_id) VALUES
                                                                                      (4,'이전 직장에서의 성과', 1, 2),
                                                                                      (5,'마케팅 전략 기획 경험', 1, 2),
                                                                                      (6,'회사에 기여할 수 있는 점', 1, 2);

-- Template 3: 디자이너 포트폴리오
INSERT INTO introduce_template_item (id, title, member_id, introduce_template_id) VALUES
                                                                                      (7,'디자인 철학과 방향성', 1, 3),
                                                                                      (8,'대표 프로젝트 소개', 1, 3),
                                                                                      (9,'협업을 통한 문제 해결 사례', 1, 3);

INSERT INTO recruitment (
    id,
    title,
    content,
    recruit_type,
    status,
    image_url,
    started_at,
    ended_at,
    member_id,
    introduce_template_id,
    recruitment_template_id,
    recruitment_request_id
) VALUES (
             1,
             '백엔드 개발자 모집',
             'Node.js 및 Spring 기반의 백엔드 개발자를 모집합니다.',
             0,                     -- 정규직(예시)
             0,                     -- 진행 중
             'https://example.com/image/backend.png',
             '2025-06-20 09:00:00',
             '2025-07-10 18:00:00',
             1,                     -- 작성자 member_id (예시)
             1,                     -- introduce_template_id (예시로 1번 템플릿)
             NULL,                  -- recruitment_template_id (선택 사항)
             NULL                   -- recruitment_request_id (선택 사항, UNIQUE이지만 NULL은 가능)
         );

INSERT INTO `recruitment_process` (id, step_type, display_order, recruitment_id)
VALUES
    (1, 0, 1, 1),
    (2, 1, 2, 1),
    (3, 2, 3, 1);

INSERT INTO `application_item_category` (id, name, input_type, display_order, application_item_category_id) VALUES
-- 대분류 (application_item_category_id = NULL)
(1, '기본 인적사항', 0, 1, NULL),
(2, '학력', 0, 2, NULL),
(3, '경력', 0, 3, NULL),
(4, '자격증', 0, 4, NULL),
(5, '어학', 0, 5, NULL),
(6, '수상 내역', 0, 6, NULL),
(7, '대외활동', 0, 7, NULL),
(8, '프로젝트 경험', 0, 8, NULL),
(9, '인턴', 0, 9, NULL),
(10, '교육', 0, 10, NULL),
(11, '포트폴리오 및 기타문서', 0, 11, NULL),

-- 기본 인적사항
(12, '이름', 0, 1, 1),
(13, '생년월일', 4, 2, 1),
(14, '성별', 0, 3, 1),
(15, '연락처', 0, 4, 1),
(16, '이메일', 0, 5, 1),
(17, '주소', 1, 6, 1),
(18, '사진', 2, 7, 1),

-- 학력
(19, '학교명', 0, 1, 2),
(20, '전공', 0, 2, 2),
(21, '재학기간', 0, 3, 2),
(22, '학위', 0, 4, 2),
(23, '졸업 구분', 0, 5, 2),
(24, '성적', 0, 6, 2),

-- 경력
(25, '회사명', 0, 1, 3),
(26, '근무기간', 0, 2, 3),
(27, '부서 / 직책', 0, 3, 3),
(28, '담당업무', 1, 4, 3),
(29, '퇴직 사유', 1, 5, 3),
(30, '경력 기술서', 1, 6, 3),

-- 자격증
(31, '자격증명', 0, 1, 4),
(32, '발급기관', 0, 2, 4),
(33, '취득일자', 4, 3, 4),

-- 어학
(34, '시험 종류', 0, 1, 5),
(35, '점수 / 등급', 0, 2, 5),
(36, '취득 일자', 4, 3, 5),

-- 수상 내역
(37, '수상명', 0, 1, 6),
(38, '주최 기관', 0, 2, 6),
(39, '수상일', 4, 3, 6),
(40, '수상 내용', 1, 4, 6),

-- 대외활동
(41, '활동명', 0, 1, 7),
(42, '활동기간', 0, 2, 7),
(43, '활동내용', 1, 3, 7),

-- 프로젝트 경험
(44, '프로젝트명', 0, 1, 8),
(45, '참여기간', 0, 2, 8),
(46, '기술스택', 0, 3, 8),
(47, '담당 역할', 1, 4, 8),
(48, '성과', 1, 5, 8),

-- 인턴
(49, '회사명', 0, 1, 9),
(50, '인턴기간', 0, 2, 9),
(51, '담당업무', 1, 3, 9),

-- 교육
(52, '교육명', 0, 1, 10),
(53, '교육기관', 0, 2, 10),
(54, '교육기간', 0, 3, 10),

-- 포트폴리오 및 기타문서
(55, '포트폴리오 URL', 3, 1, 11),
(56, '포트폴리오 파일 첨부', 2, 2, 11),
(57, '추가 첨부자료', 2, 3, 11);



-- 채용공고 ID = 1에 필수 항목 등록
INSERT INTO application_item (id,is_required, application_item_category_id, recruitment_id) VALUES
                                                                                                (1,'Y', 1, 1),
                                                                                                (2,'Y', 2, 1),
                                                                                                (3,'Y', 3, 1),
                                                                                                (4,'Y', 4, 1),
                                                                                                (5,'Y', 5, 1),
                                                                                                (6,'Y', 6, 1);

INSERT INTO application (
    id,
    recruitment_id,
    applicant_id,
    status,
    created_at,
    updated_at,
    updated_by,
    introduce_rating_result_id
) VALUES (
             1,
             1,        -- 채용공고 ID
             1,        -- 지원자 ID
             0,        -- 상태: 임시저장 또는 제출 (기본 0)
             NOW(),    -- 생성일시
             NULL,     -- 수정일시
             NULL,     -- 수정자
             NULL      -- 자기소개서 평가 결과
         ),
         (2,
          1,
          2,
          0,
          NOW(),
          NULL,
          NULL,
          NULL
         ),
         (3,
          1,
          3,
          0,
          NOW(),
          NULL,
          NULL,
          NULL);

INSERT INTO application_response (id, application_id, application_item_id, content) VALUES
                                                                                        (1,1, 1, '군필(육군 병장 만기 제대, 2015.06 ~ 2017.03)'),
                                                                                        (2,1, 2, '백엔드 개발자로 3년간 Spring 기반의 업무 경험이 있습니다.'),
                                                                                        (3,1, 3, 'resume_hong.pdf'),
                                                                                        (4,1, 4, 'https://portfolio.example.com/hong'),
                                                                                        (5,1, 5, '4500'),  -- 단위: 만원
                                                                                        (6,1, 6, '2025-07-15');

INSERT INTO application_response (id, application_id, application_item_id, content) VALUES
                                                                                        (7,2, 1, '군 면제 (대학원 석사과정 중 면제)'),
                                                                                        (8,2, 2, '마케팅 전략 수립 및 실행 경험이 있습니다. 스타트업과 대기업 모두 경험했습니다.'),
                                                                                        (9,2, 3, 'resume_lee.pdf'),
                                                                                        (10,2, 4, 'https://portfolio.example.com/lee'),
                                                                                        (11,2, 5, '5000'),
                                                                                        (12,2, 6,  '2025-08-01');

INSERT INTO application_response (id, application_id, application_item_id, content) VALUES
                                                                                        (13,3, 1, '현역 복무 중 전역 예정일 2025-11-30'),
                                                                                        (14,3, 2, '정보보안 관련 프로젝트와 인턴 경험이 있습니다. OWASP Top 10 분석 경험 포함.'),
                                                                                        (15,3, 3, 'resume_kim.pdf'),
                                                                                        (16,3, 4, 'https://portfolio.example.com/kim'),
                                                                                        (17,3, 5, '4000'),
                                                                                        (18,3, 6, '2025-12-01');


INSERT INTO introduce (
    id,
    content,
    introduce_template_id,
    applicant_id,
    application_id
) VALUES
      (1,
       '저는 새로운 기술을 배우는 데 주저함이 없는 개발자입니다. 학창 시절부터 웹 프로그래밍에 관심을 가지고 꾸준히 공부해왔으며, 최근에는 Spring Boot와 Vue.js를 활용한 사이드 프로젝트를 진행했습니다. 문제를 해결할 때 논리적인 접근을 중요시하며, 코드의 가독성과 협업 능력 향상에 항상 신경 씁니다.',
       1,
       1,
       1
      ),
      (2,
       '다양한 산업군에서 마케팅 실무를 경험하며 브랜드 전략, 퍼포먼스 마케팅, 콘텐츠 기획 등 여러 영역에서 성과를 만들어 왔습니다. 특히 데이터 기반의 의사결정과 고객 중심의 사고를 바탕으로 효과적인 캠페인을 설계하고 실행하는 데 강점을 가지고 있습니다.',
       1,
       2,
       2
      ),
      (3,
       '정보보안 분야에 흥미를 느껴 학부 시절부터 다양한 프로젝트에 참여했고, 보안 관련 논문 스터디 및 실습을 통해 지식을 쌓았습니다. 최근에는 OWASP Top 10에 기반한 웹 취약점 분석 도구를 직접 제작해보며 실무 능력을 키웠습니다. 이러한 경험을 바탕으로 더욱 안전한 시스템을 만드는 데 기여하고 싶습니다.',
       1,
       3,
       3
      );

INSERT INTO introduce_template_item_response (introduce_id, introduce_template_item_id, content) VALUES
-- introduce 1 → template 1 → item 1~3
(1, 1, '저는 가족과의 유대감 속에서 책임감과 성실함을 배웠습니다.'),
(1, 2, '대학 시절 웹 서비스를 직접 개발해보며 개발자의 꿈을 키웠습니다.'),
(1, 3, '프로젝트 팀원들과의 협업을 통해 소통의 중요성을 배웠습니다.'),

-- introduce 2 → template 2 → item 4~6
(2, 4, '이전 직장에서는 ERP 시스템 개선을 통해 업무 효율을 20% 향상시켰습니다.'),
(2, 5, '시장 조사와 고객 분석을 기반으로 마케팅 전략을 수립한 경험이 있습니다.'),
(2, 6, '데이터 분석 기반의 기획 능력으로 회사의 방향에 기여할 수 있습니다.'),

-- introduce 3 → template 3 → item 7~9
(3, 7, '사용자 중심의 디자인을 지향하며, 심플함과 직관성을 중요하게 생각합니다.'),
(3, 8, '대표 프로젝트로는 반응형 웹 포트폴리오 제작 경험이 있습니다.'),
(3, 9, '개발자와의 긴밀한 협업을 통해 UI 성능을 최적화한 경험이 있습니다.');



INSERT INTO introduce_standard (id, content, member_id) VALUES
                                                            (1, '자기소개서 평가 기준표 - 기본 항목', 1),
                                                            (2, '자기소개서 평가 기준표 - 경력직 항목', 1),
                                                            (3, '자기소개서 평가 기준표 - 기술직 항목', 1);


INSERT INTO introduce_standard_item (id, content, member_id, introduce_standard_id) VALUES
-- 1번 기준표 (기본 항목)
(1, '지원 동기가 명확한가?', 1, 1),
(2, '성장 과정이 구체적으로 기술되어 있는가?', 1, 1),
(3, '지원 직무와의 연관성이 있는 경험이 서술되었는가?', 1, 1),
(4, '문장 구성 및 표현력이 우수한가?', 1, 1),
(5, '회사에 대한 이해도가 느껴지는가?', 1, 1),

-- 2번 기준표 (경력직 항목)
(6, '이전 직무 경험이 구체적인가?', 1, 2),
(7, '성과 중심으로 서술되었는가?', 1, 2),
(8, '경력과 지원 직무 간 연계성이 충분한가?', 1, 2),
(9, '리더십 또는 협업 경험이 나타나는가?', 1, 2),
(10, '경력 기술이 과장 없이 진실된가?', 1, 2),

-- 3번 기준표 (기술직 항목)
(11, '기술 스택에 대한 이해가 명확한가?', 1, 3),
(12, '프로젝트 경험이 구체적으로 기술되어 있는가?', 1, 3),
(13, '문제 해결 경험이 서술되어 있는가?', 1, 3),
(14, '성장 의지와 학습 태도가 드러나는가?', 1, 3),
(15, '기술을 통해 어떤 가치를 만들고 싶은지가 드러나는가?', 1, 3);


INSERT INTO introduce_rating_result (
    id,
    content,
    rating_score,
    updated_at,
    updated_by,
    member_id,
    introduce_standard_id,
    introduce_id
) VALUES (
             1,
             '열정적이고 기술에 대한 이해도가 높아 보이는 지원자',85,NULL,NULL,1,1,1);




INSERT INTO question (id, content, detail_content, type, difficulty, answer, created_member_id)
VALUES
    (1,'프로그래밍 언어 중 정적 타입 언어는?', 'Java, C, Python 등의 언어를 비교', 0, 1, 'Java', 1),
    (2,'HTTP 상태코드 404는 어떤 의미인가요?', NULL, 0, 1, 'Not Found', 1),
    (3,'객체지향의 4가지 주요 특징 중 하나가 아닌 것은?', NULL, 0, 2, '직렬화', 1),
    (4,'SQL에서 데이터를 정렬할 때 사용하는 키워드는?', NULL, 0, 1, 'ORDER BY', 1),
    (5,'다음 중 브라우저에서 동작하는 언어는?', NULL, 0, 1, 'JavaScript', 1),
    (6,'HTML에서 웹 페이지의 제목을 지정하는 태그는?',NULL,0,1,'<title>',1),
    (7,'JavaScript에서 변수 선언에 사용되지 않는 키워드는?','let, var, const 중 올바르지 않은 것',0,2,'int',1),
    (8,'CSS에서 글자 색상을 지정하는 속성은?',NULL,0,1,'color',1),
    (9,'HTTP 프로토콜의 기본 포트는?',NULL,0,1,80,1),
    (10,'React에서 상태 관리를 위해 사용하는 Hook은?','상태(State)를 선언하기 위한 Hook',0,2,'useState',1),
    (11,'C언어에서 조건문을 작성할 때 사용하는 키워드는?',NULL,0,1,'if',1),
    (12,'Python에서 리스트의 길이를 구하는 함수는?',       NULL,0,1,'len()',1),
    (13,'Git에서 현재 브랜치를 확인하는 명령어는?',NULL,0,1,'git branch',1),
    (14,'SQL에서 모든 데이터를 조회하는 명령어는?',NULL,0,1,'SELECT *',1),
    (15,'TCP와 UDP 중 연결 기반(신뢰성 보장)은?',NULL,0,2,'TCP',1),
    (16,'REST API의 핵심 설계 원칙이 아닌 것은?',NULL,0,2,'Stateful',1),
    (17,'JVM은 무엇의 약자인가?',NULL,0,2,'Java Virtual Machine',1),
    (18,'MySQL에서 기본 키를 설정하는 키워드는?',NULL,0,2,'PRIMARY KEY',1),
    (19,'Docker는 어떤 기술과 관련이 깊은가?','소프트웨어 컨테이너 기술',0,2,'컨테이너',1),
    (20,'HTTP 요청 방식 중 ""리소스를 수정""할 때 주로 사용하는 것은?',NULL,0,2,'PUT',1),
    (21,'JavaScript에서 배열에 값을 추가하는 함수는?',NULL,0,1,'push()',1),
    (22,'브라우저의 로컬 저장소는 무엇인가?',NULL,0,2,'localStorage',1),
    (23,'쿠키와 세션의 가장 큰 차이점은?',NULL,0,2,'저장 위치',1),
    (24,'HTTPS는 무엇을 추가한 프로토콜인가?',NULL,0,2,'보안 계층이 추가된 HTTP',1),
    (25,'OSI 7계층에서 전송 계층은 몇 계층인가?',NULL,0,2,'4계층',1),
    (26,'Vue.js에서 컴포넌트를 등록할 때 사용하는 속성은?',NULL,0,2,'components',1),
    (27,'리눅스에서 현재 디렉토리를 확인하는 명령어는?',NULL,0,1,'pwd',1),
    (28,'시스템 콜이란?','운영체제가 제공하는 인터페이스',0,2,'커널 호출',1),
    (29,'클래스의 인스턴스를 생성하는 것을 무엇이라 하는가?',NULL,0,2,'객체를 생성하는 것',1),
    (30,'비동기 처리를 위해 JavaScript에서 사용하는 객체는?',NULL,0,2,'Promise',1),
    (31,'API란 무엇인가?','응용 프로그램 인터페이스',0,1,'Application Programming Interface',1),
    (32,'Redux는 무엇을 관리하는 도구인가?',NULL,0,2,'상태 관리 도구',1),
    (33,'C++에서 클래스 외부에서 멤버 함수를 정의할 때 사용하는 연산자?',NULL,0,2,'::',1),
    (34,'MVC 패턴에서 M은 무엇을 의미하는가?',NULL,0,1,'Model',1),
    (35,'DB 정규화의 목적이 아닌 것은?',NULL,0,2,'속도를 빠르게 하기 위함',1);

INSERT INTO question_option (option_number, content, question_id) VALUES
-- 1번: 정적 타입 언어 (정답: Java)
(1, 'Java', 1),
(2, 'Python', 1),
(3, 'C', 1),
(4, 'Ruby', 1),

-- 2번: HTTP 404 의미 (정답: Not Found)
(1, 'Not Found', 2),
(2, 'OK', 2),
(3, 'Moved Permanently', 2),
(4, 'Internal Server Error', 2),

-- 3번: 객체지향 아닌 것 (정답: 직렬화)
(1, '추상화', 3),
(2, '캡슐화', 3),
(3, '직렬화', 3),
(4, '다형성', 3),

-- 4번: SQL 정렬 키워드 (정답: ORDER BY)
(1, 'SELECT', 4),
(2, 'ORDER BY', 4),
(3, 'GROUP BY', 4),
(4, 'WHERE', 4),

-- 5번: 브라우저 언어 (정답: JavaScript)
(1, 'JavaScript', 5),
(2, 'Python', 5),
(3, 'C++', 5),
(4, 'Java', 5),

-- 6번: HTML 제목 태그 (정답: <title>)
(1, '<title>', 6),
(2, '<head>', 6),
(3, '<meta>', 6),
(4, '<header>', 6),

-- 7번: JS 변수 선언 안되는 키워드 (정답: int)
(1, 'let', 7),
(2, 'var', 7),
(3, 'const', 7),
(4, 'int', 7),

-- 8번: CSS 글자 색상 속성 (정답: color)
(1, 'color', 8),
(2, 'font-color', 8),
(3, 'text-color', 8),
(4, 'font-style', 8),

-- 9번: HTTP 기본 포트 (정답: 80)
(1, '80', 9),
(2, '443', 9),
(3, '21', 9),
(4, '8080', 9),

-- 10번: React 상태 Hook (정답: useState)
(1, 'useState', 10),
(2, 'useEffect', 10),
(3, 'useRef', 10),
(4, 'useMemo', 10),

-- 11. 조건문 키워드 (정답: if)
(1, 'if', 11),
(2, 'switch', 11),
(3, 'for', 11),
(4, 'while', 11),

-- 12. 리스트 길이 함수 (정답: len())
(1, 'len()', 12),
(2, 'size()', 12),
(3, 'count()', 12),
(4, 'length()', 12),

-- 13. 현재 브랜치 확인 (정답: git branch)
(1, 'git branch', 13),
(2, 'git status', 13),
(3, 'git log', 13),
(4, 'git show', 13),

-- 14. 모든 데이터 조회 (정답: SELECT *)
(1, 'SELECT *', 14),
(2, 'FIND ALL', 14),
(3, 'GET *', 14),
(4, 'SHOW ALL', 14),

-- 15. 연결 기반 프로토콜 (정답: TCP)
(1, 'TCP', 15),
(2, 'UDP', 15),
(3, 'ICMP', 15),
(4, 'HTTP', 15),

-- 16. REST 원칙 아님 (정답: Stateful)
(1, 'Stateful', 16),
(2, '무상태성(Stateless)', 16),
(3, '계층화된 시스템', 16),
(4, '클라이언트-서버 구조', 16),

-- 17. JVM 약자 (정답: Java Virtual Machine)
(1, 'Java Virtual Machine', 17),
(2, 'Java Variable Manager', 17),
(3, 'Just-in-time Value Module', 17),
(4, 'Java View Model', 17),

-- 18. 기본 키 설정 키워드 (정답: PRIMARY KEY)
(1, 'PRIMARY KEY', 18),
(2, 'FOREIGN KEY', 18),
(3, 'UNIQUE KEY', 18),
(4, 'AUTO_INCREMENT', 18),

-- 19. Docker 관련 기술 (정답: 컨테이너)
(1, '컨테이너', 19),
(2, '가상 머신', 19),
(3, '블록체인', 19),
(4, '서버리스', 19),

-- 20. 리소스 수정 HTTP 메서드 (정답: PUT)
(1, 'PUT', 20),
(2, 'GET', 20),
(3, 'POST', 20),
(4, 'DELETE', 20),

-- 21. 배열에 값 추가 함수 (정답: push())
(1, 'push()', 21),
(2, 'append()', 21),
(3, 'add()', 21),
(4, 'insert()', 21),

-- 22. 브라우저 로컬 저장소 (정답: localStorage)
(1, 'localStorage', 22),
(2, 'sessionStorage', 22),
(3, 'cookieStorage', 22),
(4, 'dataStorage', 22),

-- 23. 쿠키 vs 세션 차이점 (정답: 저장 위치)
(1, '저장 위치', 23),
(2, '세션은 클라이언트에 저장', 23),
(3, '쿠키는 서버에 저장', 23),
(4, '쿠키는 휘발성 저장소', 23),

-- 24. HTTPS 의미 (정답: 보안 계층이 추가된 HTTP)
(1, '보안 계층이 추가된 HTTP', 24),
(2, '압축된 HTTP', 24),
(3, '빠른 전송을 위한 HTTP', 24),
(4, 'UDP 기반 HTTP', 24),

-- 25. OSI 전송 계층 (정답: 4계층)
(1, '4계층', 25),
(2, '3계층', 25),
(3, '5계층', 25),
(4, '6계층', 25),

-- 26. Vue 컴포넌트 등록 속성 (정답: components)
(1, 'components', 26),
(2, 'methods', 26),
(3, 'props', 26),
(4, 'data', 26),

-- 27. 리눅스 현재 디렉토리 확인 (정답: pwd)
(1, 'pwd', 27),
(2, 'cd', 27),
(3, 'ls', 27),
(4, 'dir', 27),

-- 28. 시스템 콜 정의 (정답: 커널 호출)
(1, '커널 호출', 28),
(2, '운영체제 함수 호출', 28),
(3, '컴파일러 명령', 28),
(4, '네트워크 요청', 28),

-- 29. 인스턴스 생성 의미 (정답: 객체를 생성하는 것)
(1, '객체를 생성하는 것', 29),
(2, '클래스를 생성하는 것', 29),
(3, '객체를 선언하는 것', 29),
(4, '함수를 실행하는 것', 29),

-- 30. 비동기 처리 객체 (정답: Promise)
(1, 'Promise', 30),
(2, 'Callback', 30),
(3, 'setTimeout', 30),
(4, 'Interval', 30),

-- 31. API란? (정답: Application Programming Interface)
(1, 'Application Programming Interface', 31),
(2, 'Advanced Program Instruction', 31),
(3, 'Applied Protocol Integration', 31),
(4, 'Automated Process Interface', 31),

-- 32. Redux의 역할 (정답: 상태 관리 도구)
(1, '상태 관리 도구', 32),
(2, '라우팅 관리 도구', 32),
(3, 'UI 렌더링 도구', 32),
(4, '테스트 자동화 도구', 32),

-- 33. C++ 클래스 외부 멤버함수 정의 연산자 (정답: ::)
(1, '::', 33),
(2, '.', 33),
(3, '->', 33),
(4, '&&', 33),

-- 34. MVC의 M (정답: Model)
(1, 'Model', 34),
(2, 'Management', 34),
(3, 'Middleware', 34),
(4, 'Monitor', 34),

-- 35. 정규화 목적이 아닌 것 (정답: 속도를 빠르게 하기 위함)
(1, '속도를 빠르게 하기 위함', 35),
(2, '데이터 중복 최소화', 35),
(3, '데이터 무결성 향상', 35),
(4, '테이블 수 증가', 35);

INSERT INTO job_test (
    id,
    title,
    difficulty,
    test_time,
    started_at,
    ended_at,
    created_member_id,
    updated_member_id
) VALUES (
             1,
             '백엔드 개발자 기본 실무 테스트',
             2,             -- 난이도 중간
             60,            -- 제한 시간 60분
             '2025-06-21 09:00:00',
             '2025-06-30 23:59:59',
             1,             -- 생성자 ID
             NULL
         );

-- job_test_id = 1에 question_id = 1~5 삽입
INSERT INTO job_test_question (id, score, option_number, job_test_id, question_id) VALUES
                                                                                       (1,10, 1, 1, 1),
                                                                                       (2,10, 2, 1, 2),
                                                                                       (3,10, 3, 1, 3),
                                                                                       (4,10, 4, 1, 4),
                                                                                       (5,10, 5, 1, 5),
                                                                                       (6,10, 6, 1, 6),
                                                                                       (7,10, 7, 1, 7),
                                                                                       (8,10, 8, 1, 8),
                                                                                       (9,10, 9, 1, 9),
                                                                                       (10,10, 10, 1, 10);


INSERT INTO application_job_test (
    id,
    evaluator_comment,
    submitted_at,
    grading_total_score,
    evaluation_score,
    grading_status,
    evaluation_status,
    entry_code,
    application_id,
    job_test_id,
    grading_member_id,
    evaluation_member_id
) VALUES (
             1,
             NULL,                                 -- 평가자 코멘트
             NOW(),                                -- 제출일: 현재 시각
             0,                                    -- 채점 점수
             0,                                    -- 평가 점수
             0,                                    -- 채점 상태 (0: 미채점)
             0,                                    -- 평가 상태 (0: 미평가)
             'ABC123',                             -- 입장 코드 (예시)
             1,                                    -- application_id
             1,                                    -- job_test_id
             NULL,                                 -- grading_member_id
             NULL                                  -- member_id
         ),
         (
             2,
             NULL,                                 -- 평가자 코멘트
             NOW(),                                -- 제출일: 현재 시각
             0,                                    -- 채점 점수
             0,                                    -- 평가 점수
             0,                                    -- 채점 상태 (0: 미채점)
             0,                                    -- 평가 상태 (0: 미평가)
             'ABC123',                             -- 입장 코드 (예시)
             2,                                    -- application_id
             1,                                    -- job_test_id
             NULL,                                 -- grading_member_id
             NULL                                  -- member_id
         ),
         (
             3,
             NULL,                                 -- 평가자 코멘트
             NOW(),                                -- 제출일: 현재 시각
             0,                                    -- 채점 점수
             0,                                    -- 평가 점수
             0,                                    -- 채점 상태 (0: 미채점)
             0,                                    -- 평가 상태 (0: 미평가)
             'ABC123',                             -- 입장 코드 (예시)
             3,                                    -- application_id
             1,                                    -- job_test_id
             NULL,                                 -- grading_member_id
             NULL                                  -- member_id
         );


-- Question 1 (id = 1)
INSERT INTO answer (id, content, attempt, is_correct, score, application_job_test_id, question_id) VALUES
                                                                                                       (1,'Java', 1, 1, 10, 1, 1),
                                                                                                       (2,'Python', 1, 0, 0, 1, 1),
                                                                                                       (3,'Ruby', 1, 0, 0, 1, 1),
                                                                                                       (4,'PHP', 1, 0, 0, 1, 1),
                                                                                                       (5,'JavaScript', 1, 0, 0, 1, 1);

-- Question 2 (id = 2)
INSERT INTO answer (id, content, attempt, is_correct, score, application_job_test_id, question_id) VALUES
                                                                                                       (6,'OK', 1, 0, 0, 1, 2),
                                                                                                       (7,'Not Found', 1, 1, 10, 1, 2),
                                                                                                       (8,'Moved Permanently', 1, 0, 0, 1, 2),
                                                                                                       (9,'Internal Server Error', 1, 0, 0, 1, 2),
                                                                                                       (10,'Bad Request', 1, 0, 0, 1, 2);

-- Question 3 (id = 3)
INSERT INTO answer (id, content, attempt, is_correct, score, application_job_test_id, question_id) VALUES
                                                                                                       (11,'캡슐화', 1, 0, 0, 1, 3),
                                                                                                       (12,'추상화', 1, 0, 0, 1, 3),
                                                                                                       (13,'직렬화', 1, 1, 10, 1, 3),
                                                                                                       (14,'상속', 1, 0, 0, 1, 3),
                                                                                                       (15,'다형성', 1, 0, 0, 1, 3);

-- Question 4 (id = 4)
INSERT INTO answer (id, content, attempt, is_correct, score, application_job_test_id, question_id) VALUES
                                                                                                       (16,'SELECT', 1, 0, 0, 1, 4),
                                                                                                       (17,'WHERE', 1, 0, 0, 1, 4),
                                                                                                       (18,'GROUP BY', 1, 0, 0, 1, 4),
                                                                                                       (19,'ORDER BY', 1, 1, 10, 1, 4),
                                                                                                       (20,'HAVING', 1, 0, 0, 1, 4);

-- Question 5 (id = 5)
INSERT INTO answer (id, content, attempt, is_correct, score, application_job_test_id, question_id) VALUES
                                                                                                       (21,'Python', 1, 0, 0, 1, 5),
                                                                                                       (22,'C++', 1, 0, 0, 1, 5),
                                                                                                       (23,'Go', 1, 0, 0, 1, 5),
                                                                                                       (24,'JavaScript', 1, 1, 10, 1, 5),
                                                                                                       (25,'Java', 1, 0, 0, 1, 5);




INSERT INTO interview_sheet (id, name, member_id) VALUES
                                                      (1,'신입 개발자 면접 평가표', 1),
                                                      (2,'경력 디자이너 면접 평가표', 1),
                                                      (3,'마케팅 매니저 면접 평가표', 1);

INSERT INTO interview_criteria (id, sheet_id, title, content, weight, member_id) VALUES
                                                                                     (1,1, '기초 지식', '자료구조, 알고리즘 등 전반적인 컴퓨터 공학 기초 지식 수준 평가', 0.2, 1),
                                                                                     (2,1, '코딩 능력', '실제 코딩 테스트나 코드 리뷰를 통한 구현 능력 평가', 0.2, 1),
                                                                                     (3,1, '문제 해결력', '주어진 문제를 논리적으로 접근하고 해결하는 방식 평가', 0.2, 1),
                                                                                     (4,1, '협업 태도', '팀원과의 커뮤니케이션, 협업 경험에 대한 태도', 0.2, 1),
                                                                                     (5,1, '성장 가능성', '학습 의지, 피드백 수용 등 장기적인 성장 가능성', 0.2, 1);

INSERT INTO interview_criteria (id, sheet_id, title, content, weight, member_id) VALUES
                                                                                     (6,2, '디자인 퀄리티', '포트폴리오나 과제에서 보여지는 완성도와 창의성', 0.2, 1),
                                                                                     (7,2, '툴 숙련도', 'Figma, Photoshop, Illustrator 등의 툴 사용 능력', 0.2, 1),
                                                                                     (8,2, 'UI/UX 이해도', '사용자 중심의 사고와 UX 설계 능력 평가', 0.2, 1),
                                                                                     (9,2, '커뮤니케이션', '기획자, 개발자와의 협업 경험 및 커뮤니케이션 능력', 0.2, 1),
                                                                                     (10,2, '경력 적합성', '우리 조직과 과업에 잘 맞는 경력인지 여부', 0.2, 1);

INSERT INTO interview_criteria (id, sheet_id, title, content, weight, member_id) VALUES
                                                                                     (11,3, '시장 분석력', '데이터 기반으로 시장을 분석할 수 있는 역량', 0.2, 1),
                                                                                     (12,3, '전략 수립 능력', '마케팅 전략을 논리적으로 설계하고 설명할 수 있는가', 0.2, 1),
                                                                                     (13,3, '성과 중심 사고', 'ROI, CPA 등 퍼포먼스 중심으로 사고할 수 있는가', 0.2, 1),
                                                                                     (14,3, '조직 커뮤니케이션', '타 부서와 조율하는 능력 및 리더십', 0.2, 1),
                                                                                     (15,3, '프로젝트 경험', '실제 진행한 캠페인 또는 프로젝트 경험 설명', 0.2, 1);


INSERT INTO interview (
    id,
    application_id,
    sheet_id,
    datetime,
    address,
    score
) VALUES (
             1,
             1,  -- 지원서 ID
             1,  -- 면접 평가표 ID
             '2025-07-01 10:00:00',
             'https://us02web.zoom.us/j/86156493521?pwd=IqHVacToBB5a2hbsm6NjJ2HOCxJm8W.1#success',
             NULL
         ),
         (
             2,
             2,  -- 지원서 ID
             1,  -- 면접 평가표 ID
             '2025-07-01 10:30:00',
             'https://us02web.zoom.us/j/86156493521?pwd=IqHVacToBB5a2hbsm6NjJ2HOCxJm8W.1#success',
             NULL
         ),
         (
             3,
             3,  -- 지원서 ID
             3,  -- 면접 평가표 ID
             '2025-07-01 11:00:00',
             'https://us02web.zoom.us/j/86156493521?pwd=IqHVacToBB5a2hbsm6NjJ2HOCxJm8W.1#success',
             NULL
         );

INSERT INTO interviewer (id, interview_id, member_id, score, review) VALUES
                                                                         (1,1, 5, 80, NULL),
                                                                         (2,1, 6, 80, NULL),
                                                                         (3,1, 7, 90, NULL),
                                                                         (4, 2, 20, NULL, NULL),
                                                                         (5, 2, 21, NULL, NULL);

-- interviewer 1 (member 2)
INSERT INTO interview_score (id, interviewer_id, criteria_id, score, review) VALUES
                                                                                 (1,1, 1, 80, '기초 개념이 전반적으로 잘 잡혀 있음'),
                                                                                 (2,1, 2, 90, '코드 구현이 깔끔하고 명확함'),
                                                                                 (3,1, 3, 70, '문제 접근 방식이 직관적이나 설명이 부족함'),
                                                                                 (4,1, 4, 80, '협업 태도가 좋고 피드백을 잘 수용함'),
                                                                                 (5,1, 5, 80, '기술 습득 속도가 빠를 것으로 기대됨');

-- interviewer 2 (member 3)
INSERT INTO interview_score (id, interviewer_id, criteria_id, score, review) VALUES
                                                                                 (6,2, 1, 70, '기초 지식에 다소 미흡함이 있음'),
                                                                                 (7,2, 2, 80, '구현 속도는 좋지만 일부 로직이 비효율적임'),
                                                                                 (8,2, 3, 80, '문제 해결 방식이 논리적임'),
                                                                                 (9,2, 4, 70, '조금 수동적인 인상이 있음'),
                                                                                 (10,2, 5, 100, '전반적으로 성장 가능성 있음');

-- interviewer 3 (member 4)
INSERT INTO interview_score (id, interviewer_id, criteria_id, score, review) VALUES
                                                                                 (11,3, 1, 100, '기초 이론에 강하고 설명이 명확함'),
                                                                                 (12,3, 2, 80, '테스트 코드 작성이 인상적임'),
                                                                                 (13,3, 3, 90, '창의적인 해결 방식을 보여줌'),
                                                                                 (14,3, 4, 90, '소통 능력 뛰어남'),
                                                                                 (15,3, 5, 90, '성장 가능성 매우 높음');



INSERT INTO mail (id, applicant_id, email, title, content, sender_id, sended_at) VALUES
                                                                                     (1,1, 'applicant1@example.com', '서류 합격 통보', '축하드립니다! 서류 전형에 합격하셨습니다.', 1, NOW() - INTERVAL 10 DAY),
                                                                                     (2,2, 'applicant2@example.com', '면접 일정 안내', '1차 면접은 7월 1일 10시에 진행됩니다.', 1, NOW() - INTERVAL 9 DAY),
                                                                                     (3,3, 'applicant3@example.com', '실무 테스트 요청', '실무 테스트 링크를 확인해주세요.', 1, NOW() - INTERVAL 8 DAY),
                                                                                     (4,4, 'applicant4@example.com', '최종 합격 안내', '축하드립니다! 최종 합격을 알려드립니다.', 1, NOW() - INTERVAL 7 DAY),
                                                                                     (5,5, 'applicant5@example.com', '면접 장소 안내', '면접 장소는 강남 본사 3층 회의실입니다.', 1, NOW() - INTERVAL 6 DAY),

                                                                                     (6,1, 'applicant1@example.com', '실무 테스트 제출 안내', '제출 마감은 6월 25일입니다.', 1, NOW() - INTERVAL 5 DAY),
                                                                                     (7,2, 'applicant2@example.com', '면접 일정 변경 안내', '면접 일정이 7월 2일로 변경되었습니다.', 1, NOW() - INTERVAL 5 DAY),
                                                                                     (8,3, 'applicant3@example.com', '불합격 안내', '아쉽게도 이번 채용에서는 합격하지 못하셨습니다.', 1, NOW() - INTERVAL 4 DAY),
                                                                                     (9,4, 'applicant4@example.com', '포트폴리오 확인 요청', '포트폴리오 자료를 다시 제출해주세요.', 1, NOW() - INTERVAL 3 DAY),
                                                                                     (10,5, 'applicant5@example.com', '채용 관련 안내', '추가 서류 제출 요청 드립니다.', 1, NOW() - INTERVAL 3 DAY),

                                                                                     (11,1, 'applicant1@example.com', '면접 피드백 공유', '면접관 피드백을 전달드립니다.', 1, NOW() - INTERVAL 2 DAY),
                                                                                     (12,2, 'applicant2@example.com', '최종 결과 안내', '지원해주셔서 감사합니다. 결과를 확인해주세요.', 1, NOW() - INTERVAL 2 DAY),
                                                                                     (13,3, 'applicant3@example.com', '서류 확인 요청', '이력서가 누락되어 확인 부탁드립니다.', 1, NOW() - INTERVAL 2 DAY),
                                                                                     (14,4, 'applicant4@example.com', '입사 일정 조율', '입사 예정일 관련 회신 바랍니다.', 1, NOW() - INTERVAL 1 DAY),
                                                                                     (15,5, 'applicant5@example.com', '추가 질문 안내', '면접 시 질문 사항에 대한 추가 안내입니다.', 1, NOW() - INTERVAL 1 DAY),

                                                                                     (16,1, 'applicant1@example.com', '채용 일정 안내', '전체 채용 프로세스 일정을 공유드립니다.', 1, NOW()),
                                                                                     (17,2, 'applicant2@example.com', '면접 준비 자료', '면접 준비에 참고할 자료를 첨부합니다.', 1, NOW()),
                                                                                     (18,3, 'applicant3@example.com', '기술 과제 피드백', '제출하신 과제에 대한 피드백입니다.', 1, NOW()),
                                                                                     (19,4, 'applicant4@example.com', '입사 확정 안내', '입사 확정 및 계약 관련 안내입니다.', 1, NOW()),
                                                                                     (20,5, 'applicant5@example.com', '지원 감사 메일', '지원해주셔서 감사합니다.', 1, NOW());


INSERT INTO attendance_category (`id`, `status`, `created_at`, `updated_at`)
VALUES
    (1, 1, NOW(), NOW()),  -- 출근
    (2, 2, NOW(), NOW()),  -- 퇴근
    (3, 3, NOW(), NOW()),  -- 지각
    (4, 4, NOW(), NOW());  -- 조퇴

-- 2025-06-17 ~ 2025-06-21까지 정상 출근 및 퇴근 기록
INSERT INTO attendance_record (member_id, attendance_category_id, record_time, status) VALUES
                                                                                           (1, 1, '2025-06-17 09:01:00', 0),  -- 출근 (지각)
                                                                                           (1, 2, '2025-06-17 18:05:00', 0),  -- 퇴근

                                                                                           (1, 1, '2025-06-18 08:55:00', 0),  -- 출근 (정상)
                                                                                           (1, 2, '2025-06-18 18:01:00', 0),  -- 퇴근


                                                                                           (1, 1, '2025-06-20 08:58:00', 0),  -- 출근
                                                                                           (1, 2, '2025-06-20 18:02:00', 0),  -- 퇴근

                                                                                           (1, 1, '2025-06-21 09:00:00', 0),  -- 출근
                                                                                           (1, 2, '2025-06-21 17:59:00', 0);  -- 퇴근


INSERT INTO attendance_record (`member_id`, `attendance_category_id`, `record_time`, `status`, `created_at`, `updated_at`)
VALUES
-- 테스트사원1 - 출근/퇴근
-- id 1~20
(20, 1, '2025-06-01 09:00:00', 0, NOW(), NOW()),
(20, 1, '2025-06-02 09:00:00', 0, NOW(), NOW()),
(20, 1, '2025-06-03 09:00:00', 0, NOW(), NOW()),
(20, 1, '2025-06-04 09:00:00', 0, NOW(), NOW()),
(20, 1, '2025-06-05 09:00:00', 0, NOW(), NOW()),
(20, 1, '2025-06-06 09:00:00', 0, NOW(), NOW()),
(20, 1, '2025-06-07 09:00:00', 0, NOW(), NOW()),
(20, 1, '2025-06-08 09:00:00', 0, NOW(), NOW()),
(20, 1, '2025-06-09 09:00:00', 0, NOW(), NOW()),
(20, 1, '2025-06-10 09:00:00', 0, NOW(), NOW()),
(20, 2, '2025-06-01 18:00:00', 0, NOW(), NOW()),
(20, 2, '2025-06-02 18:00:00', 0, NOW(), NOW()),
(20, 2, '2025-06-03 18:00:00', 0, NOW(), NOW()),
(20, 2, '2025-06-04 18:00:00', 0, NOW(), NOW()),
(20, 2, '2025-06-05 18:00:00', 0, NOW(), NOW()),
(20, 2, '2025-06-06 18:00:00', 0, NOW(), NOW()),
(20, 2, '2025-06-07 18:00:00', 0, NOW(), NOW()),
(20, 2, '2025-06-08 18:00:00', 0, NOW(), NOW()),
(20, 2, '2025-06-09 18:00:00', 0, NOW(), NOW()),
(20, 2, '2025-06-10 18:00:00', 0, NOW(), NOW()),

-- 테스트사원2 - id 21~40
(19, 1, '2025-06-01 09:05:00', 0, NOW(), NOW()),
(19, 1, '2025-06-02 09:05:00', 0, NOW(), NOW()),
(19, 1, '2025-06-03 09:05:00', 0, NOW(), NOW()),
(19, 1, '2025-06-04 09:05:00', 0, NOW(), NOW()),
(19, 1, '2025-06-05 09:05:00', 0, NOW(), NOW()),
(19, 1, '2025-06-06 09:05:00', 0, NOW(), NOW()),
(19, 1, '2025-06-07 09:05:00', 0, NOW(), NOW()),
(19, 1, '2025-06-08 09:05:00', 0, NOW(), NOW()),
(19, 1, '2025-06-09 09:05:00', 0, NOW(), NOW()),
(19, 1, '2025-06-10 09:05:00', 0, NOW(), NOW()),
(19, 2, '2025-06-01 18:10:00', 0, NOW(), NOW()),
(19, 2, '2025-06-02 18:10:00', 0, NOW(), NOW()),
(19, 2, '2025-06-03 18:10:00', 0, NOW(), NOW()),
(19, 2, '2025-06-04 18:10:00', 0, NOW(), NOW()),
(19, 2, '2025-06-05 18:10:00', 0, NOW(), NOW()),
(19, 2, '2025-06-06 18:10:00', 0, NOW(), NOW()),
(19, 2, '2025-06-07 18:10:00', 0, NOW(), NOW()),
(19, 2, '2025-06-08 18:10:00', 0, NOW(), NOW()),
(19, 2, '2025-06-09 18:10:00', 0, NOW(), NOW()),
(19, 2, '2025-06-10 18:10:00', 0, NOW(), NOW()),

-- 인사맨 - id 41~60
(21, 1, '2025-06-01 08:50:00', 0, NOW(), NOW()),
(21, 1, '2025-06-02 08:50:00', 0, NOW(), NOW()),
(21, 1, '2025-06-03 08:50:00', 0, NOW(), NOW()),
(21, 1, '2025-06-04 08:50:00', 0, NOW(), NOW()),
(21, 1, '2025-06-05 08:50:00', 0, NOW(), NOW()),
(21, 1, '2025-06-06 08:50:00', 0, NOW(), NOW()),
(21, 1, '2025-06-07 08:50:00', 0, NOW(), NOW()),
(21, 1, '2025-06-08 08:50:00', 0, NOW(), NOW()),
(21, 1, '2025-06-09 08:50:00', 0, NOW(), NOW()),
(21, 1, '2025-06-10 08:50:00', 0, NOW(), NOW()),
(21, 2, '2025-06-01 18:30:00', 0, NOW(), NOW()),
(21, 2, '2025-06-02 18:30:00', 0, NOW(), NOW()),
(21, 2, '2025-06-03 18:30:00', 0, NOW(), NOW()),
(21, 2, '2025-06-04 18:30:00', 0, NOW(), NOW()),
(21, 2, '2025-06-05 18:30:00', 0, NOW(), NOW()),
(21, 2, '2025-06-06 18:30:00', 0, NOW(), NOW()),
(21, 2, '2025-06-07 18:30:00', 0, NOW(), NOW()),
(21, 2, '2025-06-08 18:30:00', 0, NOW(), NOW()),
(21, 2, '2025-06-09 18:30:00', 0, NOW(), NOW()),
(21, 2, '2025-06-10 18:30:00', 0, NOW(), NOW());