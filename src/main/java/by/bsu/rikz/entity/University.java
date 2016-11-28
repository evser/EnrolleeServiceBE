package by.bsu.rikz.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class University extends AbstractEntity {

	private String address;

	private String name;

	@ManyToMany
	@JoinTable(name = "available_subject", joinColumns = { @JoinColumn(name = "subject_id") }, inverseJoinColumns = { @JoinColumn(name = "university_id") })
	private List<Subject> subjects;

	@OneToMany(mappedBy = "university")
	private List<Methodist> methodists;

	@OneToMany(mappedBy = "university")
	private List<Room> rooms;

	public Methodist addMethodist(Methodist methodist) {
		getMethodists().add(methodist);
		methodist.setUniversity(this);
		return methodist;
	}

	public Methodist removeMethodist(Methodist methodist) {
		getMethodists().remove(methodist);
		methodist.setUniversity(null);
		return methodist;
	}

	public Room addRoom(Room room) {
		getRooms().add(room);
		room.setUniversity(this);
		return room;
	}

	public Room removeRoom(Room room) {
		getRooms().remove(room);
		room.setUniversity(null);
		return room;
	}

}