package com.piveguyz.empickbackend.employment.introduce.command.domain.aggregate;

import com.piveguyz.empickbackend.employment.introduce.command.application.dto.IntroduceRatingResultUpdateCommandDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "introduce_rating_result")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntroduceRatingResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @Column(name = "rating_score", nullable = false)
    private Integer ratingScore;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "member_id", nullable = false)
    private Integer memberId;

    @Column(name = "introduce_standard_id", nullable = false)
    private Integer introduceStandardId;

    @Column(name = "introduce_id", nullable = false)
    private Integer introduceId;

    public void updateIntroduceRatingResultEntity(IntroduceRatingResultUpdateCommandDTO dto) {
        this.content = dto.getContent();
        this.ratingScore = dto.getRatingScore();
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = dto.getUpdatedBy();
    }
}