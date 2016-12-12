package by.bsu.rikz.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.rest.core.annotation.RestResource;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Test extends AbstractEntity {

	private LocalDateTime date;

	private String type;

	@ManyToOne(optional = false)
	@RestResource(exported = false)
	private Room room;

	@ManyToOne(optional = false)
	@RestResource(exported = false)
	private Subject subject;

	@OneToMany(mappedBy = "test")
	@LazyCollection(LazyCollectionOption.EXTRA)
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