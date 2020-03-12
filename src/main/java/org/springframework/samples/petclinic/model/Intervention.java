
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * Simple business object representing a medical intervention.
 *
 * @author Carlos Cote
 */
@Entity
@Data
@Table(name = "interventions")

public class Intervention extends NamedEntity {

	@Column(name = "description")
	private String			description;

	@OneToOne
	@JoinColumn(name = "visit_id")
	private Visit			visit;

	@ManyToOne
	@JoinColumn(name = "vet_id")
	private Vet			vet;

	@ManyToMany
	private List<Product>	requiredProducts;
	
}
