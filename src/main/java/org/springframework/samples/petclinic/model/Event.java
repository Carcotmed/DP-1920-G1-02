
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class Event extends NamedEntity {

	@NotNull
	@Column(name = "date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	date;

	@Column(name = "published")
	@NotEmpty
	private Boolean		published;

	@Column(name = "description")
	@NotEmpty
	private String		description;

	@Column(name = "capacity")
	@NotEmpty
	private Integer		capacity;

	@Column(name = "place")
	@NotEmpty
	private String		place;


	public void setDate(final LocalDate date) {
		this.date = date;
	}

	public LocalDate getDate() {
		return this.date;
	}

	public Boolean getpublished() {
		return this.published;
	}

	public void setPublished(final Boolean published) {
		this.published = published;
	}

	public String getDescription() {
		return this.description;
	}

	protected void setDescription(final String description) {
		this.description = description;
	}

	public int getCapacity() {
		return this.capacity;
	}

	protected void setCapacity(final int capacity) {
		this.capacity = capacity;
	}

	public String getPlace() {
		return this.place;
	}

	protected void setPlace(final String place) {
		this.place = place;
	}

}
