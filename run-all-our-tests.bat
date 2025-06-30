@echo off
echo ==========================================
echo      우리가 만든 모든 테스트 실행 중...
echo ==========================================

echo [1단계] Applicant Query Service 테스트 + Introduce Command Service 테스트 한번에 실행...

call gradlew test --tests "com.piveguyz.empickbackend.employment.applicant.query.service.*" --tests "com.piveguyz.empickbackend.employment.introduce.command.application.service.*"

if %ERRORLEVEL% == 0 (
    echo.
    echo ==========================================
    echo      모든 테스트 실행 완료! 
    echo ==========================================
    echo HTML 리포트를 열고 있습니다...
    start build\reports\tests\test\index.html
    echo.
    echo 실행된 테스트:
    echo [Applicant Query Service]
    echo - ApplicantQueryServiceTest (7개 테스트)
    echo - ApplicationQueryServiceTest (7개 테스트)
    echo - ApplicationResponseQueryServiceTest (6개 테스트)
    echo.
    echo [Introduce Command Service]
    echo - IntroduceCommandServiceTest
    echo - IntroduceRatingResultCommandServiceTest
    echo - IntroduceStandardCommandServiceTest
    echo - IntroduceStandardItemCommandServiceTest (6개 테스트)
    echo - IntroduceTemplateCommandServiceTest (6개 테스트)
    echo - IntroduceTemplateItemResponseCommandServiceTest (3개 테스트)
    echo - IntroduceTemplateItemCommandServiceTest (7개 테스트)
) else (
    echo.
    echo ==========================================
    echo      테스트 실행 실패!
    echo ==========================================
    start build\reports\tests\test\index.html
)

pause 