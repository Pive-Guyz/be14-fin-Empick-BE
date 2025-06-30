package com.piveguyz.empickbackend.employment.applicant.query.service;

import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.employment.applicant.command.domain.aggregate.ApplicationStatus;
import com.piveguyz.empickbackend.employment.applicant.query.dto.ApplicationQueryDTO;
import com.piveguyz.empickbackend.employment.applicant.query.mapper.ApplicationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("지원서 Query Service 테스트")
class ApplicationQueryServiceTest {

    @Mock
    private ApplicationMapper applicationMapper;

    @InjectMocks
    private ApplicationQueryServiceImp applicationQueryService;

    private List<ApplicationQueryDTO> mockApplicationList;
    private ApplicationQueryDTO mockApplication;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        mockApplication = new ApplicationQueryDTO(
                1, // id
                1, // recruitmentId
                LocalDateTime.now(), // createdAt
                ApplicationStatus.WAITING, // status
                10, // applicantId
                0, // introduceRatingResultId
                0, // interviewId
                LocalDateTime.now(), // updatedAt
                1, // updatedBy
                null, // applicationJobtestId
                null, // jobtestGradingScore
                null  // jobtestGradingStatus
        );

        ApplicationQueryDTO mockApplication2 = new ApplicationQueryDTO(
                2, // id
                2, // recruitmentId
                LocalDateTime.now(), // createdAt
                ApplicationStatus.PASSED_DOCS, // status
                20, // applicantId
                1, // introduceRatingResultId
                1, // interviewId
                LocalDateTime.now(), // updatedAt
                1, // updatedBy
                null, // applicationJobtestId
                null, // jobtestGradingScore
                null  // jobtestGradingStatus
        );

        mockApplicationList = Arrays.asList(mockApplication, mockApplication2);
    }

    @Test
    @DisplayName("지원서 전체 조회 - 모든 지원서 조회 시 성공")
    void testFindAllApplication() {
        // Given
        given(applicationMapper.findAllApplication())
                .willReturn(mockApplicationList);

        // When
        List<ApplicationQueryDTO> result = applicationQueryService.findAllApplication();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals(10, result.get(0).getApplicantId());
        assertEquals(20, result.get(1).getApplicantId());

        verify(applicationMapper).findAllApplication();
    }

    @Test
    @DisplayName("지원서 ID로 조회 - 특정 ID의 지원서 조회 시 성공")
    void testFindApplicationById() {
        // Given
        given(applicationMapper.findApplicationById(1))
                .willReturn(mockApplication);

        // When
        ApplicationQueryDTO result = applicationQueryService.findApplicationById(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(1, result.getRecruitmentId());
        assertEquals(10, result.getApplicantId());
        assertEquals(ApplicationStatus.WAITING, result.getStatus());

        verify(applicationMapper).findApplicationById(1);
    }

    @Test
    @DisplayName("지원자 ID로 지원서 조회 - 특정 지원자의 지원서 조회 시 성공")
    void testFindApplicationByApplicantId() {
        // Given
        given(applicationMapper.findApplicationByApplicantId(10))
                .willReturn(mockApplication);

        // When
        ApplicationQueryDTO result = applicationQueryService.findApplicationByApplicantId(10);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(10, result.getApplicantId());

        verify(applicationMapper).findApplicationByApplicantId(10);
    }

    @Test
    @DisplayName("지원서 전체 조회 - 지원서가 없는 경우 빈 리스트 반환")
    void testFindAllApplicationEmpty() {
        // Given
        given(applicationMapper.findAllApplication())
                .willReturn(Collections.emptyList());

        // When
        List<ApplicationQueryDTO> result = applicationQueryService.findAllApplication();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(applicationMapper).findAllApplication();
    }

    @Test
    @DisplayName("지원서 ID로 조회 - 존재하지 않는 ID 조회 시 null 반환")
    void testFindApplicationByIdNotFound() {
        // Given
        given(applicationMapper.findApplicationById(999))
                .willReturn(null);

        // When
        ApplicationQueryDTO result = applicationQueryService.findApplicationById(999);

        // Then
        assertNull(result);

        verify(applicationMapper).findApplicationById(999);
    }

    @Test
    @DisplayName("지원자 ID로 지원서 조회 실패 - 지원서가 없는 경우 예외 발생")
    void testFindApplicationByApplicantIdNotFound() {
        // Given
        given(applicationMapper.findApplicationByApplicantId(999))
                .willReturn(null);

        // When & Then
        assertThrows(BusinessException.class, () -> {
            applicationQueryService.findApplicationByApplicantId(999);
        });

        verify(applicationMapper).findApplicationByApplicantId(999);
    }

    @Test
    @DisplayName("지원자 ID로 지원서 조회 - 정상적인 지원자 ID로 조회 시 성공")
    void testFindApplicationByApplicantIdSuccess() {
        // Given
        ApplicationQueryDTO validApplication = new ApplicationQueryDTO(
                3, // id
                3, // recruitmentId
                LocalDateTime.now(), // createdAt
                ApplicationStatus.PASSED_FINAL, // status
                30, // applicantId
                2, // introduceRatingResultId
                2, // interviewId
                LocalDateTime.now(), // updatedAt
                1, // updatedBy
                null, // applicationJobtestId
                null, // jobtestGradingScore
                null  // jobtestGradingStatus
        );

        given(applicationMapper.findApplicationByApplicantId(30))
                .willReturn(validApplication);

        // When
        ApplicationQueryDTO result = applicationQueryService.findApplicationByApplicantId(30);

        // Then
        assertNotNull(result);
        assertEquals(3, result.getId());
        assertEquals(30, result.getApplicantId());
        assertEquals(ApplicationStatus.PASSED_FINAL, result.getStatus());

        verify(applicationMapper).findApplicationByApplicantId(30);
    }
} 