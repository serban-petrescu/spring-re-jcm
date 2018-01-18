package ro.ubb.cs.re.jcm.dto;

import lombok.Data;

@Data
public class CandidateHeaderDto {
	private int id;
	private String fullName;
	private String studyLocation;
	private int studyYear;
	private String email;
}
