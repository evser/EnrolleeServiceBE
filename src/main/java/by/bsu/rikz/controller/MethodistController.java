package by.bsu.rikz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.bsu.rikz.entity.Methodist;
import by.bsu.rikz.service.LoginService;

@RestController
@RequestMapping("/methodist")
public class MethodistController {

	@Autowired
	private LoginService loginService;

	@GetMapping("/university/currentId")
	public Long getCurrentUniversityId(Authentication auth) {
		return ((Methodist) loginService.getCurrentUser(auth)).getUniversity().getId();
	}
}
