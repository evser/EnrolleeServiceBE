package by.bsu.rikz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import by.bsu.rikz.repository.EnrolleeRepository;
import by.bsu.rikz.repository.MethodistRepository;

public class UserDetailsServiceProvider implements UserDetailsService {

	@Autowired
	private EnrolleeRepository enrolleeRepository;

	@Autowired
	private MethodistRepository methodistRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Assert.notNull(username, "username can't be null");
		// Optional<Enrollee> enrolleeOprional = enrolleeRepository.findByEmail(username);
		// UserRoleEnum roleEnum;
		// if (enrolleeOprional.isPresent()) {
		// roleEnum = UserRoleEnum.ENROLEE;
		// } else {
		// roleEnum = UserRoleEnum.METHODIST;
		// methodistRepository.findByEmail(username);
		// }
		// // TODO check if user not null
		// List<SimpleGrantedAuthority> authorities = user.getUserRoles().stream()
		// .map(userRole -> new SimpleGrantedAuthority(userRole.getRole()))
		// .collect(Collectors.toList());
		// return new User(user.getLogin(), user.getPassword(),
		// user.isEnabled(), true, true, true, authorities);
		return null;
	}

}