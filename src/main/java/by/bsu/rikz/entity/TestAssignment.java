package by.bsu.rikz.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.springframework.data.rest.core.annotation.RestResource;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "test_assignment")
@Getter
@Setter
public class TestAssignment extends AbstractEntity {

	@Min(0)
	private Integer points;

	@ManyToOne
	@JoinColumn(name = "enrollee_id")
	@RestResource(exported = false)
	private Enrollee enrollee;

	@ManyToOne
	@RestResource(exported = false)
	private Test test;

}