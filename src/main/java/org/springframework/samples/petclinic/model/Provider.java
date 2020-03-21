
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
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
	@Digits(fraction = 0, integer = 9)
	@Min (0)
	private Integer	phone;

	private String	address;

	@Email
	@NotEmpty
	private String	email;

}
