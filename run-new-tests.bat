@echo off
echo ==========================================
echo       새로 생성한 테스트 실행 중...
echo ==========================================

echo [1/5] ApplicationResponseQueryServiceTest 실행...
call gradlew test --tests "ApplicationResponseQueryServiceTest"

echo [2/5] IntroduceStandardItemCommandServiceTest 실행...
call gradlew test --tests "IntroduceStandardItemCommandServiceTest"

echo [3/5] IntroduceTemplateCommandServiceTest 실행...
call gradlew test --tests "IntroduceTemplateCommandServiceTest"

echo [4/5] IntroduceTemplateItemResponseCommandServiceTest 실행...
call gradlew test --tests "IntroduceTemplateItemResponseCommandServiceTest"

echo [5/5] IntroduceTemplateItemCommandServiceTest 실행...
call gradlew test --tests "IntroduceTemplateItemCommandServiceTest"

echo.
echo ==========================================
echo       새로 생성한 테스트 실행 완료!
echo ==========================================
echo HTML 리포트를 열고 있습니다...
start build\reports\tests\test\index.html

pause 