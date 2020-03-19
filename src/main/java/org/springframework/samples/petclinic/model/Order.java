
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
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

	@ManyToOne(optional = false)
	@JoinColumn(name = "provider_id")
	private Provider	provider;

	@ManyToOne(optional = false)
	@JoinColumn(name = "product_id")
	private Product		Product;

	@ManyToOne
	@JoinColumn(name = "discount_id")
	private Discount	discount;
	
	@AssertTrue
	public boolean isValidDate() {
		if(arrivalDate == null | orderDate == null) {
			return false;
		}
		return arrivalDate.isAfter(orderDate);
	}

}
