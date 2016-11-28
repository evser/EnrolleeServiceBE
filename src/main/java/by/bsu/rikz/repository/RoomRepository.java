package by.bsu.rikz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import by.bsu.rikz.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

	List<Room> findByUniversityId(@Param("universityId") Long universityId);
}