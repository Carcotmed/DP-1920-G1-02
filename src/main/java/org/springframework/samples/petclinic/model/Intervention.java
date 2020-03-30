
package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
	@NotBlank
	private String			description;

	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "visit_id")
	private Visit			visit;

	@ManyToOne (optional = false)
	@JoinColumn(name = "vet_id")
	private Vet			vet;

	@ManyToMany
	@NotNull
	private List<Product>	requiredProducts;

	public Intervention() {
		this.requiredProducts = new ArrayList<Product> ();
	}
	
	
	
}
