package by.bsu.rikz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import by.bsu.rikz.entity.TestAssignment;

public interface TestAssignmentRepository extends JpaRepository<TestAssignment, Long> {

	@RestResource(exported = false)
	Optional<TestAssignment> findByEnrolleeIdAndTestId(Long enrolleeId, Long testId);

	List<TestAssignment> findByEnrolleeId(@Param("enrolleeId") Long enrolleeId);

	@RestResource(path = "findByUniversityIdAndSubjectId")
	List<TestAssignment> findByTestRoomUniversityIdAndTestSubjectId(@Param("universityId") Long universityId, @Param("subjectId") Long subjectId);

}