package by.bsu.rikz;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Matchers.any;

@SpringBootApplication
@EnableDiscoveryClient
public class RikzApplication extends RepositoryRestMvcConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(RikzApplication.class, args);
	}

    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
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
		halObjectMapper.findAndRegisterModules();
		return halObjectMapper;
	}

	@Override
    @Primary
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = super.objectMapper();
		objectMapper.findAndRegisterModules();
		return objectMapper;
	}

}
