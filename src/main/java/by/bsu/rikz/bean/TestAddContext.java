package by.bsu.rikz.bean;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestAddContext implements Serializable {

	private static final long serialVersionUID = -9104484047722741720L;

	@NotNull
	private LocalDateTime date;

	@NotNull
	private Long roomId;

	@NotNull
	private Long subjectId;

	@NotNull
	private String type;
}
