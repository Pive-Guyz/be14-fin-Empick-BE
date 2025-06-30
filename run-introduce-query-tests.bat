@echo off
echo ==========================================
echo   자기소개서 Query Service 테스트 실행 중...
echo ==========================================

call gradlew test --tests "com.piveguyz.empickbackend.employment.introduce.query.service.*"

if %ERRORLEVEL% == 0 (
    echo.
    echo ==========================================
    echo   자기소개서 Query 테스트 실행 완료! 
    echo ==========================================
    echo HTML 리포트를 열고 있습니다...
    start build\reports\tests\test\index.html
    echo.
    echo 실행된 테스트:
    echo - IntroduceQueryServiceTest
    echo - IntroduceResultQueryServiceTest  
    echo - IntroduceStandardQueryServiceTest
    echo - IntroduceTemplateQueryServiceTest
    echo - IntroduceTemplateItemResponseQueryServiceTest
) else (
    echo.
    echo ==========================================
    echo   자기소개서 Query 테스트 실행 실패!
    echo ==========================================
    start build\reports\tests\test\index.html
)

pause 