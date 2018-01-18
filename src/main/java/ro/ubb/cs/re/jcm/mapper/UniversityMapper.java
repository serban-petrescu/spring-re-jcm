package ro.ubb.cs.re.jcm.mapper;

import org.springframework.stereotype.Component;
import ro.ubb.cs.re.jcm.dto.SpecializationDto;
import ro.ubb.cs.re.jcm.dto.UniversityDto;
import ro.ubb.cs.re.jcm.entity.Specialization;
import ro.ubb.cs.re.jcm.entity.University;

import java.util.stream.Collectors;


@Component
public class UniversityMapper {

	public UniversityDto toUniversityDto(University university) {
		UniversityDto universityDto = new UniversityDto();
		universityDto.setId(university.getId());
		universityDto.setName(university.getName());
		universityDto.setSpecializations(university.getSpecializations().stream()
			.map(this::toSpecializationDto)
			.collect(Collectors.toList()));
		return universityDto;

	}

	private SpecializationDto toSpecializationDto(Specialization specialization) {
		SpecializationDto specializationDto = new SpecializationDto();
		specializationDto.setId(specialization.getId());
		specializationDto.setName(specialization.getName());
		return specializationDto;
	}
}
