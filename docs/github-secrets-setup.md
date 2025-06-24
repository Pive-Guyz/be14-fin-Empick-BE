# 🔐 GitHub Secrets 설정 가이드

## 📍 설정 위치

`empick-backend GitHub Repository → Settings → Secrets and variables → Actions`

## 🔑 필수 Secrets

### **AWS 인증 정보**

```bash
AWS_ACCESS_KEY_ID=AKIA***************Q  # Terraform에서 사용 중인 실제 값
AWS_SECRET_ACCESS_KEY=y3e/***************************Ej  # Terraform에서 사용 중인 실제 값
```

### **EC2 인스턴스 정보**

```bash
# Terraform Output에서 확인된 값들 (2025-06-24 업데이트)
BLUE_INSTANCE_ID=i-0c1f37f5cdfc657ed
GREEN_INSTANCE_ID=i-02f47b3ff2cb0dd85
```

### **ALB 접속 정보**

```bash
ALB_URL=http://vpc2-backend-alb-660956039.ap-northeast-2.elb.amazonaws.com
ALB_HEALTH_CHECK_URL=http://vpc2-backend-alb-660956039.ap-northeast-2.elb.amazonaws.com/actuator/health
```

---

## 📋 **Secrets 설정 체크리스트**

- [ ] **AWS_ACCESS_KEY_ID** : `AKIA***************Q` (실제 값 사용)
- [ ] **AWS_SECRET_ACCESS_KEY** : `y3e/***************************Ej` (실제 값 사용)
- [ ] **BLUE_INSTANCE_ID** : `i-0c1f37f5cdfc657ed`
- [ ] **GREEN_INSTANCE_ID** : `i-02f47b3ff2cb0dd85`
- [ ] **ALB_URL** : `http://vpc2-backend-alb-660956039.ap-northeast-2.elb.amazonaws.com`
- [ ] **ALB_HEALTH_CHECK_URL** : `http://vpc2-backend-alb-660956039.ap-northeast-2.elb.amazonaws.com/actuator/health`

---

## 🔧 **실제 AWS 키 확인 방법**

### **1. Terraform 설정에서 확인**

```bash
# infra/vpc2-backend/terraform.tfvars 파일에서 확인
grep -E "(aws_access_key|aws_secret_key)" terraform.tfvars
```

### **2. AWS CLI로 인증 테스트**

```bash
# AWS 자격 증명 확인
aws sts get-caller-identity --region ap-northeast-2
```

### **3. EC2 인스턴스 ID 확인**

```bash
# EC2 인스턴스 목록 확인
aws ec2 describe-instances --region ap-northeast-2 --query 'Reservations[].Instances[?State.Name==`running`].[InstanceId,Tags[?Key==`Name`].Value|[0],PrivateIpAddress]' --output table
```

---

## ⚠️ **주의사항**

1. **보안**: 실제 AWS 키 값을 문서에 하드코딩하지 마세요
2. **권한**: AWS 키에 S3, EC2, SSM 권한이 있는지 확인하세요
3. **리전**: 모든 리소스가 `ap-northeast-2` 리전에 있는지 확인하세요
4. **인스턴스 ID**: 실제 배포된 인스턴스 ID와 일치하는지 확인하세요

---

## 🚀 **설정 완료 후 테스트**

GitHub Actions에서 **"Run workflow"** 버튼으로 수동 실행 테스트를 진행하세요.
