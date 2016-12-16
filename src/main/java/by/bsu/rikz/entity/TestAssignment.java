package by.bsu.rikz.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	@RestResource(exported = false)
	private Test test;

}