package by.bsu.rikz.service.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.bsu.rikz.bean.TestResultContext;
import by.bsu.rikz.entity.Enrollee;
import by.bsu.rikz.entity.Test;
import by.bsu.rikz.entity.TestAssignment;
import by.bsu.rikz.repository.EnrolleeRepository;
import by.bsu.rikz.repository.TestAssignmentRepository;
import by.bsu.rikz.repository.TestRepository;
import by.bsu.rikz.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private EnrolleeRepository enroleeRepository;

	@Autowired
	private TestAssignmentRepository testAssignmentRepository;

	@Autowired
	private TestRepository testRepository;

	@Override
	@PreAuthorize("hasRole('ENROLLEE')")
	public void assignTest(Authentication authentication, Long testId) {
		Enrollee enrollee = enroleeRepository.findByEmail(authentication.getName()).get();
		Test test = testRepository.findOne(testId);

		if (testAssignmentRepository.findByEnrolleeIdAndTestId(enrollee.getId(), test.getId()).isPresent()) {
			throw new ValidationException("Enrollee has been assigned to this test");
		}

		if (test.getRoom().getCapacity() <= test.getTestAssignments().size()) {
			throw new ValidationException("No more places available for this room");
		}

		TestAssignment testAssignment = new TestAssignment();
		testAssignment.setEnrollee(enrollee);
		testAssignment.setTest(test);
		testAssignmentRepository.save(testAssignment);
	}

	@Override
	@Transactional
	public void addTestResults(List<TestResultContext> testResults) {
		testResults.forEach(testResult -> {
			Optional<TestAssignment> testOptional = Optional.ofNullable(testAssignmentRepository.findOne(testResult.getTestAssignmentId()));
			TestAssignment testAssignment = testOptional
					.orElseThrow(() -> new ValidationException("Test assignment not found: " + testResult.getTestAssignmentId()));
			testAssignment.setPoints(testResult.getPoints());
		});
	}

}
