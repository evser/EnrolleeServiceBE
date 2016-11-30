package by.bsu.rikz.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import by.bsu.rikz.service.LoginService;

@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@GetMapping("/login")
	public String login(@RequestParam(name = "success", required = false) String success, Authentication auth, HttpSession session) {
		if (auth == null) {
			return "forward:/login-form.html";
		}
		loginService.loginHandler(auth, session);
		return "redirect:/";
	}
}
