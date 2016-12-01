package by.bsu.rikz;

import static org.mockito.Matchers.any;

import java.util.List;

import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

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

	@Bean
	public HttpMessageConverters httpMessageConverters(final Jackson2ObjectMapperBuilder builder, List<HttpMessageConverter<?>> converters) {
		return new HttpMessageConverters(converters) {

			@Override
			protected List<HttpMessageConverter<?>> postProcessConverters(List<HttpMessageConverter<?>> converters) {
				for (HttpMessageConverter<?> converter : converters) {
					if (converter instanceof MappingJackson2HttpMessageConverter) {
						builder.configure(((MappingJackson2HttpMessageConverter) converter).getObjectMapper());
					}
				}
				return converters;
			}
		};
	}
}
