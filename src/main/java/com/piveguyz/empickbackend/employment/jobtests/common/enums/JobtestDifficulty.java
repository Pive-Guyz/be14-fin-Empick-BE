package com.piveguyz.empickbackend.employment.jobtests.common.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum JobtestDifficulty {
    EASY(0),
    MEDIUM(1),
    HARD(2);

    private final int code;

    JobtestDifficulty(int code) {
        this.code = code;
    }

    public static JobtestDifficulty fromCode(int code) {
        return Arrays.stream(JobtestDifficulty.values())
                .filter(d -> d.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid difficulty code: " + code)); // 🚩 Exception Handler로 수정해야 함
    }
}
