package by.bsu.rikz.service;

import org.springframework.security.core.Authentication;

import by.bsu.rikz.bean.PasswordChangeContext;
import by.bsu.rikz.bean.SignUpRequestContext;
import by.bsu.rikz.entity.User;

public interface LoginService {

	boolean changePassword(PasswordChangeContext passwordChangeContext, Authentication auth);

	User getCurrentUser(Authentication auth);

	/**
	 * 
	 * @return true if was signed up, else if user already exists
	 */
	void signUp(SignUpRequestContext signUpRequestContext);

	boolean checkUserIdByLogin(Long id, String login);
}
