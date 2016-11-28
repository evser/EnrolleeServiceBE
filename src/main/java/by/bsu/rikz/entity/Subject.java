package by.bsu.rikz.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Subject extends AbstractEntity {

	private String code;

	private String name;

	@ManyToMany(mappedBy = "subjects")
	private List<University> universities;

	@OneToMany(mappedBy = "subject")
	private List<Test> tests;

	public Test addTest(Test test) {
		getTests().add(test);
		test.setSubject(this);
		return test;
	}

	public Test removeTest(Test test) {
		getTests().remove(test);
		test.setSubject(null);
		return test;
	}

}