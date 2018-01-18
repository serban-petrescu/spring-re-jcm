package ro.ubb.cs.re.jcm.repository;

import org.springframework.data.repository.CrudRepository;
import ro.ubb.cs.re.jcm.entity.University;

import java.util.List;

public interface UniversityRepository extends CrudRepository<University, Integer> {
	@Override
	List<University> findAll();
}
