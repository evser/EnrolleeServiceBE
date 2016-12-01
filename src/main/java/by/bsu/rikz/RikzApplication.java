package by.bsu.rikz;

import static org.mockito.Matchers.any;

import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootApplication
public class RikzApplication extends RepositoryRestMvcConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(RikzApplication.class, args);
	}

	@Override
	public RepositoryRestConfiguration config() {
		RepositoryRestConfiguration config = Mockito.spy(super.config());
		Mockito.when(config.isIdExposedFor(any())).thenReturn(true);
		return config;
	}

	@Override
	@Bean
	public ObjectMapper halObjectMapper() {
		ObjectMapper halObjectMapper = super.halObjectMapper();
		halObjectMapper.registerModule(new JavaTimeModule());
		return halObjectMapper;
	}

}
