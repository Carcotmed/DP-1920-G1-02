
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "events")
public class Event extends BaseEntity {

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	date;

	private Boolean		published;

	private String		description;

	private Integer		capacity;

	private String		place;

}
