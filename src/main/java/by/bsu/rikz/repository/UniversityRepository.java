package by.bsu.rikz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import by.bsu.rikz.entity.University;

public interface UniversityRepository extends JpaRepository<University, Long> {

}