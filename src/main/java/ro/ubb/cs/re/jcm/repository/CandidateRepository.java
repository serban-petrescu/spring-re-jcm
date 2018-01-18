package ro.ubb.cs.re.jcm.repository;

import org.springframework.data.repository.CrudRepository;
import ro.ubb.cs.re.jcm.entity.Candidate;

import java.util.Collection;
import java.util.List;

public interface CandidateRepository extends CrudRepository<Candidate, Integer> {
	@Override
	List<Candidate> findAll();

	List<Candidate> findByIdIn(Collection<Integer> idList);
}
