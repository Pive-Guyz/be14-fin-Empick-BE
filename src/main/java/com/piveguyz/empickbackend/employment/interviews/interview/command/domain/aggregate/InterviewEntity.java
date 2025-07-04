package com.piveguyz.empickbackend.employment.interviews.interview.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="interview")
public class InterviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement
    @Column(name = "id")
    private Integer id;

    @Column(name = "application_id")
    private Integer applicationId;

    @Column(name = "sheet_id")
    private Integer sheetId;

    @Column(name = "datetime")
    private LocalDateTime datetime;

    @Column(name = "address")
    private String address;

    @Column(name = "score")
    private Double score;
}
