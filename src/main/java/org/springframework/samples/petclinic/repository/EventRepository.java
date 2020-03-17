/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.Participation;

/**
 * Repository class for <code>Owner</code> domain objects All method names are compliant
 * with Spring Data naming conventions so this interface can easily be extended for Spring
 * Data See here:
 * http://static.springsource.org/spring-data/jpa/docs/current/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
public interface EventRepository {

	/**
	 * Retrieve all <code>Event</code>s from the data store.
	 *
	 * @return the <code>Event</code>s if found
	 * @throws org.springframework.dao.DataRetrievalFailureException
	 *             if not found
	 */
	Collection<Event> findAllEvents() throws DataAccessException;

	/**
	 * Save an <code>Event</code> to the data store, either inserting or updating it.
	 *
	 * @param event
	 *            the <code>Event</code> to save
	 * @see BaseEntity#isNew
	 */
	void save(Event event) throws DataAccessException;

	/**
	 * Retrieve an <code>Event</code> from the data store by id.
	 *
	 * @param id
	 *            the id to search for
	 * @return the <code>Event</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException
	 *             if not found
	 */
	Event findEventById(int eventId) throws DataAccessException;

	/**
	 * Count the <owner>s who participate in an <code>Event</code>.
	 *
	 * @param id
	 *            the id to search for
	 * @return the amount of owners in an event
	 * @throws org.springframework.dao.DataRetrievalFailureException
	 *             if not found
	 */
	Collection<Participation> findParticipationsByEventId(int eventId) throws DataAccessException;

}
