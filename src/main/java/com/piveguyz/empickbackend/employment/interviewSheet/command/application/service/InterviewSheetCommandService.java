package com.piveguyz.empickbackend.employment.interviewSheet.command.application.service;


import com.piveguyz.empickbackend.employment.interviewSheet.command.application.dto.InterviewSheetCommandDTO;

public interface InterviewSheetCommandService {
    InterviewSheetCommandDTO createSheet(InterviewSheetCommandDTO dto);

    InterviewSheetCommandDTO updateSheet(Integer id, InterviewSheetCommandDTO dto);

    InterviewSheetCommandDTO deleteSheet(Integer id);
}
