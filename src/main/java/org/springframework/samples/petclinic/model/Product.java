package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name="products")
public class Product extends NamedEntity{

	@NotNull
	@Digits(fraction=2, integer=2)
	@Min(0)
	private Double price;
	
	@NotNull
	@Min(0)
	private Integer quantity;
	
	@NotNull
	private Boolean allAvailable;
	
	@ManyToOne(optional = false)
	@NotNull
	@JoinColumn(name="provider_id")
	private Provider provider;
	
	@NotNull
	private Boolean enabled;
	
}
