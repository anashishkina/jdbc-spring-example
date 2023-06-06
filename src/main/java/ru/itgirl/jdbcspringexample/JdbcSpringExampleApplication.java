package ru.itgirl.jdbcspringexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class JdbcSpringExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(JdbcSpringExampleApplication.class, args);
	}

}
