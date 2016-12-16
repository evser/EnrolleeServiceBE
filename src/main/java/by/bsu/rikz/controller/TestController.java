package by.bsu.rikz.controller;

import by.bsu.rikz.bean.TestAddContext;
import by.bsu.rikz.bean.TestResultContext;
import by.bsu.rikz.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
	public ResponseEntity<?> addTestResults(@Valid @RequestBody List<TestResultContext> testResults) {
		testService.addTestResults(testResults);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/add")
	public ResponseEntity<?> addTestResults(@Valid @RequestBody TestAddContext testAddContext) {
		return testService.addTest(testAddContext) ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().build();
	}
}
