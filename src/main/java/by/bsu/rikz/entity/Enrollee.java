package by.bsu.rikz.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Enrollee extends User {

	@Column(name = "passport_id")
	private String passportId;

	@Column(name = "phone_number")
	private String phoneNumber;

	@OneToMany(mappedBy = "enrollee")
	private List<Document> documents;

	@OneToMany(mappedBy = "enrollee")
	private List<TestAssignment> testAssignments;

	public Document addDocument(Document document) {
		getDocuments().add(document);
		document.setEnrollee(this);

		return document;
	}

	public Document removeDocument(Document document) {
		getDocuments().remove(document);
		document.setEnrollee(null);

		return document;
	}

	public TestAssignment addTestAssignment(TestAssignment testAssignment) {
		getTestAssignments().add(testAssignment);
		testAssignment.setEnrollee(this);

		return testAssignment;
	}

	public TestAssignment removeTestAssignment(TestAssignment testAssignment) {
		getTestAssignments().remove(testAssignment);
		testAssignment.setEnrollee(null);

		return testAssignment;
	}

}