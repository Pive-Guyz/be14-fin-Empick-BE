@echo off
echo ===============================
echo 전체 테스트 스위트 실행 (결재 포함)
echo ===============================
echo.

echo 테스트 실행 중...

./gradlew test --tests "com.piveguyz.empickbackend.employment.applicant.query.service.ApplicantQueryServiceTest" ^
            --tests "com.piveguyz.empickbackend.employment.applicant.query.service.ApplicationQueryServiceTest" ^
            --tests "com.piveguyz.empickbackend.employment.applicant.query.service.ApplicationResponseQueryServiceTest" ^
            --tests "com.piveguyz.empickbackend.employment.applicant.command.application.service.ApplicantCommandServiceTest" ^
            --tests "com.piveguyz.empickbackend.employment.applicant.command.application.service.ApplicationCommandServiceTest" ^
            --tests "com.piveguyz.empickbackend.employment.applicant.command.application.service.ApplicationResponseCommandServiceTest" ^
            --tests "com.piveguyz.empickbackend.employment.introduce.command.application.service.IntroduceCommandServiceTest" ^
            --tests "com.piveguyz.empickbackend.employment.introduce.command.application.service.IntroduceRatingResultCommandServiceTest" ^
            --tests "com.piveguyz.empickbackend.employment.introduce.command.application.service.IntroduceStandardCommandServiceTest" ^
            --tests "com.piveguyz.empickbackend.employment.introduce.command.application.service.IntroduceStandardItemCommandServiceTest" ^
            --tests "com.piveguyz.empickbackend.employment.introduce.command.application.service.IntroduceTemplateCommandServiceTest" ^
            --tests "com.piveguyz.empickbackend.employment.introduce.command.application.service.IntroduceTemplateItemResponseCommandServiceTest" ^
            --tests "com.piveguyz.empickbackend.employment.introduce.command.application.service.IntroduceTemplateItemCommandServiceTest" ^
            --tests "com.piveguyz.empickbackend.employment.introduce.query.service.IntroduceStandardQueryServiceTest" ^
            --tests "com.piveguyz.empickbackend.employment.introduce.query.service.IntroduceTemplateQueryServiceTest" ^
            --tests "com.piveguyz.empickbackend.employment.introduce.query.service.IntroduceQueryServiceTest" ^
            --tests "com.piveguyz.empickbackend.employment.introduce.query.service.IntroduceRatingResultQueryServiceTest" ^
            --tests "com.piveguyz.empickbackend.approvals.approvalContent.query.service.ApprovalContentQueryServiceTest" ^
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
    echo ✅ 모든 테스트가 성공적으로 완료되었습니다!
    echo ===============================
    echo.
    echo 총 실행된 테스트 스위트:
    echo - 지원자 Query Service 테스트 (3개)
    echo - 지원자 Command Service 테스트 (3개)
    echo - 자기소개서 Command Service 테스트 (7개)
    echo - 자기소개서 Query Service 테스트 (4개)
    echo - 결재 Query Service 테스트 (6개)
    echo - 결재 Command Service 테스트 (1개)
    echo ===============================
    echo 🎯 총 24개 테스트 클래스 실행 완료
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