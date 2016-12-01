package by.bsu.rikz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.bsu.rikz.repository.TestRepository;
import by.bsu.rikz.service.TestService;

@RestController
@RequestMapping("/")
public class TestController {

	@Autowired
	private TestService testService;

	@Autowired
	private TestRepository testRepository;

	// @PutMapping("/assign/{testId}")
	// public ResponseEntity<?> assignTest(@PathVariable("testId") Long testId) {
	//
	// }

}
