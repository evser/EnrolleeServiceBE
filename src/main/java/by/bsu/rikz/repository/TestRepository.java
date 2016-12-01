package by.bsu.rikz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import by.bsu.rikz.entity.Test;

public interface TestRepository extends JpaRepository<Test, Long> {

	@RestResource(path = "findByUniversityId")
	List<Test> findByRoomUniversityId(@Param("universityId") Long universityId);

	@RestResource(path = "findByUniversityIdAndSubjectId")
	@Query("SELECT test From Test test WHERE test.room.university.id = :universityId AND test.subject.id = :subjectId AND test.room.capacity > size(test.testAssignments)")
	List<Test> findByRoomUniversityIdAndSubjectId(@Param("universityId") Long universityId, @Param("subjectId") Long subjectId);
}