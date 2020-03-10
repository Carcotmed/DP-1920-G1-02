
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "adoptions")
public class Adoption extends BaseEntity {

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	date;

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	end;

	@OneToOne
	@JoinColumn(name = "pet_id")
	private Pet			pet;

	@OneToOne
	@JoinColumn(name = "owner_id")
	private Owner		owner;

}
