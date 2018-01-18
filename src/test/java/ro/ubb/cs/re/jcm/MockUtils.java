package ro.ubb.cs.re.jcm;

import ro.ubb.cs.re.jcm.entity.Candidate;
import ro.ubb.cs.re.jcm.entity.Specialization;
import ro.ubb.cs.re.jcm.entity.University;
import ro.ubb.cs.re.jcm.repository.CandidateRepository;
import ro.ubb.cs.re.jcm.repository.SpecializationRepository;

import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockUtils {

	private MockUtils() {
		super();
	}

	public static SpecializationRepository mockSpecializationRepository() {
		Specialization specialization = getSpecialization();
		SpecializationRepository repository = mock(SpecializationRepository.class);
		when(repository.findAll()).thenReturn(Collections.singletonList(specialization));
		when(repository.findOne(anyInt())).then(a -> {
			if (a.getArguments()[0].equals(1)) {
				return specialization;
			} else {
				return null;
			}
		});
		return repository;
	}


	public static CandidateRepository mockCandidateRepository() {
		Candidate candidate = new Candidate();
		candidate.setId(1);
		candidate.setFirstName("John");
		candidate.setLastName("Doe");
		candidate.setStudyYear(1);
		candidate.setEmail("john.doe@gmail.com");
		candidate.setPhone("0712123321");
		candidate.setSpecialization(getSpecialization());
		CandidateRepository repository = mock(CandidateRepository.class);
		when(repository.findAll()).thenReturn(Collections.singletonList(candidate));
		when(repository.findOne(anyInt())).then(a -> {
			if (a.getArguments()[0].equals(1)) {
				return candidate;
			} else {
				return null;
			}
		});
		when(repository.save(any(Candidate.class))).then(a -> a.getArguments()[0]);
		return repository;
	}


	public static Specialization getSpecialization() {
		Specialization specialization = new Specialization();
		specialization.setId(1);
		specialization.setName("Test Specialization");
		specialization.setUniversity(getUniversity(specialization));
		return specialization;
	}

	public static University getUniversity(Specialization specialization) {
		University university = new University();
		university.setId(1);
		university.setName("UBB");
		university.setSpecializations(Collections.singletonList(specialization));
		return university;
	}




}
