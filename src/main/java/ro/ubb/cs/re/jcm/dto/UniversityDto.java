package ro.ubb.cs.re.jcm.dto;

import lombok.Data;

import java.util.List;

@Data
public class UniversityDto {
	private int id;
	private String name;
	private List<SpecializationDto> specializations;
}
