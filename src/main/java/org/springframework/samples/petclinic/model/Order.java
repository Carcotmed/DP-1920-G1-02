
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

	@NotNull
	@Min(0)
	private Integer		quantity;

	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	orderDate;

	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	arrivalDate;

	@NotNull
	private Boolean		sent;

	@ManyToOne
	@JoinColumn(name = "provider_id")
	private Provider	provider;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product		Product;

	@ManyToOne(optional = true)
	@JoinColumn(name = "discount_id")
	private Discount	discount;

}
