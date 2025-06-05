package com.piveguyz.empickbackend.employment.jobtests.question.command.domain.aggregate;

import com.piveguyz.empickbackend.employment.jobtests.question.command.application.dto.UpdateQuestionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.common.enums.JobtestDifficulty;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.aggregate.enums.QuestionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@Table(name = "question")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "detail_content", nullable = true)
    private String detailContent;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    private QuestionType type;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "difficulty", nullable = false)
    private JobtestDifficulty difficulty;

    @Column(name = "answer", nullable = false)
    private String answer;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Column(name = "created_member_id", nullable = false)
    private int createdMemberId;

    @Column(name = "updated_member_id", nullable = true)
    private Integer updatedMemberId;


    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void updateQuestionEntity(UpdateQuestionCommandDTO dto) {
        if (dto.getContent() != null) {
            this.content = dto.getContent();
        }
        if (dto.getDetailContent() != null) {
            this.detailContent = dto.getDetailContent();
        }
        if (dto.getType() != null) {
            this.type = dto.getType();
        }
        if (dto.getDifficulty() != null) {
            this.difficulty = dto.getDifficulty();
        }
        if (dto.getAnswer() != null) {
            this.answer = dto.getAnswer();
        }
        this.updatedMemberId = dto.getUpdatedMemberId();
        this.updatedAt = LocalDateTime.now();
    }
}
