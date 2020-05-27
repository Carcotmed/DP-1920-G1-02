/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.projections.VisitIntervention;

/**
 * Repository class for <code>Visit</code> domain objects All method names are compliant
 * with Spring Data naming conventions so this interface can easily be extended for Spring
 * Data See here:
 * http://static.springsource.org/spring-data/jpa/docs/current/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
public interface VisitRepository {

	/**
	 * Save a <code>Visit</code> to the data store, either inserting or updating it.
	 * @param visit the <code>Visit</code> to save
	 * @see BaseEntity#isNew
	 */
	void save(Visit visit) throws DataAccessException;

	List<Visit> findByPetId(Integer petId);

	Visit findById(int visitId);
	
	@Query ("SELECT v.id AS visitId, v.description AS visitDescription, v.bringer AS visitBringer, v.date AS visitDate, i.id AS interventionId, i.name AS interventionName, i.vet.firstName AS interventionFirstName, i.vet.lastName AS interventionLastName FROM Visit v INNER JOIN v.intervention i WHERE v.pet.id = ?1")
	List <VisitIntervention> findVisitInterventionByPetId (Integer petId);

}
