# 다우기술과제테스트 조직도API
## 설명
그룹웨어는 조직 구성원들이 의사 소통, 업무 전달, 정보 공유 및 의사 결정 등의
업무 활동을 원활하게 수행할 수 있도록 지원하는 <br>협업 SW입니다.
<br>해당 API는 조직도를 기반으로 부서별, 부서원별, 직위/직책별 다양한 기능 및 권한 설정을
제공합니다.

## 목차
### 1. API 환경
### 2. API 기능
*  2-1. 조직도 조회 API
*  2-2. 부서 관리 API
*  2-3. 부서원 관리 API
*  2-4. REQUEST 예시
*  2-5. Error Code
### 3. 테이블구조

## 상세내용

## 1. API 환경

### REST API
프로그래밍언어 - JAVA8<br>
프레임워크 - 스프링부트<br>
데이터베이스 - H2(내장디비)<br>
빌드 - GRADLE<br>
기타 - JPA, 롬복, 하이버네이트, 스프링부트 웹

## 2. API 기능
### 2-1. 조직도 조회 API
* GET 조직도 조회 URL<br>

**http://{서버URL}/org/organizations?파라미터=값&파라미터=값**<br><br>

* 파라미터<br>

|파라미터|타입|필수여부|설명|
|----------|-------|:-----:|-------------|
|deptCode|String|N|기준 부서코드. 미입력시 최상위 노드를 기준으로 조회|
|deptOnly|boolean|N|부서만 또는 부서원 포함 응답 구분|
|searchType|String|N|검색 대상<br>* dept: 부서<br>* member: 부서원|
|searchKeyword|String|N|검색어|

* Response<br>

|속성|타입|설명|
|----------|-------|-------------|
|id|Integer|데이터 고유 ID|
|type|String|데이터 타입<br>* Company: 회사<br>* Division: 본부<br>* Department: 부, 팀<br>* Member: 부서원<br>규모: Company > Division > Department|
|name|String|회사명, 부서명, 부서원명|
|code|String|부서코드|
|manager|Boolean|type이 “Member”인 경우 팀장 여부. 팀장(TRUE), 팀원(NULL)|

### 2-2. 부서관리 API

|항목|Method|URL|
|----------|-------|-------------|
|부서 추가|POST|http://{서버URL}/org/dept|
|부서 수정|PUT|http://{서버URL}/org/dept/{deptId}|
|부서 삭제|DELETE|http://{서버URL}/org/dept/{deptId}|

### 2-3. 부서원 관리 API

|항목|Method|URL|
|----------|-------|-------------|
|부서원 추가|POST|http://{서버URL}/org/member|
|부서원 수정|PUT|http://{서버URL}/org/member/{memberId}|
|부서원 삭제|DELETE|http://{서버URL}/org/member/{memberId}|

### 2-4. REQUEST 예시
* 부서추가
```jsonc
{
  "type" : "Company",    // Company, Division
  "name" : "회사",
  "code" : "D200",       //Company 경우 제외
  "parent" : 0   //최상위 노드는 0 그외에 부모로 지정할 ID
}
```

* 멤버추가
```jsonc
{
  "type" : "Member",
  "name" : "개발자",
  "manager": true, //부서장이 아닌경우 제외
  "parent" : 1 //속하게될 부서의 ID
}
```
### 2-5. Error Code

|속성|타입|필수여부|설명|
|----------|-------|:-----:|-------------|
|code|String|Y|오류코드|
|message|String|Y|오류 메시지|

|HTTP 상태 코드|오류 코드|설명|
|------------|------------|------------|
|400|BAD_REQUEST|요청값이 적절하지 않음|
|500|INTERNAL_SERVER_ERROR|내부 서버 오류|

### 3. 테이블구조

* 테이블명 - HRM_ORG

|컬럼|타입|제약조건|
|------------|------------|------------|
|id|Integer|PK|
|code|Varchar||
|manager|Boolean||
|name|Varchar||
|type|Varchar||
|parent_id|Integer|FK|
