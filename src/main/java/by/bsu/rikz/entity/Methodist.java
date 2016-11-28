package by.bsu.rikz.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Methodist extends User {

	@ManyToOne
	private University university;

}