@echo off
echo ==========================================
echo       전체 테스트 실행 중...
echo ==========================================

call gradlew test

if %ERRORLEVEL% == 0 (
    echo.
    echo ==========================================
    echo       테스트 실행 완료! 
    echo ==========================================
    echo HTML 리포트를 열고 있습니다...
    start build\reports\tests\test\index.html
    echo.
    echo 리포트 경로: build\reports\tests\test\index.html
) else (
    echo.
    echo ==========================================
    echo       테스트 실행 실패!
    echo ==========================================
    echo HTML 리포트에서 실패 원인을 확인하세요.
    start build\reports\tests\test\index.html
)

pause 