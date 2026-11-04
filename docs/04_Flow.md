- 애플리케이션 시작
    - src/main/java/egovframework/example/EgovBootApplication.java
    - Spring이 설정을 읽고, 필요한 객체를 생성하며 웹 요청을 받을 준비 (Spring은 Controller, Service, 설정 클래스 등을 찾아 관리 객체인 Bean으로 등록한다.)
    - 설정 파일: src/main/resources/application.properties
- 주요 기능 파일
    - DB 연결 정보: EgovConfigDatasource.java
    - Mapper 인터페이스와 SQL XML 연결: EgovConfigMapper.java
        - Mapper: Service가 호출할 DB 작업 메서드를 선언
        - SQL XML: 	각 메서드가 실행할 실제 SQL을 작성
    - Thymeleaf, 정적 파일 경로, 예외 화면 등: EgovConfigWeb.java
    - 트랜잭션 적용 대상과 롤백 규칙: EgovConfigTransaction.java
    - 샘플 ID 생성 기능: EgovConfigIdGeneration.java
    - 메시지와 공통 기능: EgovConfigCommon.java
- MyBatis 연결
    - Mapper 메서드와 SQL을 연결하고, SQL 실행과 결과 변환을 처리 (서비스에서 Mapper 메서드를 호출하면 MyBatis가 연결된 SQL을 실행한다.)
    - SQL XML 경로: src/main/resources/egovframework/sqlmap/example/mappers/EgovSample_Sample_SQL.xml
---
- 권한 관리
    - pom.xml에 등록된 egovframe-boot-starter-access 사용
        - eGovFrame에서 제공하는 접근 제어용 라이브러리
    - 설정과 DB의 권한 정보를 이용해 요청한 URL의 접근 허용 여부를 판단
- 권한 관리 설정: application.properties
    ```
    org.egovframe.access.globalAuthen=session: 세션 인증 방식 사용
    org.egovframe.access.mappingPath=/**: 전체 경로를 접근 제어 대상으로 설정
    org.egovframe.access.loginUrl: 로그인 화면 주소 지정
    org.egovframe.access.accessDeniedUrl: 접근 거부 화면 주소 지정
    org.egovframe.access.sqlAuthorityUser: 사용자별 권한 조회 SQL
    org.egovframe.access.sqlRoleAndUrl: URL별 허용 권한 조회 SQL
    org.egovframe.access.excludeList: 로그인 화면, 정적 파일 등 검사 제외 경로 지정
    ```
- 로그인 처리
    - EgovSampleController.java의 loginProcess()에서 USERS 테이블의 사용자 정보를 조회하고 비밀번호 비교
    - 로그인 성공 시 세션에 loginVO, userId, accessUser 저장
    - 인증: 사용자가 누구인지 확인하는 과정
    - 인가: 해당 사용자가 요청한 기능을 사용할 수 있는지 확인하는 과정
- 사용자·권한 저장 테이블
    - 초기 테이블·데이터 SQL: src/main/resources/db/sampledb.sql
    - USERS: 사용자 아이디와 비밀번호 저장
    - AUTHORITIES: 사용자에게 부여한 권한 저장
    - ROLES: 접근 대상 URL과 역할 코드 저장
    - AUTHROLES: 권한 코드와 역할 코드 연결
- 접근 제어 흐름
    - 사용자가 URL 요청
    - 검사 제외 경로가 아니라면 Access의 접근 제어 적용
    - 세션의 로그인 정보와 DB 기반 권한 정보를 이용해 접근 허용 여부 판단
    - 권한이 없으면 접근 거부 처리
    - 초기 데이터 기준으로 관리자는 조회·등록·수정·삭제, 일반 사용자는 목록·상세 조회 권한 보유
- 현재 사용자 정보 확인
    - src/main/java/egovframework/example/sample/service/impl/EgovUserDetailsServiceImpl.java
    - EgovUserDetailsHelper를 이용해 로그인 여부, 현재 사용자, 권한 목록 확인
    - / 경로는 접근 검사 제외 목록에 있으며 컨트롤러에서 직접 로그인 여부 확인