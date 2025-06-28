@echo off
echo ==========================================
echo   Introduce Command Service 테스트 실행 중...
echo ==========================================

call gradlew test --tests "com.piveguyz.empickbackend.employment.introduce.command.application.service.*"

if %ERRORLEVEL% == 0 (
    echo.
    echo ==========================================
    echo   Introduce 테스트 실행 완료! 
    echo ==========================================
    echo HTML 리포트를 열고 있습니다...
    start build\reports\tests\test\index.html
) else (
    echo.
    echo ==========================================
    echo   Introduce 테스트 실행 실패!
    echo ==========================================
    start build\reports\tests\test\index.html
)

pause 