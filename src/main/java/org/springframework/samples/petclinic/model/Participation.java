
package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Entity
@Data
@Table(name = "participations")
public class Participation extends BaseEntity {

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Pet>	pets;

	@OneToOne
	@JoinColumn(name = "event_id")
	@NotEmpty
	private Event		event;

	@OneToOne
	@JoinColumn(name = "owner_id")
	@NotEmpty
	private Owner		owner;

}
