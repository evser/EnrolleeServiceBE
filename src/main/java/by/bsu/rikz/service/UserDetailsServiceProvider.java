package by.bsu.rikz.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import by.bsu.rikz.entity.User;
import by.bsu.rikz.entity.enums.UserRoleEnum;
import by.bsu.rikz.repository.EnrolleeRepository;
import by.bsu.rikz.repository.MethodistRepository;

@Service
public class UserDetailsServiceProvider implements UserDetailsService {

	@Autowired
	private EnrolleeRepository enrolleeRepository;

	@Autowired
	private MethodistRepository methodistRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Assert.notNull(username, "username can't be null");
		Optional<? extends User> userOptional = enrolleeRepository.findByEmail(username);
		UserRoleEnum roleEnum;
		if (userOptional.isPresent()) {
			roleEnum = UserRoleEnum.ENROLLEE;
		} else {
			userOptional = methodistRepository.findByEmail(username);
			roleEnum = UserRoleEnum.METHODIST;
		}

		User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User with a such login doesn't exist: " + username));
		List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(roleEnum.name()));
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

}