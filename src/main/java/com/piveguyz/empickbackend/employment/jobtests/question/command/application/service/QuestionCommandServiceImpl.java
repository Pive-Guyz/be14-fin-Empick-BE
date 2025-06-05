package com.piveguyz.empickbackend.employment.jobtests.question.command.application.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.dto.CreateQuestionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.dto.DeleteQuestionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.dto.UpdateQuestionCommandDTO;
import com.piveguyz.empickbackend.employment.jobtests.question.command.application.mapper.QuestionMapper;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.aggregate.QuestionEntity;
import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.repository.QuestionRepository;
import com.piveguyz.empickbackend.member.command.domain.repository.MemberRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class QuestionCommandServiceImpl implements QuestionCommandService {

    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;

    public QuestionCommandServiceImpl(QuestionRepository questionRepository,
                                      MemberRepository memberRepository) {
        this.questionRepository = questionRepository;
        this.memberRepository = memberRepository;
    }

    // 실무 테스트 문제 등록
    @Override
    public CreateQuestionCommandDTO createQuestion(CreateQuestionCommandDTO createQuestionCommandDTO) {
        // 이미 존재할 경우
        if (questionRepository.existsByContent(createQuestionCommandDTO.getContent())) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_QUESTION_DUPLICATE);
        }

        // 작성자가 없는 회원인 경우
        if (!memberRepository.existsById(createQuestionCommandDTO.getCreatedMemberId())) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_QUESTION_INVALID_MEMBER);
        }

        QuestionEntity questionEntity = QuestionMapper.toEntity(createQuestionCommandDTO);
        QuestionEntity saved = questionRepository.save(questionEntity);

        return QuestionMapper.toCreateDto(saved);
    }


    // 실무 테스트 문제 수정
    @Override
    public UpdateQuestionCommandDTO updateQuestion(int id, UpdateQuestionCommandDTO updateQuestionCommandDTO) {
        // 문제 있는지 확인
        QuestionEntity question = questionRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResponseCode.EMPLOYMENT_QUESTION_NOT_FOUND));

        // 수정자가 없는 회원이라면
        if (!memberRepository.existsById(updateQuestionCommandDTO.getUpdatedMemberId())) {
            throw new BusinessException(ResponseCode.EMPLOYMENT_QUESTION_INVALID_UPDATED_MEMBER);
        }

        question.updateQuestionEntity(updateQuestionCommandDTO);

        QuestionEntity updatedEntity = questionRepository.save(question);

        return QuestionMapper.toUpdateDto(updatedEntity);
    }

    // 실무 테스트 문제 삭제
    @Override
    public DeleteQuestionCommandDTO deleteQuestion(int id) {
        // 문제 있는지 확인
        QuestionEntity question = questionRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResponseCode.EMPLOYMENT_QUESTION_NOT_FOUND));

        try {
            questionRepository.delete(question);
        } catch (DataIntegrityViolationException e) {
            // 참조중이라면 EMPLOYMENT_QUESTION_DELETE_CONFLICT 에러 발생
            throw new BusinessException(ResponseCode.EMPLOYMENT_QUESTION_DELETE_CONFLICT);
        }

        return QuestionMapper.toDeleteDto(question);
    }
}
