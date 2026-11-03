- Extension
    - Extension Pack for Java
    - https://egovframe.go.kr/home/sub.do?menuNo=107
        - VS Code Extension vsix 파일 다운로드
        - Install from VSIX
- 메이븐 설치
    - https://maven.apache.org/download.cgi
    - apache-maven-3.10.0-bin.zip
    - 환경 변수 등록
    - VSCode 재실행 후 mvn -version 버전 확인
        - 안 될 시
            ```
            #### PowerShell 창에서 바로 환경 변수 갱신
            $env:Path = [System.Environment]::GetEnvironmentVariable("Path","Machine") + ";" + [System.Environment]::GetEnvironmentVariable("Path","User")
            ```
- 예제 다운로드
    - https://www.egovframe.go.kr/home/sub.do?menuNo=37
    - 실행환경 > 예제 다운로드 > 실행환경 Boot Starter 5.0.0 적용 예제
- Dependency 다운로드 및 첫 빌드
    - mvn clean package
        - 실패 시 캐시 삭제(Win + R, %USERPROFILE%\.m2\repository) 후 터미널(CMD 또는 PowerShell)을 관리자 권한으로 실행
            ```
            cd /d [프로젝트 경로]
            mvn clean package -U -DskipTests
            ```
- 프로젝트 실행
    ```
    mvn spring-boot:run
    ```
    - http://localhost:8080 접속 (로그인 페이지, 아이디: admin 또는 user 비밀번호: 1)
    - 포트 충돌 방지
        - src\main\resources\application.properties에서 server.port 변경
- 파일 구조
    - Controller
        - src/main/java/egovframework/example/sample/web/EgovSampleController.java
- Endpoint 예외 처리 후 컨트롤러 확인
    1. application.properties의 org.egovframe.access.excludeList에 엔드포인트(권한 예외 URL) 추가
    2. Controller에 테스트용 메서드 추가
        ```
        @GetMapping("/sample/hello")
        @ResponseBody
        public String hello() {
            return "Hello, 전자정부프레임워크 5.0";
        }
        ```
    3. 서버(애플리케이션) 재실행 후 확인