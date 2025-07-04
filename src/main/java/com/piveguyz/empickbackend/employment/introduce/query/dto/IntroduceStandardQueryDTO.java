package com.piveguyz.empickbackend.employment.introduce.query.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IntroduceStandardQueryDTO {
    private Integer id;
    private String content;
    private Integer memberId;
    private List<IntroduceStandardItemQueryDTO> items;
}
