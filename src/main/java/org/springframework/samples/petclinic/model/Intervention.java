package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Simple business object representing a pet.
 *
 * @author Carlos Cote
 */
@Entity
@Table(name = "intervention")
public class Intervention extends NamedEntity {
	
	@Column(name = "date")        
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate Date;
	
	@Column(name = "description")
	private String description;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "pet_id")
	private Pet pet;

	@ManyToOne
	@JoinColumn(name = "visit_id")
	private Visit visit;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;
	
	@ManyToMany (cascade = CascadeType.ALL, mappedBy = "product_id", fetch = FetchType.EAGER)	
	private Collection <Product> requiredProducts;

}
