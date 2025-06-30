package com.piveguyz.empickbackend.employment.applicant.query.service;

import com.piveguyz.empickbackend.employment.applicant.query.dto.ApplicationResponseQueryDTO;
import com.piveguyz.empickbackend.employment.applicant.query.mapper.ApplicationResponseMapper;
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
@DisplayName("지원서 응답 Query Service 테스트")
class ApplicationResponseQueryServiceTest {

    @Mock
    private ApplicationResponseMapper applicationResponseMapper;

    @InjectMocks
    private ApplicationResponseQueryServiceImp applicationResponseQueryService;

    private List<ApplicationResponseQueryDTO> mockResponseList;
    private ApplicationResponseQueryDTO mockResponse1;
    private ApplicationResponseQueryDTO mockResponse2;

    @BeforeEach
    @DisplayName("테스트 데이터 초기화")
    void setUp() {
        mockResponse1 = new ApplicationResponseQueryDTO();
        mockResponse1.setId(1);
        mockResponse1.setApplicationId(10);
        mockResponse1.setApplicationItemId(1);
        mockResponse1.setContent("첫 번째 질문에 대한 답변입니다.");

        mockResponse2 = new ApplicationResponseQueryDTO();
        mockResponse2.setId(2);
        mockResponse2.setApplicationId(10);
        mockResponse2.setApplicationItemId(2);
        mockResponse2.setContent("두 번째 질문에 대한 답변입니다.");

        ApplicationResponseQueryDTO mockResponse3 = new ApplicationResponseQueryDTO();
        mockResponse3.setId(3);
        mockResponse3.setApplicationId(20);
        mockResponse3.setApplicationItemId(1);
        mockResponse3.setContent("다른 지원서의 첫 번째 질문 답변입니다.");

        mockResponseList = Arrays.asList(mockResponse1, mockResponse2, mockResponse3);
    }

    @Test
    @DisplayName("지원서 응답 전체 조회 - 모든 지원서 응답 조회 시 성공")
    void testFindAllApplicationResponse() {
        // Given
        given(applicationResponseMapper.findAllApplicationResponse())
                .willReturn(mockResponseList);

        // When
        List<ApplicationResponseQueryDTO> result = applicationResponseQueryService.findAllApplicationResponse();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals(3, result.get(2).getId());
        assertEquals("첫 번째 질문에 대한 답변입니다.", result.get(0).getContent());
        assertEquals("두 번째 질문에 대한 답변입니다.", result.get(1).getContent());

        verify(applicationResponseMapper).findAllApplicationResponse();
    }

    @Test
    @DisplayName("지원서 ID로 응답 조회 - 특정 지원서의 모든 응답 조회 시 성공")
    void testFindApplicationResponsesByApplicationId() {
        // Given
        List<ApplicationResponseQueryDTO> applicationResponses = Arrays.asList(mockResponse1, mockResponse2);
        given(applicationResponseMapper.findApplicationResponsesByApplicationId(10))
                .willReturn(applicationResponses);

        // When
        List<ApplicationResponseQueryDTO> result = applicationResponseQueryService.findApplicationResponsesByApplicationId(10);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(10, result.get(0).getApplicationId());
        assertEquals(10, result.get(1).getApplicationId());
        assertEquals(1, result.get(0).getApplicationItemId());
        assertEquals(2, result.get(1).getApplicationItemId());

        verify(applicationResponseMapper).findApplicationResponsesByApplicationId(10);
    }

    @Test
    @DisplayName("지원서 응답 전체 조회 - 응답이 없는 경우 빈 리스트 반환")
    void testFindAllApplicationResponseEmpty() {
        // Given
        given(applicationResponseMapper.findAllApplicationResponse())
                .willReturn(Collections.emptyList());

        // When
        List<ApplicationResponseQueryDTO> result = applicationResponseQueryService.findAllApplicationResponse();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(applicationResponseMapper).findAllApplicationResponse();
    }

    @Test
    @DisplayName("지원서 ID로 응답 조회 - 해당 지원서에 응답이 없는 경우 빈 리스트 반환")
    void testFindApplicationResponsesByApplicationIdEmpty() {
        // Given
        given(applicationResponseMapper.findApplicationResponsesByApplicationId(999))
                .willReturn(Collections.emptyList());

        // When
        List<ApplicationResponseQueryDTO> result = applicationResponseQueryService.findApplicationResponsesByApplicationId(999);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(applicationResponseMapper).findApplicationResponsesByApplicationId(999);
    }

    @Test
    @DisplayName("지원서 ID로 응답 조회 - 여러 응답이 있는 경우 모든 응답 반환")
    void testFindApplicationResponsesByApplicationIdMultiple() {
        // Given
        ApplicationResponseQueryDTO response1 = new ApplicationResponseQueryDTO();
        response1.setId(10);
        response1.setApplicationId(100);
        response1.setApplicationItemId(1);
        response1.setContent("경력 관련 답변");

        ApplicationResponseQueryDTO response2 = new ApplicationResponseQueryDTO();
        response2.setId(11);
        response2.setApplicationId(100);
        response2.setApplicationItemId(2);
        response2.setContent("지원동기 관련 답변");

        ApplicationResponseQueryDTO response3 = new ApplicationResponseQueryDTO();
        response3.setId(12);
        response3.setApplicationId(100);
        response3.setApplicationItemId(3);
        response3.setContent("자기소개 관련 답변");

        List<ApplicationResponseQueryDTO> multipleResponses = Arrays.asList(response1, response2, response3);

        given(applicationResponseMapper.findApplicationResponsesByApplicationId(100))
                .willReturn(multipleResponses);

        // When
        List<ApplicationResponseQueryDTO> result = applicationResponseQueryService.findApplicationResponsesByApplicationId(100);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        
        // 모든 응답이 같은 지원서 ID를 가지는지 확인
        assertTrue(result.stream().allMatch(response -> response.getApplicationId() == 100));
        
        // 각 응답의 내용 확인
        assertEquals("경력 관련 답변", result.get(0).getContent());
        assertEquals("지원동기 관련 답변", result.get(1).getContent());
        assertEquals("자기소개 관련 답변", result.get(2).getContent());

        verify(applicationResponseMapper).findApplicationResponsesByApplicationId(100);
    }

    @Test
    @DisplayName("지원서 ID로 응답 조회 - 단일 응답이 있는 경우 정상 반환")
    void testFindApplicationResponsesByApplicationIdSingle() {
        // Given
        ApplicationResponseQueryDTO singleResponse = new ApplicationResponseQueryDTO();
        singleResponse.setId(20);
        singleResponse.setApplicationId(200);
        singleResponse.setApplicationItemId(1);
        singleResponse.setContent("유일한 답변입니다.");

        List<ApplicationResponseQueryDTO> singleResponseList = Arrays.asList(singleResponse);

        given(applicationResponseMapper.findApplicationResponsesByApplicationId(200))
                .willReturn(singleResponseList);

        // When
        List<ApplicationResponseQueryDTO> result = applicationResponseQueryService.findApplicationResponsesByApplicationId(200);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(20, result.get(0).getId());
        assertEquals(200, result.get(0).getApplicationId());
        assertEquals("유일한 답변입니다.", result.get(0).getContent());

        verify(applicationResponseMapper).findApplicationResponsesByApplicationId(200);
    }
} 