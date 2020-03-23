
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "discounts")
public class Discount extends BaseEntity {

	@NotNull
	@Digits(fraction = 2, integer = 2)
	@Min(1)
	private Double	percentage;

	@Min(0)
	@NotNull
	private Integer	quantity;

	@ManyToOne(optional = false)
	@NotNull
	@JoinColumn(name = "product_id")
	private Product	product;
	
	@ManyToOne(optional = false)
	@NotNull
	@JoinColumn(name = "provider_id")
	private Provider provider;

}
