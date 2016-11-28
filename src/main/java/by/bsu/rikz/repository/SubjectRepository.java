package by.bsu.rikz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import by.bsu.rikz.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

}