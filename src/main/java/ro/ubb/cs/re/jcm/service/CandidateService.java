package ro.ubb.cs.re.jcm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.cs.re.jcm.dto.CandidateDto;
import ro.ubb.cs.re.jcm.dto.CandidateExportDto;
import ro.ubb.cs.re.jcm.dto.CandidateHeaderDto;
import ro.ubb.cs.re.jcm.entity.Candidate;
import ro.ubb.cs.re.jcm.exception.CandidateNotFoundException;
import ro.ubb.cs.re.jcm.mapper.CandidateMapper;
import ro.ubb.cs.re.jcm.repository.CandidateRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidateService {
	private final CandidateRepository repository;
	private final CandidateMapper mapper;

	@Autowired
	public CandidateService(CandidateRepository repository, CandidateMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Transactional
	public List<CandidateHeaderDto> readAll() {
		return repository.findAll().stream()
			.map(mapper::toCandidateHeaderDto)
			.collect(Collectors.toList());
	}

	@Transactional
	public List<CandidateExportDto> readSelection(List<Integer> ids) {
		return repository.findByIdIn(ids).stream()
			.map(mapper::toCandidateExportDto)
			.collect(Collectors.toList());
	}

	@Transactional
	public CandidateDto read(int id) {
		Candidate candidate = repository.findOne(id);
		if (candidate != null) {
			return mapper.toCandidateDto(candidate);
		} else {
			throw new CandidateNotFoundException(id);
		}
	}

	@Transactional
	public void update(int id, CandidateDto dto) {
		Candidate candidate = repository.findOne(id);
		if (candidate != null) {
			mapper.mergeDtoIntoCandidate(dto, candidate);
			repository.save(candidate);
		} else {
			throw new CandidateNotFoundException(id);
		}
	}

	@Transactional
	public CandidateDto create(CandidateDto dto) {
		Candidate candidate = mapper.toCandidate(dto);
		return mapper.toCandidateDto(repository.save(candidate));
	}

	@Transactional
	public void delete(int id) {
		repository.delete(id);
	}
}
