package by.personal.filmrandomizer;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.sql.DataSource;
import java.time.temporal.ChronoUnit;

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

	@Bean
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setReadTimeout(10000);
		factory.setConnectTimeout(10000);
		return new RestTemplate(factory);
	}
}
