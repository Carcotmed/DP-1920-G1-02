
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

public class Adoption {

	@Column(name = "date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	date;

	@Column(name = "end")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	end;

	@OneToOne
	@JoinColumn(name = "pet_id")
	private Pet			pet;

	@OneToOne
	@JoinColumn(name = "owner_id")
	private Owner		owner;


	public void setDate(final LocalDate date) {
		this.date = date;
	}

	public LocalDate getDate() {
		return this.date;
	}
	public void setEnd(final LocalDate date) {
		this.end = date;
	}

	public LocalDate getEnd() {
		return this.end;
	}

	public Pet getpet() {
		return this.pet;
	}

	public void setPet(final Pet pet) {
		this.pet = pet;
	}

	public Owner getOwner() {
		return this.owner;
	}

	protected void setOwner(final Owner owner) {
		this.owner = owner;
	}

}
