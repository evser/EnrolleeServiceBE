package by.bsu.rikz.bean;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.crypto.password.PasswordEncoder;

import by.bsu.rikz.entity.Enrollee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestContext {

	@Email
	private String email;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	private String middleName;

	@NotNull
	@Min(5)
	private String password;

	@NotBlank
	private String passportId;

	@NotBlank
	private String phoneNumber;

	public Enrollee toEnrolee(PasswordEncoder passwordEncoder) {
		Enrollee enrollee = new Enrollee();
		enrollee.setEmail(email);
		enrollee.setFirstName(firstName);
		enrollee.setLastName(lastName);
		enrollee.setMiddleName(middleName);
		enrollee.setPassword(passwordEncoder.encode(password));
		enrollee.setPassportId(passportId);
		enrollee.setPhoneNumber(phoneNumber);
		return enrollee;
	}
}
