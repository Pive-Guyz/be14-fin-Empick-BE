SET foreign_key_checks = 0;

DROP TABLE IF EXISTS `mail`;
DROP TABLE IF EXISTS `mail_template`;

SET foreign_key_checks = 1;

CREATE TABLE `mail` (
    `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    `applicant_id` INT NOT NULL COMMENT '지원자 id',
    `email` VARCHAR(255) NOT NULL COMMENT '이메일',
    `content` LONGTEXT NOT NULL COMMENT '내용',
    `sender_id` INT NOT NULL COMMENT '발송자 id',
    `sended_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '발송 일시',
    FOREIGN KEY (`applicant_id`) REFERENCES `applicant`(`id`),
    FOREIGN KEY (`sender_id`) REFERENCES `member`(`id`)
)
COMMENT = '안내 메일';

CREATE TABLE `mail_template` (
     `id`	INT	NOT NULL PRIMARY KEY  AUTO_INCREMENT COMMENT 'id',
     `title`	VARCHAR(255)	NOT NULL COMMENT '제목',
     `content`	LONGTEXT	NOT NULL COMMENT '내용',
     `is_deleted` VARCHAR(4) NOT NULL DEFAULT 'N' COMMENT '삭제 여부',
     `member_id` INT NULL COMMENT '수정자 id',
     `updated_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    FOREIGN KEY (`member_id`) REFERENCES `member`(`id`)
)
COMMENT = '안내 메일 템플릿';