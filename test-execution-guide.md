# 🧪 테스트 실행 가이드

## 📋 생성된 테스트 목록

### ✅ **Applicant Query Service 테스트**
- `ApplicantQueryServiceTest.java` (7개 테스트)
- `ApplicationQueryServiceTest.java` (7개 테스트)  
- `ApplicationResponseQueryServiceTest.java` (6개 테스트)

### ✅ **Introduce Command Service 테스트**
- `IntroduceStandardItemCommandServiceTest.java` (6개 테스트)
- `IntroduceTemplateCommandServiceTest.java` (6개 테스트)
- `IntroduceTemplateItemResponseCommandServiceTest.java` (3개 테스트)
- `IntroduceTemplateItemCommandServiceTest.java` (7개 테스트)

## 🚀 테스트 실행 명령어

### **1. 전체 테스트 실행**
```bash
./gradlew test
```

### **2. 특정 패키지 테스트 실행**

#### Applicant Query Service 테스트만 실행
```bash
./gradlew test --tests "com.piveguyz.empickbackend.employment.applicant.query.service.*"
```

#### Introduce Command Service 테스트만 실행  
```bash
./gradlew test --tests "com.piveguyz.empickbackend.employment.introduce.command.application.service.*"
```

### **3. 개별 테스트 클래스 실행**

#### 새로 생성한 테스트들 개별 실행
```bash
# 지원자 응답 조회 서비스 테스트
./gradlew test --tests "ApplicationResponseQueryServiceTest"

# 표준 항목 Command 서비스 테스트
./gradlew test --tests "IntroduceStandardItemCommandServiceTest"

# 템플릿 Command 서비스 테스트
./gradlew test --tests "IntroduceTemplateCommandServiceTest"

# 템플릿 항목 응답 Command 서비스 테스트
./gradlew test --tests "IntroduceTemplateItemResponseCommandServiceTest"

# 템플릿 항목 Command 서비스 테스트
./gradlew test --tests "IntroduceTemplateItemCommandServiceTest"
```

### **4. 특정 테스트 메서드 실행**
```bash
# 예시: 특정 메서드만 실행
./gradlew test --tests "ApplicationResponseQueryServiceTest.testFindAllApplicationResponse"
```

### **5. 테스트 결과와 상세 로그 확인**
```bash
# 상세 정보와 함께 실행
./gradlew test --info

# 테스트 스택트레이스 포함하여 실행
./gradlew test --stacktrace
```

## 📊 테스트 결과 확인

### **1. HTML 리포트 확인**
테스트 실행 후 다음 경로에서 상세한 HTML 리포트를 확인할 수 있습니다:
```
파일 경로: C:\fin-empick-BE\build\reports\tests\test\index.html
브라우저: file:///C:/fin-empick-BE/build/reports/tests/test/index.html
```

### **2. XML 결과 파일**
```
경로: C:\fin-empick-BE\build\test-results\test\
```

### **3. 콘솔 출력**
- ✅ **성공**: `BUILD SUCCESSFUL`
- ❌ **실패**: `BUILD FAILED` + 실패한 테스트 정보

## 🔧 테스트 설정

### **테스트 프로파일 실행**
```bash
# 특정 프로파일로 테스트 실행
./gradlew test -Dspring.profiles.active=test
```

### **병렬 테스트 실행** 
```bash
# 병렬로 테스트 실행 (속도 향상)
./gradlew test --parallel
```

### **테스트 캐시 클리어**
```bash
# 테스트 캐시 삭제 후 실행
./gradlew clean test
```

## 📈 테스트 통계 (현재)

- **총 테스트 수**: 101개
- **성공**: 100개 ✅
- **실패**: 1개 ❌ (기존 테스트)
- **새로 추가된 테스트**: 22개 (모두 성공)

## 🎯 주요 테스트 시나리오

### **Query Service 테스트**
- 데이터 조회 성공/실패
- 빈 결과 처리
- 예외 상황 처리
- 여러 데이터 조회

### **Command Service 테스트**  
- Create/Update/Delete 작업
- 비즈니스 규칙 검증
- 중복 데이터 처리
- 예외 상황 처리

## 🚨 테스트 실패 시 대응

### **1. 실패한 테스트 재실행**
```bash
./gradlew test --rerun-tasks
```

### **2. 특정 실패 테스트만 실행**
```bash
# 실패한 테스트명으로 실행
./gradlew test --tests "IntroduceRatingResultCommandServiceTest"
```

### **3. 디버그 모드로 실행**
```bash
./gradlew test --debug-jvm
```

---

## 📝 참고사항

- 모든 테스트는 **Mockito**를 사용한 단위 테스트입니다
- **Given-When-Then** 패턴을 따릅니다  
- **@ExtendWith(MockitoExtension.class)** 사용
- 실제 데이터베이스 연결 없이 목업 데이터로 테스트됩니다 