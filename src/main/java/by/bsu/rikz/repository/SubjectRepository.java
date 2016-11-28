package by.bsu.rikz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import by.bsu.rikz.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

	@RestResource(path = "findByUniversityId")
	List<Subject> findByUniversitiesId(@Param("universityId") Long universityId);
}