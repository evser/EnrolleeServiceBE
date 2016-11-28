package by.bsu.rikz.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Test extends AbstractEntity {

	private LocalDateTime date;

	private String type;

	@ManyToOne
	private Room room;

	@ManyToOne
	private Subject subject;

	@OneToMany(mappedBy = "test")
	private List<TestAssignment> testAssignments;

	public TestAssignment addTestAssignment(TestAssignment testAssignment) {
		getTestAssignments().add(testAssignment);
		testAssignment.setTest(this);

		return testAssignment;
	}

	public TestAssignment removeTestAssignment(TestAssignment testAssignment) {
		getTestAssignments().remove(testAssignment);
		testAssignment.setTest(null);

		return testAssignment;
	}

}