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

package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.repository.EventRepository;
import org.springframework.samples.petclinic.repository.PetRepository;

/**
 * Spring Data JPA specialization of the {@link PetRepository} interface
 *
 * @author Michael Isvy
 * @since 15.1.2013
 */
public interface SpringDataEventRepository extends EventRepository, Repository<Event, Integer> {

	@Override
	@Query("SELECT event FROM Event event")
	Collection<Event> findAllEvents() throws DataAccessException;

	@Override
	@Query("SELECT event FROM Event event WHERE event.published = 'true'")
	Collection<Event> findAllPublishedEvents() throws DataAccessException;

	@Override
	@Query("SELECT event FROM Event event where event.id =:eventId")
	Event findEventById(@Param("eventId") int eventId) throws DataAccessException;

	@Override
	@Query("SELECT participation FROM Participation participation where event_id = :eventId")
	Collection<Participation> findParticipationsByEventId(@Param("eventId") int eventId) throws DataAccessException;

}
