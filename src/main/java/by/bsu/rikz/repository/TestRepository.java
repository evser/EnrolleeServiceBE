package by.bsu.rikz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import by.bsu.rikz.entity.Test;

public interface TestRepository extends JpaRepository<Test, Long> {

	@RestResource(path = "findByUniversityId")
	List<Test> findByRoomUniversityId(@Param("universityId") Long universityId);

	@RestResource(path = "findByUniversityIdAndSubjectId")
	List<Test> findByRoomUniversityIdAndSubjectId(@Param("universityId") Long universityId, @Param("subjectId") Long subjectId);

	@RestResource(path = "findByEnrolleeId")
	List<Test> findByTestAssignmentsEnrolleeId(@Param("enrolleeId") Long enrolleeId);
}