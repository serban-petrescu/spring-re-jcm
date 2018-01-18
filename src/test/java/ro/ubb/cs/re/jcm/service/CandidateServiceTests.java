package ro.ubb.cs.re.jcm.service;

import org.junit.Test;
import ro.ubb.cs.re.jcm.dto.CandidateDto;
import ro.ubb.cs.re.jcm.dto.CandidateHeaderDto;
import ro.ubb.cs.re.jcm.exception.CandidateNotFoundException;
import ro.ubb.cs.re.jcm.mapper.CandidateMapper;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static ro.ubb.cs.re.jcm.MockUtils.mockCandidateRepository;
import static ro.ubb.cs.re.jcm.MockUtils.mockSpecializationRepository;

public class CandidateServiceTests {

	@Test
	public void testReadAllSuccessful() {
		CandidateService service = createService();

		List<CandidateHeaderDto> candidates = service.readAll();

		assertNotNull(candidates);
		assertEquals(1, candidates.size());
		assertEquals("John Doe", candidates.get(0).getFullName());
	}

	@Test
	public void testReadSingleSuccessful() {
		CandidateService service = createService();

		CandidateDto candidate = service.read(1);

		assertNotNull(candidate);
		assertEquals("John", candidate.getFirstName());
		assertEquals("Doe", candidate.getLastName());
	}

	@Test(expected = CandidateNotFoundException.class)
	public void testReadSingleThrowsExceptionWhenNotFound() {
		CandidateService service = createService();

		service.read(2);
	}

	private CandidateService createService() {
		return new CandidateService(mockCandidateRepository(), new CandidateMapper(mockSpecializationRepository()));
	}
}
