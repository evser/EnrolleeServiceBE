package by.bsu.rikz.service;

import java.util.Optional;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.bsu.rikz.bean.PasswordChangeContext;
import by.bsu.rikz.bean.SignUpRequestContext;
import by.bsu.rikz.entity.Enrollee;
import by.bsu.rikz.entity.User;
import by.bsu.rikz.repository.EnrolleeRepository;
import by.bsu.rikz.repository.MethodistRepository;
import by.bsu.rikz.repository.UserRepository;

@Service
public class LoginService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private EnrolleeRepository enrolleeRepository;

	@Autowired
	private MethodistRepository methodistRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public boolean changePassword(PasswordChangeContext passwordChangeContext, Authentication auth) {
		User user = userRepository.findByEmail(auth.getName()).get();
		boolean passwordMatches = passwordEncoder.matches(passwordChangeContext.getOldPassword(), user.getPassword());
		if (passwordMatches) {
			user.setPassword(passwordEncoder.encode(passwordChangeContext.getNewPassword()));
			userRepository.save(user);
		}
		return passwordMatches;
	}

	public User getCurrentUser(Authentication auth) {
		Optional<Enrollee> enroleeOptional = enrolleeRepository.findByEmail(auth.getName());
		return enroleeOptional.isPresent() ? enroleeOptional.get() : methodistRepository.findByEmail(auth.getName()).get();
	}

	/**
	 * 
	 * @return true if was signed up, else if user already exists
	 */
	@Transactional
	public void signUp(SignUpRequestContext signUpRequestContext) {
		Optional<User> userByEmail = userRepository.findByEmail(signUpRequestContext.getEmail());
		if (userByEmail.isPresent()) {
			throw new ValidationException("User with a such login already presents: " + signUpRequestContext.getEmail());
		}

		enrolleeRepository.save(signUpRequestContext.toEnrolee(passwordEncoder));
		autologin(signUpRequestContext.getEmail(), signUpRequestContext.getPassword());
	}

	private void autologin(String username, String password) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

		authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		if (usernamePasswordAuthenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}
	}
}
