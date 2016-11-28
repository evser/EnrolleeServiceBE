package by.bsu.rikz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import by.bsu.rikz.entity.Test;

public interface TestRepository extends JpaRepository<Test, Long> {

	// @Query("SELECT DISTINCT test FROM Test test where test.room.university.id = :id")
	List<Test> findByRoomUniversityId(@Param("univesityId") Long univesityId);

	List<Test> findByRoomUniversityIdAndSubjectId(@Param("univesityId") Long univesityId, @Param("subjectId") Long subjectId);
}