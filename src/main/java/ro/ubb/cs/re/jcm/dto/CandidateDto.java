package ro.ubb.cs.re.jcm.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.*;

@Data
public class CandidateDto {
	private Integer id;

	@NotNull(message = "First name may not be empty.")
	@Size(min = 1, max = 64, message = "First name may not be empty or longer than 64 characters.")
	private String firstName;

	@NotNull(message = "Last name may not be empty.")
	@Size(min = 1, max = 64, message = "Last name may not be empty or longer than 64 characters.")
	private String lastName;

	@Email(message = "The given email address is not valid.")
	@NotNull(message = "Email address may not be empty.")
	@NotEmpty(message = "Email address may not be empty.")
	private String email;

	@Size(max = 20, message = "Phone number may not be longer than 20 characters.")
	@Pattern(regexp = "^((\\+4|004)?[ |-]?\\d{4}[ |-]?\\d{3}[ |-]?\\d{3})?$", message = "The given phone number is invalid.")
	private String phone;

	@Min(value = 1, message = "The study year is invalid.")
	@Max(value = 6, message = "The study year is invalid.")
	private int studyYear;

	@NotNull(message = "Specialization may not be empty.")
	private int specialization;
}
