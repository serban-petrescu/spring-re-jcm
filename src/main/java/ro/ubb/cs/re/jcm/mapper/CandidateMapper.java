package ro.ubb.cs.re.jcm.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.cs.re.jcm.dto.CandidateDto;
import ro.ubb.cs.re.jcm.dto.CandidateExportDto;
import ro.ubb.cs.re.jcm.dto.CandidateHeaderDto;
import ro.ubb.cs.re.jcm.entity.Candidate;
import ro.ubb.cs.re.jcm.entity.Specialization;
import ro.ubb.cs.re.jcm.exception.SpecializationNotFoundException;
import ro.ubb.cs.re.jcm.repository.SpecializationRepository;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class CandidateMapper {
	private final SpecializationRepository specializationRepository;

	@Autowired
	public CandidateMapper(SpecializationRepository specializationRepository) {
		this.specializationRepository = specializationRepository;
	}

	public CandidateHeaderDto toCandidateHeaderDto(Candidate candidate) {
		CandidateHeaderDto candidateHeaderDto = new CandidateHeaderDto();
		candidateHeaderDto.setId(candidate.getId());
		candidateHeaderDto.setFullName(candidate.getFirstName() + " " + candidate.getLastName());
		candidateHeaderDto.setStudyLocation(getStudyLocation(candidate.getSpecialization()));
		candidateHeaderDto.setStudyYear(candidate.getStudyYear());
		candidateHeaderDto.setEmail(candidate.getEmail());
		return candidateHeaderDto;

	}

	public CandidateDto toCandidateDto(Candidate candidate) {
		CandidateDto candidateDto = new CandidateDto();
		candidateDto.setId(candidate.getId());
		candidateDto.setFirstName(candidate.getFirstName());
		candidateDto.setLastName(candidate.getLastName());
		candidateDto.setEmail(candidate.getEmail());
		candidateDto.setPhone(candidate.getPhone());
		candidateDto.setStudyYear(candidate.getStudyYear());
		candidateDto.setSpecialization(candidate.getSpecialization().getId());
		return candidateDto;
	}

	public CandidateExportDto toCandidateExportDto(Candidate candidate) {
	    CandidateExportDto candidateExportDto= new CandidateExportDto();
	    candidateExportDto.setFirstName(candidate.getFirstName());
	    candidateExportDto.setLastName(candidate.getLastName());
	    candidateExportDto.setEmail(candidate.getEmail());
	    candidateExportDto.setPhone(candidate.getPhone());
	    candidateExportDto.setUniversity(candidate.getSpecialization().getUniversity().getName());
	    candidateExportDto.setSpecialization(candidate.getSpecialization().getName());
	    candidateExportDto.setStudyYear(candidate.getStudyYear());
	    return candidateExportDto;

	}

	public Candidate toCandidate(CandidateDto dto) {
		Candidate candidate = new Candidate();
		mergeDtoIntoCandidate(dto, candidate);
		return candidate;
	}

	public void mergeDtoIntoCandidate(CandidateDto dto, Candidate candidate) {
		candidate.setFirstName(dto.getFirstName());
		candidate.setLastName(dto.getLastName());
		candidate.setEmail(dto.getEmail());
		candidate.setPhone(dto.getPhone());
		candidate.setStudyYear(dto.getStudyYear());
		Specialization specialization = specializationRepository.findOne(dto.getSpecialization());
		if (specialization != null) {
			candidate.setSpecialization(specialization);
		} else {
			throw new SpecializationNotFoundException();
		}
	}

	private static String getStudyLocation(Specialization specialization) {
		String abbrev = getAbbreviation(specialization.getUniversity().getName());
		return abbrev + ": " + specialization.getName();
	}

	private static String getAbbreviation(String name) {
		return Arrays.stream(Pattern.compile("[,. -]").split(name))
				.filter(s -> !s.isEmpty())
				.map(s -> s.substring(0, 1) + ".")
				.collect(Collectors.joining());
	}

}
