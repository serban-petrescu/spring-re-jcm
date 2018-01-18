package ro.ubb.cs.re.jcm.exception;

public class SpecializationNotFoundException extends RuntimeException {
	public SpecializationNotFoundException() {
		super("No specialization was selected.");
	}
}
