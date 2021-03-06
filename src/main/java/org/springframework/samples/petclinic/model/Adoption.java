
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "adoptions")
public class Adoption extends BaseEntity {

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@FutureOrPresent
	private LocalDate	date;

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	end;

	@OneToOne
	@JoinColumn(name = "pet_id")
	@NotNull
	private Pet			pet;

	@OneToOne
	@JoinColumn(name = "owner_id")
	@NotNull
	private Owner		owner;


	@AssertTrue
	public boolean isValidDate() {
		if (this.end == null) {
			return true;
		} else {
			return this.end.isAfter(this.date);
		}
	}
	@AssertTrue
	public boolean isValidEnd() {
		if (this.end == null) {
			return true;
		} else {
			return this.end.isAfter(LocalDate.now());
		}
	}
}
