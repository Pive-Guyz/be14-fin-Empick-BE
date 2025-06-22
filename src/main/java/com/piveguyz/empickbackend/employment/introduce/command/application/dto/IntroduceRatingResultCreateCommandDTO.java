package com.piveguyz.empickbackend.employment.introduce.command.application.dto;

import com.piveguyz.empickbackend.employment.introduce.command.domain.aggregate.IntroduceRatingResultStatus;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntroduceRatingResultCreateCommandDTO {
    private Integer id;
    private String content;
    private Integer ratingScore;
    private LocalDateTime updatedAt;
    private Integer updatedBy;
    private Integer memberId;
    private Integer introduceStandardId;
    private Integer introduceId;
}
