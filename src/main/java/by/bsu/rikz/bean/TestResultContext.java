package by.bsu.rikz.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class TestResultContext implements Serializable {

	private static final long serialVersionUID = 1601793892219354292L;

	@NonNull
	private Long testAssignmentId;

	@NonNull
	private Integer points;

}
