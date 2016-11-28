package by.bsu.rikz.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "test_assignment")
@Getter
@Setter
public class TestAssignment extends AbstractEntity {

	private Integer points;

	@ManyToOne
	@JoinColumn(name = "enrollee_id")
	private Enrollee enrollee;

	@ManyToOne
	private Test test;

}