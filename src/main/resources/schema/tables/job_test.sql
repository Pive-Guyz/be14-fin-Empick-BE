SET foreign_key_checks = 0;

DROP TABLE IF EXISTS `job_test_evaluation_result`;
DROP TABLE IF EXISTS `job_test_evaluation_criteria`;
DROP TABLE IF EXISTS `grading_result`;
DROP TABLE IF EXISTS `question_grading_criteria`;
DROP TABLE IF EXISTS `answer`;
DROP TABLE IF EXISTS `job_test_question`;
DROP TABLE IF EXISTS `question_option`;
DROP TABLE IF EXISTS `question`;
DROP TABLE IF EXISTS `application_job_test`;
DROP TABLE IF EXISTS `job_test`;
DROP TABLE IF EXISTS `job_test_type`;

SET foreign_key_checks = 1;

# ========================== 실무 테스트 유형
CREATE TABLE job_test_type
(
    id                INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(255) NOT NULL,
    description       LONGTEXT     NULL,
    created_at        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        DATETIME     NULL,
    created_member_id INT          NOT NULL,
    updated_member_id INT          NULL,

    FOREIGN KEY (`created_member_id`) REFERENCES `member` (`id`),
    FOREIGN KEY (`updated_member_id`) REFERENCES `member` (`id`)
);


# ========================== 실무 테스트
CREATE TABLE job_test
(
    id                INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title             VARCHAR(255) NOT NULL,
    difficulty        TINYINT      NOT NULL,
    test_time         INT          NOT NULL,
    created_at        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        DATETIME     NULL,
    job_test_type_id  INT          NOT NULL,
    created_member_id INT          NOT NULL,
    updated_member_id INT          NULL,

    FOREIGN KEY (`job_test_type_id`) REFERENCES `job_test_type` (`id`),
    FOREIGN KEY (`created_member_id`) REFERENCES `member` (`id`),
    FOREIGN KEY (`updated_member_id`) REFERENCES `member` (`id`)
);


# ========================== 지원서별 실무 테스트
CREATE TABLE application_job_test
(
    id                  INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    assigned_at         DATETIME NOT NULL,
    evaluator_comment   LONGTEXT NULL,
    submitted_at        DATETIME NOT NULL,
    grading_total_score INT      NULL,
    evaluation_score    INT      NULL,
    grading_status      TINYINT  NOT NULL DEFAULT 0,
    evaluation_status   TINYINT  NOT NULL DEFAULT 0,
    application_id      INT      NOT NULL,
    job_test_id         INT      NOT NULL,
    member_id           INT      NOT NULL,

    FOREIGN KEY (`application_id`) REFERENCES `application`(`id`),
    FOREIGN KEY (`job_test_id`) REFERENCES `job_test` (`id`),
    FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
);


# ========================== 실무 테스트 문제
CREATE TABLE question
(
    id                INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    content           LONGTEXT     NOT NULL,
    detail_content    LONGTEXT     NULL,
    type              TINYINT      NOT NULL,
    difficulty        TINYINT      NOT NULL,
    answer            VARCHAR(255) NULL,
    created_at        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        DATETIME     NULL,
    created_member_id INT          NOT NULL,
    updated_member_id INT          NULL,

    FOREIGN KEY (`created_member_id`) REFERENCES `member` (`id`),
    FOREIGN KEY (`updated_member_id`) REFERENCES `member` (`id`)
);

# ========================== 실무 테스트 문제별 선택지
CREATE TABLE question_option
(
    id            INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    option_number INT          NOT NULL,
    content       VARCHAR(255) NOT NULL,
    question_id   INT          NOT NULL,

    FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
);


# ========================== 실무 테스트별 문제
CREATE TABLE job_test_question
(
    id            INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    score         INT NOT NULL,
    option_number INT NOT NULL,
    job_test_id   INT NOT NULL,
    question_id   INT NOT NULL,

    FOREIGN KEY (`job_test_id`) REFERENCES `job_test` (`id`),
    FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
);


# ========================== 지원서별 문제 답변
CREATE TABLE answer
(
    id                      INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    content                 LONGTEXT NOT NULL,
    attempt                 INT      NOT NULL DEFAULT 1,
    is_correct              TINYINT  NULL,
    application_job_test_id INT      NOT NULL,
    job_test_question_id    INT      NOT NULL,

    FOREIGN KEY (`application_job_test_id`) REFERENCES `application_job_test` (`id`),
    FOREIGN KEY (`job_test_question_id`) REFERENCES `job_test_question` (`id`)
);


# ========================== 실무 테스트 문제별 채점 기준
CREATE TABLE question_grading_criteria
(
    id             INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    content        LONGTEXT NOT NULL,
    detail_content LONGTEXT NULL,
    score_weight   DOUBLE   NOT NULL,
    question_id    INT      NOT NULL,

    FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
);


# ========================== 실무 테스트 답안 채점 결과
CREATE TABLE grading_result
(
    id                           INT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    evaluator_comment            LONGTEXT   NULL,
    is_satisfied                 VARCHAR(4) NOT NULL,
    answer_id                    INT        NOT NULL,
    question_grading_criteria_id INT        NOT NULL,

    FOREIGN KEY (`answer_id`) REFERENCES `answer` (`id`)

);


# ========================== 실무 테스트 평가 기준
CREATE TABLE job_test_evaluation_criteria
(
    id             INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    content        LONGTEXT NOT NULL,
    detail_content LONGTEXT NOT NULL,
    score_weight   DOUBLE   NOT NULL,
    job_test_id    INT      NOT NULL,

    FOREIGN KEY (`job_test_id`) REFERENCES `job_test` (`id`)
);


# ========================== 실무 테스트 평가 결과
CREATE TABLE job_test_evaluation_result
(
    id                              INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    evaluator_comment               LONGTEXT NULL,
    score                           INT      NOT NULL,
    application_job_test_id         INT      NOT NULL,
    job_test_evaluation_criteria_id INT      NOT NULL,

    FOREIGN KEY (`application_job_test_id`) REFERENCES `application_job_test` (`id`),
    FOREIGN KEY (`job_test_evaluation_criteria_id`) REFERENCES `job_test_evaluation_criteria` (`id`)
);
