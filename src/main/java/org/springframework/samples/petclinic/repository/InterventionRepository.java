package org.springframework.samples.petclinic.repository;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Intervention;

public interface InterventionRepository extends CrudRepository<Intervention, Integer>{

	@Query ("SELECT i FROM Intervention i WHERE i.id = ?1")
	Intervention findInterventionById(int interventionId);

	@Query ("SELECT i FROM Intervention i WHERE i.visit.date = ?1")
	Collection<Intervention> getInterventionsOfDay(LocalDate date);

}
