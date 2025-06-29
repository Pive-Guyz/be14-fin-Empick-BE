package com.piveguyz.empickbackend.orgstructure.member.query.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.orgstructure.member.query.dto.MemberResponseDTO;
import com.piveguyz.empickbackend.orgstructure.member.query.dto.MemberRoleQueryDTO;
import com.piveguyz.empickbackend.orgstructure.member.query.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberMapper memberMapper;

    @Override
    public MemberResponseDTO getMemberInfo(int memberId) {
        MemberResponseDTO member = memberMapper.findMemberById(memberId);
        if (member == null) {
            throw new BusinessException(ResponseCode.MEMBER_NOT_FOUND);
        }
        return member;
    }

    @Override
    public List<MemberResponseDTO> getMembersByName(String name) {
        List<MemberResponseDTO> members = memberMapper.findMembersByName(name);
        if (members.isEmpty()) {
            throw new BusinessException(ResponseCode.MEMBER_NOT_FOUND);
        }
        return members;
    }

    @Override
    public List<MemberResponseDTO> getMembersByEmployeeNumber(int employeeNumber) {
        List<MemberResponseDTO> members = memberMapper.findMembersByEmployeeNumber(employeeNumber);
        if (members.isEmpty()) {
            throw new BusinessException(ResponseCode.MEMBER_NOT_FOUND);
        }
        return members;
    }

    @Override
    public List<MemberRoleQueryDTO> getMemberRoles(int employeeNumber) {
        List<MemberRoleQueryDTO> memberRoles = memberMapper.findRolesByEmployeeNumber(employeeNumber);
        if (memberRoles.isEmpty()) {
            throw new BusinessException(ResponseCode.NOT_FOUND);
        }
        return memberRoles;
    }

    @Override
    public List<MemberResponseDTO> findAllMembers() {
        List<MemberResponseDTO> members = memberMapper.findAllMembers();
        if (members.isEmpty()) {
            throw new BusinessException(ResponseCode.NOT_FOUND);
        }
        return members;
    }

    @Override
    public MemberResponseDTO getMemberById(Integer id) {
        MemberResponseDTO dto = memberMapper.getMemberById(id);
        if(dto == null) {
            throw new BusinessException(ResponseCode.MEMBER_NOT_FOUND);
        }
        return dto;
    }

    @Override
    public String getProfileImageKey(int memberId) {
        MemberResponseDTO member = memberMapper.findMemberById(memberId);
        if (member == null) {
            throw new BusinessException(ResponseCode.MEMBER_NOT_FOUND);
        }

        String pictureUrl = member.getPictureUrl();
        if (pictureUrl == null || pictureUrl.isBlank()) {
            throw new BusinessException(ResponseCode.MEMBER_PROFILE_IMAGE_NOT_FOUND);
        }

        return pictureUrl;
    }
}
