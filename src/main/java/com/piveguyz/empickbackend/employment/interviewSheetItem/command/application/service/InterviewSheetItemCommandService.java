package com.piveguyz.empickbackend.employment.interviewSheetItem.command.application.service;


import com.piveguyz.empickbackend.employment.interviewSheetItem.command.application.dto.InterviewSheetItemCommandDTO;

public interface InterviewSheetItemCommandService {
    InterviewSheetItemCommandDTO create(InterviewSheetItemCommandDTO interviewSheetItemCommandDTO);

    InterviewSheetItemCommandDTO delete(Integer id);
}
