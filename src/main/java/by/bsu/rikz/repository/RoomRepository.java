package by.bsu.rikz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import by.bsu.rikz.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

}