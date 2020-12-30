package by.personal.filmrandomizer;

import liquibase.integration.spring.SpringLiquibase;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.sql.DataSource;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication(exclude = {JacksonAutoConfiguration.class})
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
	@Primary
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setReadTimeout(10000);
		factory.setConnectTimeout(10000);
		return new RestTemplate(factory);
	}

	@Bean
	@Qualifier("htmlRestTemplate")
	public RestTemplate htmlRestTemplate() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setReadTimeout(10000);
		factory.setConnectTimeout(10000);

		RestTemplate restTemplate = new RestTemplate(factory);
		List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
		for(HttpMessageConverter<?> converter : converters){
			if(converter instanceof MappingJackson2HttpMessageConverter){
				try{
					((MappingJackson2HttpMessageConverter) converter).setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return restTemplate;
	}

	@Bean
	public GsonJsonParser gsonJsonParser() {
		return new GsonJsonParser();
	}


	@Bean
	public OkHttp3ClientHttpRequestFactory requestFactory() {
		OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory();
		factory.setReadTimeout(30000);
		factory.setConnectTimeout(30000);
		return factory;
	}
}
