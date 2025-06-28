package com.piveguyz.empickbackend.employment.applicant.query.service;

import com.piveguyz.empickbackend.employment.applicant.query.dto.ApplicantFullInfoDTO;
import com.piveguyz.empickbackend.employment.applicant.query.dto.ApplicantQueryDTO;
import com.piveguyz.empickbackend.employment.applicant.query.mapper.ApplicantMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("지원자 Query Service 테스트")
class ApplicantQueryServiceTest {

    @Mock
    private ApplicantMapper applicantMapper;

    @InjectMocks
    private ApplicantQueryServiceImp applicantQueryService;

    private List<ApplicantQueryDTO> mockApplicantList;
    private ApplicantQueryDTO mockApplicant;
    private List<ApplicantFullInfoDTO> mockFullInfoList;
    private ApplicantFullInfoDTO mockFullInfo;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        mockApplicant = ApplicantQueryDTO.builder()
                .id(1)
                .name("홍길동")
                .phone("010-1234-5678")
                .email("hong@example.com")
                .birth("1990-01-01")
                .address("서울시 강남구")
                .build();

        ApplicantQueryDTO mockApplicant2 = ApplicantQueryDTO.builder()
                .id(2)
                .name("김철수")
                .phone("010-9876-5432")
                .email("kim@example.com")
                .birth("1985-05-15")
                .address("부산시 해운대구")
                .build();

        mockApplicantList = Arrays.asList(mockApplicant, mockApplicant2);

        mockFullInfo = new ApplicantFullInfoDTO();
        mockFullInfo.setApplicantId(1);
        mockFullInfo.setName("홍길동");
        mockFullInfo.setPhone("010-1234-5678");
        mockFullInfo.setEmail("hong@example.com");
        mockFullInfo.setBirth("1990-01-01");
        mockFullInfo.setAddress("서울시 강남구");

        mockFullInfoList = Arrays.asList(mockFullInfo);
    }

    @Test
    @DisplayName("지원자 전체 조회 - 모든 지원자 조회 시 성공")
    void testFindAllApplicant() {
        // Given
        given(applicantMapper.findAllApplicant())
                .willReturn(mockApplicantList);

        // When
        List<ApplicantQueryDTO> result = applicantQueryService.findAllApplicant();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("홍길동", result.get(0).getName());
        assertEquals("김철수", result.get(1).getName());
        assertEquals("hong@example.com", result.get(0).getEmail());
        assertEquals("kim@example.com", result.get(1).getEmail());

        verify(applicantMapper).findAllApplicant();
    }

    @Test
    @DisplayName("지원자 ID로 조회 - 특정 ID의 지원자 조회 시 성공")
    void testFindApplicantById() {
        // Given
        given(applicantMapper.findApplicantById(1))
                .willReturn(mockApplicant);

        // When
        ApplicantQueryDTO result = applicantQueryService.findApplicantById(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("홍길동", result.getName());
        assertEquals("010-1234-5678", result.getPhone());
        assertEquals("hong@example.com", result.getEmail());

        verify(applicantMapper).findApplicantById(1);
    }

    @Test
    @DisplayName("지원자 이름으로 검색 - 이름 검색 시 해당 지원자들 반환")
    void testSearchApplicantsByName() {
        // Given
        List<ApplicantQueryDTO> searchResult = Arrays.asList(mockApplicant);
        given(applicantMapper.searchApplicantsByName("홍길동"))
                .willReturn(searchResult);

        // When
        List<ApplicantQueryDTO> result = applicantQueryService.searchApplicantsByName("홍길동");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("홍길동", result.get(0).getName());

        verify(applicantMapper).searchApplicantsByName("홍길동");
    }

    @Test
    @DisplayName("지원자 풀 정보 전체 조회 - 모든 지원자 상세 정보 조회 시 성공")
    void testFindAllApplicantFullInfo() {
        // Given
        given(applicantMapper.findAllApplicantFullInfo())
                .willReturn(mockFullInfoList);

        // When
        List<ApplicantFullInfoDTO> result = applicantQueryService.findAllApplicantFullInfo();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("홍길동", result.get(0).getName());

        verify(applicantMapper).findAllApplicantFullInfo();
    }

    @Test
    @DisplayName("지원자 전체 조회 - 지원자가 없는 경우 빈 리스트 반환")
    void testFindAllApplicantEmpty() {
        // Given
        given(applicantMapper.findAllApplicant())
                .willReturn(Collections.emptyList());

        // When
        List<ApplicantQueryDTO> result = applicantQueryService.findAllApplicant();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(applicantMapper).findAllApplicant();
    }

    @Test
    @DisplayName("지원자 ID로 조회 - 존재하지 않는 ID 조회 시 null 반환")
    void testFindApplicantByIdNotFound() {
        // Given
        given(applicantMapper.findApplicantById(999))
                .willReturn(null);

        // When
        ApplicantQueryDTO result = applicantQueryService.findApplicantById(999);

        // Then
        assertNull(result);

        verify(applicantMapper).findApplicantById(999);
    }

    @Test
    @DisplayName("지원자 이름으로 검색 - 검색 결과가 없는 경우 빈 리스트 반환")
    void testSearchApplicantsByNameEmpty() {
        // Given
        given(applicantMapper.searchApplicantsByName("존재하지않는이름"))
                .willReturn(Collections.emptyList());

        // When
        List<ApplicantQueryDTO> result = applicantQueryService.searchApplicantsByName("존재하지않는이름");

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(applicantMapper).searchApplicantsByName("존재하지않는이름");
    }
} 