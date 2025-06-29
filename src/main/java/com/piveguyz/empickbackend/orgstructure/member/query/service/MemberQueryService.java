package com.piveguyz.empickbackend.orgstructure.member.query.service;

import com.piveguyz.empickbackend.orgstructure.member.query.dto.MemberResponseDTO;
import com.piveguyz.empickbackend.orgstructure.member.query.dto.MemberRoleQueryDTO;

import java.util.List;

public interface MemberQueryService {
    MemberResponseDTO getMemberInfo(int memberId);

    String getProfileImageKey(int memberId);

    List<MemberResponseDTO>  getMembersByName(String name);

    List<MemberResponseDTO> getMembersByEmployeeNumber(int employeeNumbers);

    List<MemberRoleQueryDTO> getMemberRoles(int memberId);

    List<MemberResponseDTO> findAllMembers();

    MemberResponseDTO getMemberById(Integer id);
}
