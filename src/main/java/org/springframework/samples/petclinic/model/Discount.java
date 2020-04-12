
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "discounts")
public class Discount extends BaseEntity {

	@NotNull
	@Digits(fraction = 2, integer = 2)
	@DecimalMin("1.0")
	private Double	percentage;

	@Min(1)
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
	
	@NotNull
	private Boolean enabled;

}
