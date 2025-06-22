package com.piveguyz.empickbackend.employment.introduce.query.dto;

import com.piveguyz.empickbackend.employment.introduce.command.domain.aggregate.IntroduceRatingResultStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IntroduceResultQueryDTO {
    private int id;
    private int memberId;
    private String content;
    private int ratingScore;
    private int introduceStandardId;
    private Integer introduceId;
    private int updatedBy;
    private LocalDateTime updatedAt;
}
