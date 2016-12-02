package by.bsu.rikz.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import by.bsu.rikz.bean.PasswordChangeContext;
import by.bsu.rikz.bean.SignUpRequestContext;
import by.bsu.rikz.entity.User;
import by.bsu.rikz.service.LoginService;

@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired
	private LoginService loginService;

	// @GetMapping("/login")
	// public String login(@RequestParam(name = "success", required = false) String success, Authentication auth, HttpSession session) {
	// if (auth == null) {
	// return "forward:/login-form.html";
	// }
	// return "redirect:/";
	// }

	@GetMapping("/users/current")
	public ResponseEntity<User> getCurrentUser(Authentication auth) {
		return ResponseEntity.ok(loginService.getCurrentUser(auth));
	}

	@PatchMapping("/users")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChangeContext passwordChangeContext, Authentication auth) {
		if (loginService.changePassword(passwordChangeContext, auth)) {
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		return ResponseEntity.ok().build();
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequestContext signUpRequestContext) {
		loginService.signUp(signUpRequestContext);
		return ResponseEntity.ok().build();
	}
}
