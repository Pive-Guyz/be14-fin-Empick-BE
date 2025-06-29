package com.piveguyz.empickbackend.orgstructure.member.command.application.service;

import com.piveguyz.empickbackend.orgstructure.member.command.application.dto.MemberSignUpRequestDTO;
import com.piveguyz.empickbackend.orgstructure.member.command.application.dto.MemberSignUpResponseDTO;
import com.piveguyz.empickbackend.orgstructure.member.command.application.dto.MemberUpdateRequestDTO;

public interface MemberCommandService {
    MemberSignUpResponseDTO signUp(MemberSignUpRequestDTO request);

    void updateProfileImage(int memberId, String key);

    void clearProfileImage(int memberId);

    void resignMember(int memberId);
    
    void updateMyInfo(Integer memberId, MemberUpdateRequestDTO request);
}
