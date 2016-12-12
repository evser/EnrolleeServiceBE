package by.bsu.rikz.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import by.bsu.rikz.bean.TestAddContext;
import by.bsu.rikz.bean.TestResultContext;

public interface TestService {

	public void assignTest(Authentication authentication, Long testId);

	public void addTestResults(List<TestResultContext> testResults);

	public boolean addTest(TestAddContext testAddContext);
}
