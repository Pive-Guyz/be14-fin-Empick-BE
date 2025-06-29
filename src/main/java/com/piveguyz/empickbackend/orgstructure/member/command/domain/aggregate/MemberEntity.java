package com.piveguyz.empickbackend.orgstructure.member.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "member",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_member_employee_number", columnNames = "employee_number"),
                @UniqueConstraint(name = "uk_member_email", columnNames = "email")
        }
)
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "employee_number", nullable = false)
    private Integer employeeNumber;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "birth", length = 255)
    private String birth;

    @Column(name = "phone", nullable = false, length = 255)
    private String phone;

    // uuid를 백에서 생성하고 경로를 저장해야 하므로 프론트에서는
    @Column(name = "picture_url", nullable = false, length = 255)
    private String pictureUrl;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "vacation_count", nullable = false)
    private int vacationCount;

    @Column(name = "hire_at", insertable = false)
    private LocalDateTime hireAt;

    @Column(name = "resign_at")
    private LocalDateTime resignAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_member_id")
    private Integer createdMemberId;

    @Column(name = "deleted_member_id")
    private Integer deletedMemberId;

    @Column(name = "updated_member_id")
    private Integer updatedMemberId;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "position_id")
    private Integer positionId;

    @Column(name = "job_id")
    private Integer jobId;

    @Column(name = "rank_id")
    private Integer rankId;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void updateProfileImageUrl(String key) {
        this.pictureUrl = key;
    }

    public void clearProfileImageUrl() {
        this.pictureUrl = null;
    }

    // FK 필드 업데이트 메서드
    public void updateDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public void updatePositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public void updateJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public void updateRankId(Integer rankId) {
        this.rankId = rankId;
    }

    // String 필드 업데이트 메서드
    public void updateName(String requestedValue) {
        this.name = requestedValue;
    }

    public void updatePhone(String requestedValue) {
        this.phone = requestedValue;
    }

    public void updateEmail(String requestedValue) {
        this.email = requestedValue;
    }

    public void updateAddress(String requestedValue) {
        this.address = requestedValue;
    }

    public void updatePictureUrl(String requestedValue) {
        this.pictureUrl = requestedValue;
    }

    public void updateBirth(String requestedValue) {
        this.birth = requestedValue;
    }

    public void resign(LocalDateTime resignAt, int deletedMemberId) {
        this.resignAt = resignAt;
        this.status = 0; // 비활성
        this.deletedMemberId = deletedMemberId;
    }
}
