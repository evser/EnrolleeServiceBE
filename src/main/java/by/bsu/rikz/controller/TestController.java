package by.bsu.rikz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.bsu.rikz.bean.TestResultContext;
import by.bsu.rikz.service.TestService;

@RestController
@RequestMapping("/tests")
public class TestController {

	@Autowired
	private TestService testService;

	@PutMapping("/assign/{testId}")
	public ResponseEntity<?> assignTest(@PathVariable("testId") Long testId, Authentication authentication) {
		testService.assignTest(authentication, testId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/result")
	public ResponseEntity<?> addTestResults(@RequestBody List<TestResultContext> testResults) {
		testService.addTestResults(testResults);
		return ResponseEntity.noContent().build();
	}

}
