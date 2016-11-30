package by.bsu.rikz;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests()
				// .antMatchers("/h2-console/**", "/", "/login").permitAll()
				.anyRequest().permitAll()
				.and().csrf().disable()
				.headers().frameOptions().disable()
				// .and().authorizeRequests().antMatchers("/**").authenticated()
				.and().formLogin().loginPage("/login").usernameParameter("login")
				.successHandler(new SimpleUrlAuthenticationSuccessHandler() {

					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
							throws IOException, ServletException {
						for (Enumeration<String> headerNameEnum = request.getHeaderNames(); headerNameEnum.hasMoreElements();)
							System.out.println(headerNameEnum.nextElement());
						String header = request.getHeader("user-agent");
						if (header.contains("Android")) {
							response.setStatus(HttpServletResponse.SC_OK);
						}
					}
				})
				.failureHandler(new AuthenticationFailureHandler() {

					@Override
					public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
							throws IOException, ServletException {
						response.sendError(HttpServletResponse.SC_FORBIDDEN);
					}
				})
				.and().rememberMe()
				.and().logout().logoutSuccessUrl("/");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
