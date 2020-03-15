
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "discounts")
public class Discount extends BaseEntity {

	@NotNull
	@Digits(fraction = 2, integer = 2)
	private Double	percentage;

	@Min(0)
	@NotNull
	private Integer	quantity;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product	product;
	
	@ManyToOne
	@JoinColumn(name = "provider_id")
	private Provider provider;

}
