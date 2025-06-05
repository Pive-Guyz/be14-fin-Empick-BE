SET foreign_key_checks = 0;

DROP TABLE IF EXISTS `interview_sheet`;
DROP TABLE IF EXISTS `interview_criteria`;
DROP TABLE IF EXISTS `interview_sheet_item`;
DROP TABLE IF EXISTS `interview`;
DROP TABLE IF EXISTS `interview_score`;

SET foreign_key_checks = 1;

CREATE TABLE `interview_sheet` (
   `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
   `name`	VARCHAR(255)	NOT NULL COMMENT '이름',
   `is_deleted`	VARCHAR(4)	NOT NULL	DEFAULT 'N' COMMENT '삭제 여부',
   `member_id`	INT NULL COMMENT '수정자 id',
   `updated_at`	DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
   FOREIGN KEY (`member_id`) REFERENCES `member`(`id`)
)
COMMENT = '면접 평가 기준표';

CREATE TABLE `interview_criteria` (
  `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
  `content`	LONGTEXT	NOT NULL COMMENT '내용',
  `detail_content`	LONGTEXT	NOT NULL COMMENT '상세 내용',
  `is_deleted`	VARCHAR(4)	NOT NULL	DEFAULT 'N' COMMENT '삭제 여부',
  `member_id`	INT	NULL COMMENT '수정자 id',
  `updated_at`	DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
  FOREIGN KEY (`member_id`) REFERENCES `member`(`id`)
)
COMMENT = '면접 평가 기준';

CREATE TABLE `interview_sheet_item` (
    `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    `sheet_id`	INT	NOT NULL COMMENT '평가표 id',
    `criteria_id`	INT	NOT NULL COMMENT '면접 평가 기준 id',
    `weight`	INT	NOT NULL COMMENT '가중치',
    `member_id`	INT NULL COMMENT '수정자 id',
    `updated_at`	DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    FOREIGN KEY (`sheet_id`) REFERENCES `interview_sheet`(`id`),
    FOREIGN KEY (`criteria_id`) REFERENCES `interview_criteria`(`id`),
    FOREIGN KEY (`member_id`) REFERENCES `member`(`id`)
)
COMMENT = '평가 기준표별 평가 기준';

CREATE TABLE `interview` (
     `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
     `sheet_id`	INT	NOT NULL COMMENT '평가표 id',
     `date`	DATETIME	NOT NULL COMMENT '날짜',
     `address`	LONGTEXT	NOT NULL COMMENT '주소',
     `score`	DOUBLE	NULL COMMENT '점수',
     `interview_review`	LONGTEXT	NULL COMMENT '면접 평가',
     FOREIGN KEY (`sheet_id`) REFERENCES `interview_sheet`(`id`)
)
COMMENT = '면접';

CREATE TABLE `interview_score` (
   `id`	INT	NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
   `interview_id`	INT	NOT NULL COMMENT '면접 id',
   `interviewer_id`	INT	NOT NULL COMMENT '면접 담당자 id',
   `item_id`	INT	NOT NULL COMMENT '평가 기준 id',
   `score`	DOUBLE	NULL COMMENT '점수',
   `review`	LONGTEXT    NULL COMMENT '평가',
   FOREIGN KEY (`interview_id`) REFERENCES `interview`(`id`),
   FOREIGN KEY (`interviewer_id`) REFERENCES `member`(`id`),
   FOREIGN KEY (`item_id`) REFERENCES `interview_sheet_item`(`id`)
)
COMMENT = '평가 기준별 점수';