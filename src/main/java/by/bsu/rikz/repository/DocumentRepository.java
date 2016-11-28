package by.bsu.rikz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import by.bsu.rikz.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

	List<Document> findByEnrolleeId(@Param("id") Long id);
}