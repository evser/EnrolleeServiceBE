package by.bsu.rikz.service.impl;

import by.bsu.rikz.bean.TestAddContext;
import by.bsu.rikz.bean.TestInfoContext;
import by.bsu.rikz.bean.TestResultContext;
import by.bsu.rikz.entity.Enrollee;
import by.bsu.rikz.entity.Test;
import by.bsu.rikz.entity.TestAssignment;
import by.bsu.rikz.repository.*;
import by.bsu.rikz.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.validation.ValidationException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TestServiceImpl implements TestService {

    private static final String MAIL_URL = "http://MAIL-SERVICE";

	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;

	@Autowired
	private EnrolleeRepository enroleeRepository;

	@Autowired
	private TestAssignmentRepository testAssignmentRepository;

	@Autowired
	private TestRepository testRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Override
	@PreAuthorize("hasRole('ENROLLEE')")
	public void assignTest(Authentication authentication, Long testId) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
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


		TestInfoContext testInfoContext = new TestInfoContext();
		testInfoContext.setName(testAssignment.getEnrollee().getFirstName());
		testInfoContext.setType(testAssignment.getTest().getType());
		testInfoContext.setAddress(testAssignment.getTest().getRoom().getUniversity().getAddress());
		testInfoContext.setDateTime(testAssignment.getTest().getDate().format(formatter));
		testInfoContext.setPoints(testAssignment.getPoints());
		testInfoContext.setSubject(testAssignment.getTest().getSubject().getName());
		testInfoContext.setRoom(testAssignment.getTest().getRoom().getNumber());
		testInfoContext.setUniversity(testAssignment.getTest().getRoom().getUniversity().getName());
		testInfoContext.setEmail(testAssignment.getEnrollee().getEmail());
		List<HttpMessageConverter<?>> converters = new ArrayList<>();
		converters.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(converters);
		HttpEntity<TestInfoContext> testAssignmentHttpEntity = new HttpEntity<>(testInfoContext);
		restTemplate.exchange(MAIL_URL + "/sendNotification", HttpMethod.POST,
				testAssignmentHttpEntity, Void.class);
	}

	@Override
	@Transactional
	public void addTestResults(List<TestResultContext> testResults) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		testResults.forEach(testResult -> {
			Optional<TestAssignment> testOptional = Optional.ofNullable(testAssignmentRepository.findOne(testResult.getTestAssignmentId()));
			TestAssignment testAssignment = testOptional
					.orElseThrow(() -> new ValidationException("Test assignment not found: " + testResult.getTestAssignmentId()));
			testAssignment.setPoints(testResult.getPoints());

			TestInfoContext testInfoContext = new TestInfoContext();
			testInfoContext.setName(testAssignment.getEnrollee().getFirstName());
			testInfoContext.setType(testAssignment.getTest().getType());
			testInfoContext.setAddress(testAssignment.getTest().getRoom().getUniversity().getAddress());
			testInfoContext.setDateTime(testAssignment.getTest().getDate().format(formatter));
			testInfoContext.setPoints(testAssignment.getPoints());
			testInfoContext.setSubject(testAssignment.getTest().getSubject().getName());
			testInfoContext.setRoom(testAssignment.getTest().getRoom().getNumber());
			testInfoContext.setUniversity(testAssignment.getTest().getRoom().getUniversity().getName());
			testInfoContext.setEmail(testAssignment.getEnrollee().getEmail());
			List<HttpMessageConverter<?>> converters = new ArrayList<>();
			converters.add(new MappingJackson2HttpMessageConverter());
			restTemplate.setMessageConverters(converters);
			HttpEntity<TestInfoContext> testAssignmentHttpEntity = new HttpEntity<>(testInfoContext);
			restTemplate.exchange(MAIL_URL + "/sendResult", HttpMethod.POST,
					testAssignmentHttpEntity, Void.class);
		});
	}

	@Override
	@Transactional
	public boolean addTest(TestAddContext testAddContext) {
		Test existingTest = testRepository.findFirstByRoomIdAndDate(testAddContext.getRoomId(), testAddContext.getDate());
		if (existingTest != null) {
			return false;
		}
		Test test = new Test();
		test.setRoom(roomRepository.findOne(testAddContext.getRoomId()));
		test.setSubject(subjectRepository.findOne(testAddContext.getSubjectId()));
		test.setDate(testAddContext.getDate());
		test.setType(testAddContext.getType());
		testRepository.save(test);
		return true;
	}

}
