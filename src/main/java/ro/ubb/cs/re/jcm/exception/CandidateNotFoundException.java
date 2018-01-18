package ro.ubb.cs.re.jcm.exception;

public class CandidateNotFoundException extends RuntimeException {
	public CandidateNotFoundException(int id) {
		super("The candidate with id " + id + " was not found in the database.");
	}
}
