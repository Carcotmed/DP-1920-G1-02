package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="products")
public class Product extends NamedEntity{

	@NotNull
	@Digits(fraction=2, integer=2)
	private Double price;
	
	@NotNull
	@Min(0)
	private Integer quantity;
	
	@NotNull
	private Boolean allAvailable;
	
	@ManyToOne
	@JoinColumn(name="provider_id")
	private Provider provider;
	
}
