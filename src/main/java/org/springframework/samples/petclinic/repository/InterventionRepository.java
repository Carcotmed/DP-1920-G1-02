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

	//Profiling
	@Query ("SELECT DISTINCT i FROM Intervention i LEFT JOIN FETCH i.visit WHERE i.id = ?1")
	Intervention findInterventionWithVisitById(int interventionId);

	@Query ("SELECT DISTINCT i FROM Intervention i LEFT JOIN FETCH i.requiredProducts WHERE i.id = ?1")
	Intervention findInterventionWithProductsById(int interventionId);

	@Query ("SELECT DISTINCT i FROM Intervention i LEFT JOIN FETCH i.visit LEFT JOIN FETCH i.requiredProducts WHERE i.id = ?1")
	Intervention findInterventionWithVisitAndProductsById(int interventionId);

}
