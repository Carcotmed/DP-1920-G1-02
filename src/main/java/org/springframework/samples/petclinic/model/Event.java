
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Future;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "events")
public class Event extends BaseEntity {

	public Event() {
		this.date = LocalDate.now().plusDays(1);
		this.capacity = 0;
	}


	@Future
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	date;

	private Boolean		published;

	private String		description;

	private Integer		capacity;

	private String		place;

	@ManyToOne(optional = true)
	@JoinColumn(name = "provider_id")
	private Provider	sponsor;

}
