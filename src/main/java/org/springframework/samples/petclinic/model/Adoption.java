
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	@JoinColumn(name = "type_id")
	private PetType		type;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner		owner;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pet", fetch = FetchType.EAGER)
	private Set<Visit>	visits;


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

	public PetType getType() {
		return this.type;
	}

	public void setType(final PetType type) {
		this.type = type;
	}

	public Owner getOwner() {
		return this.owner;
	}

	protected void setOwner(final Owner owner) {
		this.owner = owner;
	}

}
