package egovframework.example.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
// import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
// import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class EgovConfigDatasource {
	// HSQL
	// @Bean(name="dataSource")
	// public DataSource dataSource() {
	//     EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
	//     return builder.setType(EmbeddedDatabaseType.HSQL).addScript("classpath:/db/sampledb.sql").build();
	// }

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

}
