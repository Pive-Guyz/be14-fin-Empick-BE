@echo off
echo ===============================
echo 결재 관련 테스트 실행
echo ===============================
echo.

echo 결재 테스트 실행 중...

./gradlew test --tests "com.piveguyz.empickbackend.approvals.approvalContent.query.service.ApprovalContentQueryServiceTest" ^
            --tests "com.piveguyz.empickbackend.approvals.approvalCategoryItem.query.service.ApprovalCategoryItemQueryServiceTest" ^
            --tests "com.piveguyz.empickbackend.approvals.approvalCategory.query.service.ApprovalCategoryQueryServiceTest" ^
            --tests "com.piveguyz.empickbackend.approvals.approval.query.service.ApprovalQueryServiceTest" ^
            --tests "com.piveguyz.empickbackend.approvals.approval.query.service.ApprovalLineServiceTest" ^
            --tests "com.piveguyz.empickbackend.approvals.approval.query.service.ApprovalDetailQueryServiceTest" ^
            --tests "com.piveguyz.empickbackend.approvals.approval.command.application.service.ApprovalCommandServiceTest" ^
            --continue

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ===============================
    echo ✅ 결재 테스트가 성공적으로 완료되었습니다!
    echo ===============================
    echo.
    echo 실행된 결재 테스트:
    echo - ApprovalContentQueryServiceTest
    echo - ApprovalCategoryItemQueryServiceTest  
    echo - ApprovalCategoryQueryServiceTest
    echo - ApprovalQueryServiceTest
    echo - ApprovalLineServiceTest
    echo - ApprovalDetailQueryServiceTest
    echo - ApprovalCommandServiceTest
    echo ===============================
    echo 🎯 총 7개 결재 테스트 클래스 실행 완료
    echo ===============================
    echo.
    echo HTML 테스트 리포트 확인:
    echo build\reports\tests\test\index.html
    echo.
) else (
    echo.
    echo ❌ 일부 테스트가 실패했습니다.
    echo 자세한 내용은 테스트 리포트를 확인하세요.
    echo.
)

pause 