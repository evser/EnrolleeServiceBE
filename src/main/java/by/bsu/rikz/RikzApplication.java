package by.bsu.rikz;

import static org.mockito.Matchers.any;

import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

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

}
