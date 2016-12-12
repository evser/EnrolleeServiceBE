package by.bsu.rikz;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import by.bsu.rikz.entity.enums.UserRoleEnum;

@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String ENROLLEE_HOME_PAGE = "/enrollee/news.html";

	private static final String METHODIST_HOME_PAGE = "/methodist/add-test.html";

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests()
				.antMatchers("/enrollees/**", "/methodists/**").denyAll()
				.antMatchers("/login", "/signup").anonymous()
				.antMatchers("/h2-console/**", "/", "/browser/**").permitAll()
				.antMatchers("/**").authenticated()
				.and().csrf().disable().headers().frameOptions().disable()
				.and().formLogin().usernameParameter("login")
				.successHandler(new SimpleUrlAuthenticationSuccessHandler() {

					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
							throws IOException, ServletException {
						String header = request.getHeader(HttpHeaders.USER_AGENT);
						if (header != null && header.contains("Android")) {
							response.setStatus(HttpServletResponse.SC_OK);
						} else {
							response.sendRedirect(authentication.getAuthorities().stream()
									.filter(role -> UserRoleEnum.ENROLLEE.name().equals(role.getAuthority()))
									.findFirst()
									.isPresent() ? ENROLLEE_HOME_PAGE : METHODIST_HOME_PAGE);
						}
					}
				})
				.failureHandler((request, response, exception) -> response.sendError(HttpServletResponse.SC_FORBIDDEN))
				.and().rememberMe()
				.and().logout().logoutSuccessHandler((request, response, authentication) -> {
					String header = request.getHeader(HttpHeaders.USER_AGENT);
					if (header != null && header.contains("Android")) {
						response.setStatus(HttpServletResponse.SC_OK);
					} else {
						try {
							response.sendRedirect("/");
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}

				});
		// .and().exceptionHandling().authenticationEntryPoint((request, response, accessDeniedException) -> {
		// if (accessDeniedException != null) {
		// response.sendError(HttpStatus.FORBIDDEN.value(), accessDeniedException.getLocalizedMessage());
		// }
		// });
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
