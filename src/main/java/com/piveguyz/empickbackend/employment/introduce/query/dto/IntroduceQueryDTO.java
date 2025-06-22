package com.piveguyz.empickbackend.employment.introduce.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IntroduceQueryDTO {
    private Integer id;
    private String content;
    private Integer applicantId;
    private Integer introduceTemplateId;
    private Integer applicationId;
}