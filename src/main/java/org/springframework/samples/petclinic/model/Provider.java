
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

	@NotNull
	@Pattern (regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$")
	private String	phone;

	private String	address;

	@Email
	@NotEmpty
	private String	email;

}
