package com.piveguyz.empickbackend.employment.applicant.command.application.service;

import com.piveguyz.empickbackend.employment.applicant.command.application.dto.ApplicationCommandDTO;
import com.piveguyz.empickbackend.employment.applicant.command.domain.aggregate.ApplicationEntity;
import com.piveguyz.empickbackend.employment.applicant.command.domain.repository.ApplicationRepository;
import com.piveguyz.empickbackend.employment.introduce.command.domain.repository.IntroduceRatingResultRepository;
import com.piveguyz.empickbackend.employment.recruitment.command.domain.repository.RecruitmentRepository;
import com.piveguyz.empickbackend.employment.applicant.command.domain.repository.ApplicantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("지원서 Command Service 테스트")
class ApplicationCommandServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private RecruitmentRepository recruitmentRepository;

    @Mock
    private ApplicantRepository applicantRepository;

    @Mock
    private IntroduceRatingResultRepository introduceRatingResultRepository;

    @InjectMocks
    private ApplicationCommandServiceImp applicationCommandService;

    private ApplicationEntity mockEntity;
    private ApplicationCommandDTO mockDto;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        // Mock 데이터 설정
        mockDto = new ApplicationCommandDTO();
        mockDto.setRecruitmentId(1);
        mockDto.setApplicantId(10);
        mockDto.setStatus(0);
        mockDto.setIntroduceRatingResultId(null);

        mockEntity = new ApplicationEntity(1, 1, 10, 0, null, null, null, null);
    }

    @Test
    @DisplayName("지원서 등록 - 정상적인 데이터로 지원서 등록 시 성공")
    void testRegisterApplication() {
        // Given
        given(applicationRepository.existsByRecruitmentIdAndApplicantId(1, 10))
                .willReturn(false);
        given(applicationRepository.save(any(ApplicationEntity.class)))
                .willReturn(mockEntity);

        // When
        ApplicationCommandDTO result = applicationCommandService.register(mockDto);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(1, result.getRecruitmentId());
        assertEquals(10, result.getApplicantId());
        assertEquals(0, result.getStatus());

        verify(applicationRepository).existsByRecruitmentIdAndApplicantId(1, 10);
        verify(applicationRepository).save(any(ApplicationEntity.class));
    }

    @Test
    @DisplayName("지원서 수정 - 기존 지원서 정보 업데이트 시 성공")
    void testUpdateApplication() {
        // Given
        ApplicationCommandDTO updateDto = new ApplicationCommandDTO();
        updateDto.setId(1);
        updateDto.setRecruitmentId(2);
        updateDto.setApplicantId(20);
        updateDto.setStatus(1);
        updateDto.setIntroduceRatingResultId(null);

        ApplicationEntity updatedEntity = new ApplicationEntity(1, 2, 20, 1, null, null, null, null);

        given(applicationRepository.findById(1))
                .willReturn(Optional.of(mockEntity));
        given(applicationRepository.save(any(ApplicationEntity.class)))
                .willReturn(updatedEntity);

        // When
        ApplicationCommandDTO result = applicationCommandService.updated(updateDto);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());

        verify(applicationRepository).findById(1);
        verify(applicationRepository).save(any(ApplicationEntity.class));
    }

    @Test
    @DisplayName("지원서 삭제 - 기존 지원서 ID로 삭제 요청 시 성공")
    void testDeleteApplication() {
        // Given
        given(applicationRepository.findById(1))
                .willReturn(Optional.of(mockEntity));

        // When
        Integer result = applicationCommandService.deleted(1);

        // Then
        assertEquals(1, result);

        verify(applicationRepository).findById(1);
        verify(applicationRepository).delete(mockEntity);
    }

    @Test
    @DisplayName("지원서 등록 실패 - 중복 지원 시 예외 발생")
    void testRegisterApplicationDuplicate() {
        // Given
        given(applicationRepository.existsByRecruitmentIdAndApplicantId(1, 10))
                .willReturn(true);

        // When & Then
        assertThrows(Exception.class, () -> {
            applicationCommandService.register(mockDto);
        });

        verify(applicationRepository).existsByRecruitmentIdAndApplicantId(1, 10);
        verify(applicationRepository, never()).save(any(ApplicationEntity.class));
    }

    @Test
    @DisplayName("지원서 수정 실패 - 존재하지 않는 지원서 ID로 수정 시 예외 발생")
    void testUpdateApplicationNotFound() {
        // Given
        ApplicationCommandDTO updateDto = new ApplicationCommandDTO();
        updateDto.setId(999); // 존재하지 않는 ID
        updateDto.setRecruitmentId(2);
        updateDto.setApplicantId(20);
        updateDto.setStatus(1);

        given(applicationRepository.findById(999))
                .willReturn(Optional.empty());

        // When & Then
        assertThrows(Exception.class, () -> {
            applicationCommandService.updated(updateDto);
        });

        verify(applicationRepository).findById(999);
        verify(applicationRepository, never()).save(any(ApplicationEntity.class));
    }

    @Test
    @DisplayName("지원서 삭제 실패 - 존재하지 않는 지원서 ID로 삭제 시 예외 발생")
    void testDeleteApplicationNotFound() {
        // Given
        given(applicationRepository.findById(999))
                .willReturn(Optional.empty());

        // When & Then
        assertThrows(Exception.class, () -> {
            applicationCommandService.deleted(999);
        });

        verify(applicationRepository).findById(999);
        verify(applicationRepository, never()).delete(any(ApplicationEntity.class));
    }
}
