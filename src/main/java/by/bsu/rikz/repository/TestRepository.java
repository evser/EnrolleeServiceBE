package by.bsu.rikz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import by.bsu.rikz.entity.Test;

public interface TestRepository extends JpaRepository<Test, Long> {

}