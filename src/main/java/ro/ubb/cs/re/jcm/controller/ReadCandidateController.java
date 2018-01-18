package ro.ubb.cs.re.jcm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.ubb.cs.re.jcm.dto.CandidateDto;
import ro.ubb.cs.re.jcm.dto.CandidateExportDto;
import ro.ubb.cs.re.jcm.dto.CandidateHeaderDto;
import ro.ubb.cs.re.jcm.service.CandidateService;

import java.util.List;

@RestController
@RequestMapping("/rest/candidates")
public class ReadCandidateController {
	private final CandidateService service;

	@Autowired
	public ReadCandidateController(CandidateService service) {
		this.service = service;
	}

	@GetMapping
	public List<CandidateHeaderDto> readAll() {
		return service.readAll();
	}

	@GetMapping("/{id}")
	public CandidateDto read(@PathVariable int id) {
		return service.read(id);
	}

	@PostMapping("/export")
	public List<CandidateExportDto> export(@RequestBody List<Integer> ids) {
		return service.readSelection(ids);
	}
}
