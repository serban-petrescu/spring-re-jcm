package ro.ubb.cs.re.jcm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.cs.re.jcm.dto.UniversityDto;
import ro.ubb.cs.re.jcm.service.UniversityService;

import java.util.List;

@RestController
@RequestMapping("/rest/universities")
public class ReadUniversityController {
	private final UniversityService service;

	@Autowired
	public ReadUniversityController(UniversityService service) {
		this.service = service;
	}

	@GetMapping
	public List<UniversityDto> readAll() {
		return service.readAll();
	}
}
