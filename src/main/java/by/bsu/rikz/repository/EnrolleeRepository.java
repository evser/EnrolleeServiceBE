package by.bsu.rikz.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import by.bsu.rikz.entity.Enrollee;

public interface EnrolleeRepository extends UserBaseRepository<Enrollee> {

	List<Enrollee> findByTestAssignmentsId(@Param("testAssignmentId") Long testAssignmentId);
}