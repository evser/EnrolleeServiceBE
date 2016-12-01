package by.bsu.rikz.bean;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeContext implements Serializable {

	private static final long serialVersionUID = -85542521769860044L;

	@NotNull
	@Min(5)
	private String oldPassword;

	@NotNull
	@Min(5)
	private String newPassword;

}
