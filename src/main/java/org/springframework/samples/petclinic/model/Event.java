
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "events")
public class Event extends NamedEntity {

	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	date;

	@NotEmpty
	private Boolean		published;

	@NotEmpty
	private String		description;

	@NotEmpty
	private Integer		capacity;

	@NotEmpty
	private String		place;

}
