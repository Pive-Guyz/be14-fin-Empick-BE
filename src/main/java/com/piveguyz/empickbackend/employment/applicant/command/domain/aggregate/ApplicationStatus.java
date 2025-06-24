package com.piveguyz.empickbackend.employment.applicant.command.domain.aggregate;

public enum ApplicationStatus {
    WAITING(0, "서류검토대기중"),
    PASSED_DOCS(1, "서류합격"),
    PASSED_PRACTICAL(2, "실무합격"),
    PASSED_INTERVIEW(3, "면접합격"),
    PASSED_FINAL(4, "최종합격"),
    REJECTED(5, "불합격");

    private final int code;
    private final String description;

    ApplicationStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ApplicationStatus fromCode(int code) {
        for (ApplicationStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid ApplicationStatus code: " + code);
    }
}
