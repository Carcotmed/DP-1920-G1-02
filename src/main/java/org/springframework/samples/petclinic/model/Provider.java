
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Simple JavaBean domain object representing a product provider.
 *
 * @author Carlos Cote
 */
@Entity
@Data
@Table(name = "providers")
public class Provider extends NamedEntity {

	@NotNull
	@Min (100000000l)
	@Max (999999999l)
	private Integer	phone;

	private String	address;

	@Email
	private String	email;

}
