package ro.ubb.cs.re.jcm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.ubb.cs.re.jcm.dto.CandidateDto;
import ro.ubb.cs.re.jcm.service.CandidateService;

import javax.validation.Valid;

@RestController
@RequestMapping("/rest/candidates")
public class WriteCandidateController {
	private final CandidateService service;

	@Autowired
	public WriteCandidateController(CandidateService service) {
		this.service = service;
	}

	@PostMapping
	public CandidateDto create(@Valid @RequestBody CandidateDto dto) {
		return service.create(dto);
	}

	@PutMapping("/{id}")
	public void update(@PathVariable int id, @Valid @RequestBody CandidateDto dto) {
		service.update(id, dto);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		service.delete(id);
	}
}
