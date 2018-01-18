package ro.ubb.cs.re.jcm.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Specialization {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	@ManyToOne
	@JoinColumn(name = "university_id")
	private University university;
}
