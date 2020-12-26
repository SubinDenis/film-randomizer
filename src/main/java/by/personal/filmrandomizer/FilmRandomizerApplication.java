package by.personal.filmrandomizer;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

import javax.sql.DataSource;

@SpringBootApplication
@ComponentScans(
		@ComponentScan("by.personal.filmrandomizer")
)
public class FilmRandomizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmRandomizerApplication.class, args);
	}

	@Bean
	public SpringLiquibase liquibase(DataSource ds) {
		SpringLiquibase liquiabse = new SpringLiquibase();
		liquiabse.setDataSource(ds);
		liquiabse.setChangeLog("classpath:db/master.xml");
		return liquiabse;
	}
}
