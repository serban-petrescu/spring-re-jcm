package ro.ubb.cs.re.jcm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"firstName", "lastName", "email", "phone", "university", "specialization", "studyYear"})
public class CandidateExportDto {
	@JsonProperty("First Name")
	private String firstName;
	@JsonProperty("Last Name")
	private String lastName;
	@JsonProperty("Email")
	private String email;
	@JsonProperty("Phone")
	private String phone;
	@JsonProperty("University")
	private String university;
	@JsonProperty("Specialization")
	private String specialization;
	@JsonProperty("Year")
	private int studyYear;
}
