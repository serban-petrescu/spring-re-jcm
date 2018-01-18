package ro.ubb.cs.re.jcm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.cs.re.jcm.dto.UniversityDto;
import ro.ubb.cs.re.jcm.mapper.UniversityMapper;
import ro.ubb.cs.re.jcm.repository.UniversityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UniversityService {
	private final UniversityRepository repository;
	private final UniversityMapper mapper;

	@Autowired
	public UniversityService(UniversityRepository repository, UniversityMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	public List<UniversityDto> readAll() {
		return repository.findAll().stream()
			.map(mapper::toUniversityDto)
			.collect(Collectors.toList());
	}
}
