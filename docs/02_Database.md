- 데이터베이스 접속 후 sampledb 생성
    ```
    mysql -u root -p
    CREATE DATABASE sampledb;
    USE sampledb;
    SOURCE ".sql 스크립트 경로"
    ```
- pom.xml 의존성 추가
    - DB 연결을 위한 MariaDB JDBC 드라이버 등록
        ```
        <!-- maridb -->
		<dependency>
			<groupId>org.mariadb.jdbc</groupId>
			<artifactId>mariadb-java-client</artifactId>
		</dependency>
        ```
- 데이터베이스 연결 정보 설정
    - src/main/java/egovframework/example/config/EgovConfigDatasource
        ```
        // MariaDB
        @Bean(name="dataSource")
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
            dataSource.setUrl("jdbc:mariadb://localhost:3306/sampledb");
            dataSource.setUsername("root");
            dataSource.setPassword("mariadb");
            return dataSource;
        }
        ```
- 프로젝트 실행
    ```
    mvn spring-boot:run
    ```