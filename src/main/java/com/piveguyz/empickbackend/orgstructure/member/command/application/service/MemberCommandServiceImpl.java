package com.piveguyz.empickbackend.orgstructure.member.command.application.service;

import com.piveguyz.empickbackend.auth.facade.AuthFacade;
import com.piveguyz.empickbackend.common.constants.RoleCode;
import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.mail.command.application.dto.MailCommandDTO;
import com.piveguyz.empickbackend.employment.mail.facade.MailFacade;
import com.piveguyz.empickbackend.infra.s3.dto.S3UploadResponseDTO;
import com.piveguyz.empickbackend.infra.s3.service.S3Service;
import com.piveguyz.empickbackend.orgstructure.member.command.application.dto.MemberSignUpRequestDTO;
import com.piveguyz.empickbackend.orgstructure.member.command.application.dto.MemberSignUpResponseDTO;
import com.piveguyz.empickbackend.orgstructure.member.command.application.dto.MemberUpdateRequestDTO;
import com.piveguyz.empickbackend.orgstructure.member.command.domain.aggregate.MemberEntity;
import com.piveguyz.empickbackend.orgstructure.member.command.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthFacade authFacade;
    private final S3Service s3Service;
    private final MailFacade mailFacade;

    @Override
    @Transactional
    public MemberSignUpResponseDTO signUp(MemberSignUpRequestDTO request) {
        // 🔥 Step 1️⃣: 현재 로그인 사용자 ID 조회
        Integer createdMemberId = authFacade.getCurrentMemberId();

        // 🔥 Step 2️⃣: 권한 체크 (퍼사드 사용 + enum)
        authFacade.checkHasRole(RoleCode.ROLE_HR_ACCESS);

        // 🔥 Step 3️⃣: 입사처리자(현재 로그인 사용자) 조회
        MemberEntity createdMember = memberRepository.findById(createdMemberId)
                .orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_CREATED_MEMBER_NOT_FOUND));

        // 🔥 Step 4️⃣: 이메일 중복 체크
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ResponseCode.MEMBER_EMAIL_DUPLICATED);
        }

        // 🔥 Step 5️⃣: UUID 생성 및 S3 프로필 이미지 업로드
        String profileImageUrl;
        if (request.getProfileImage() != null && !request.getProfileImage().isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + ".png";

            S3UploadResponseDTO s3Response = s3Service.uploadFile("profiles", fileName, request.getProfileImage());
            profileImageUrl = s3Response.getKey(); // "profiles/uuid.png"
        } else {
            throw new BusinessException(ResponseCode.MEMBER_PROFILE_IMAGE_REQUIRED);
        }

        // 🔥 Step 6️⃣: EmployeeNumber 생성
        Integer employeeNumber = generateUniqueEmployeeNumber();

        Integer defaultRankId = 1; // RANK_EMP (사원) - ROLE_USER 권한

        // 🔥 Step 7️⃣: 신규 사원 생성
        MemberEntity member = MemberEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(employeeNumber.toString()))
                .name(request.getName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .birth(request.getBirth())
                .pictureUrl(profileImageUrl) // S3 key 저장 (profiles/uuid.png)
                .employeeNumber(employeeNumber)
                .hireAt(request.getHireAt())
                .status(1) // 기본 활성 상태
                .rankId(defaultRankId)
                .createdAt(LocalDateTime.now())
                .createdMemberId(createdMemberId)
                .build();

        MemberEntity savedMember = memberRepository.save(member);

        // 🔥 Step 8️⃣: 이메일 발송 (사번 전달)
        MailCommandDTO mailDto = new MailCommandDTO(
                null, // id (새로 생성되므로 null)
                null, // applicantId (사원 등록이므로 null)
                savedMember.getEmail(), // email
                "[EMPICK] 사원 등록 완료 - 사번 안내", // title
                String.format(
                        "안녕하세요 %s님,\n\n" +
                                "사원 등록이 완료되었습니다.\n\n" +
                                "📋 사번: %d\n" +
                                "🔑 초기 비밀번호: %d\n\n" +
                                "로그인 후 반드시 비밀번호를 변경해주세요.\n\n" +
                                "감사합니다.",
                        savedMember.getName(),
                        savedMember.getEmployeeNumber(),
                        savedMember.getEmployeeNumber()
                ), // content
                createdMemberId, // senderId (입사처리자)
                null // sendedAt (발송 시 자동 설정)
        );

        mailFacade.sendMail(mailDto);

        return MemberSignUpResponseDTO.builder()
                .id(savedMember.getId())
                .email(savedMember.getEmail())
                .name(savedMember.getName())
                .employeeNumber(savedMember.getEmployeeNumber())
                .createdAt(LocalDate.from(savedMember.getCreatedAt()))
                .build();
    }

    @Override
    @Transactional
    public void updateProfileImage(int memberId, String key) {
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));

        member.updateProfileImageUrl(key);
    }

    @Override
    @Transactional
    public void clearProfileImage(int memberId) {
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));

        member.clearProfileImageUrl();
    }

    @Override
    @Transactional
    public void resignMember(int memberId) {
        Integer currentMemberId = authFacade.getCurrentMemberId();

        authFacade.checkHasRole(RoleCode.ROLE_HR_ACCESS);

        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));

        if (member.getResignAt() != null) {
            throw new BusinessException(ResponseCode.ALREDY_RESIGNED);
        }

        member.resign(LocalDateTime.now(), currentMemberId);
    }

    @Override
    @Transactional
    public void updateMyInfo(Integer memberId, MemberUpdateRequestDTO request) {
        // 현재 로그인 사용자 확인
        Integer currentMemberId = authFacade.getCurrentMemberId();
        
        // memberId가 null인 경우 현재 로그인한 사용자 ID 사용
        Integer targetMemberId = memberId != null ? memberId : currentMemberId;
        
        // 본인 정보만 수정 가능
        if (!currentMemberId.equals(targetMemberId)) {
            throw new BusinessException(ResponseCode.MEMBER_UPDATE_PERMISSION_DENIED);
        }

        // 사원 조회
        MemberEntity member = memberRepository.findById(targetMemberId)
                .orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));

        // 이메일 중복 체크 (본인 제외)
        if (!member.getEmail().equals(request.getEmail()) && 
            memberRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ResponseCode.MEMBER_EMAIL_DUPLICATED);
        }

        // 정보 업데이트 (사용자가 수정 가능한 필드만)
        member.updateName(request.getName());
        member.updatePhone(request.getPhone());
        member.updateEmail(request.getEmail());
        
        // updatedMemberId 설정
        member.setUpdatedMemberId(currentMemberId);
    }

    /**
     * 랜덤한 6자리 EmployeeNumber를 생성하고 중복 방지.
     */
    private Integer generateUniqueEmployeeNumber() {
        int maxAttempts = 10;
        for (int i = 0; i < maxAttempts; i++) {
            int randomNumber = (int) (Math.random() * 900000) + 100000; // 100000 ~ 999999
            if (!memberRepository.existsByEmployeeNumber(randomNumber)) {
                return randomNumber;
            }
        }
        throw new BusinessException(ResponseCode.MEMBER_EMPLOYEE_NUMBER_DUPLICATE);
    }
}