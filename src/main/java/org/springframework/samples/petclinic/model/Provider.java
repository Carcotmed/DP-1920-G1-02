
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

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

	@NotEmpty
	@Pattern(regexp = "(^$|[0-9]{10})")
	private Integer	phone;

	private String	address;

	@Email
	private String	email;

}
