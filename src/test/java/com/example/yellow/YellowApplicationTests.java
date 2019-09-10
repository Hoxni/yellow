package com.example.yellow;

import com.example.yellow.repository.JoggingRepository;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {YellowApplicationTests.Initializer.class})
public class YellowApplicationTests {

	static class Initializer
			implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues.of(
					"spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
					"spring.datasource.username=" + postgreSQLContainer.getUsername(),
					"spring.datasource.password=" + postgreSQLContainer.getPassword()
			).applyTo(configurableApplicationContext.getEnvironment());
		}
	}

	@ClassRule
	public static PostgreSQLContainer postgreSQLContainer =
			new PostgreSQLContainer("postgres:latest")
			.withDatabaseName("sample")
			.withUsername("user")
			.withPassword("pass");

	@Autowired
	private JoggingRepository joggingRepository;

	@Test
	public void test() {
		Assert.assertEquals(9, joggingRepository.count());
	}

}
