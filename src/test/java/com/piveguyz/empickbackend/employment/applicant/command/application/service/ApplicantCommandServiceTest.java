package com.piveguyz.empickbackend.employment.applicant.command.application.service;

import com.piveguyz.empickbackend.employment.applicant.command.application.dto.ApplicantCommandDTO;
import com.piveguyz.empickbackend.employment.applicant.command.domain.aggregate.ApplicantEntity;
import com.piveguyz.empickbackend.employment.applicant.command.domain.repository.ApplicantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("지원자 Command Service 테스트")
class ApplicantCommandServiceTest {

    @Mock
    private ApplicantRepository applicantRepository;

    @InjectMocks
    private ApplicantCommandServiceImp applicantCommandService;

    private ApplicantEntity mockEntity;
    private ApplicantCommandDTO mockDto;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        // Mock DTO 설정
        mockDto = ApplicantCommandDTO.builder()
                .name("홍길동")
                .phone("010-1234-5678")
                .email("hong@example.com")
                .profileUrl("profile.jpg")
                .birth("1990-01-01")
                .address("서울시 강남구")
                .build();

        // Mock Entity 설정
        mockEntity = ApplicantEntity.builder()
                .id(1)
                .name("홍길동")
                .phone("010-1234-5678")
                .email("hong@example.com")
                .profileUrl("profile.jpg")
                .birth("1990-01-01")
                .address("서울시 강남구")
                .build();
    }

    @Test
    @DisplayName("지원자 등록 - 정상적인 데이터로 지원자 등록 시 성공")
    void testCreateApplicant() {
        // Given
        given(applicantRepository.save(any(ApplicantEntity.class)))
                .willReturn(mockEntity);

        // When
        ApplicantCommandDTO result = applicantCommandService.createApplicant(mockDto);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("홍길동", result.getName());
        assertEquals("010-1234-5678", result.getPhone());
        assertEquals("hong@example.com", result.getEmail());
        assertEquals("profile.jpg", result.getProfileUrl());
        assertEquals("1990-01-01", result.getBirth());
        assertEquals("서울시 강남구", result.getAddress());

        verify(applicantRepository).save(any(ApplicantEntity.class));
    }

    @Test
    @DisplayName("지원자 등록 - 필수 정보만으로 지원자 등록 시 성공")
    void testCreateApplicantWithRequiredFieldsOnly() {
        // Given
        ApplicantCommandDTO minimalDto = ApplicantCommandDTO.builder()
                .name("김철수")
                .phone("010-9876-5432")
                .email("kim@example.com")
                .birth("1985-05-15")
                .address("부산시 해운대구")
                .build();

        ApplicantEntity minimalEntity = ApplicantEntity.builder()
                .id(2)
                .name("김철수")
                .phone("010-9876-5432")
                .email("kim@example.com")
                .birth("1985-05-15")
                .address("부산시 해운대구")
                .build();

        given(applicantRepository.save(any(ApplicantEntity.class)))
                .willReturn(minimalEntity);

        // When
        ApplicantCommandDTO result = applicantCommandService.createApplicant(minimalDto);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals("김철수", result.getName());
        assertEquals("010-9876-5432", result.getPhone());
        assertEquals("kim@example.com", result.getEmail());
        assertNull(result.getProfileUrl()); // 선택 필드는 null
        assertEquals("1985-05-15", result.getBirth());
        assertEquals("부산시 해운대구", result.getAddress());

        verify(applicantRepository).save(any(ApplicantEntity.class));
    }
} 