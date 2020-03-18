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

package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.repository.EventRepository;
import org.springframework.samples.petclinic.repository.ParticipationRepository;
import org.springframework.stereotype.Service;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class EventService {

	private EventRepository			eventRepository;
	private ParticipationRepository	participationRepository;


	@Autowired
	public EventService(final EventRepository eventRepository, final ParticipationRepository participationRepository) {
		this.eventRepository = eventRepository;
		this.participationRepository = participationRepository;
	}

	public Collection<Event> findAllEvents() {
		return this.eventRepository.findAllEvents();
	}

	public Event findEventById(final int eventId) {
		return this.eventRepository.findEventById(eventId);
	}

	public Collection<Participation> findParticipationsByEventId(final int eventId) {
		return this.eventRepository.findParticipationsByEventId(eventId);
	}

	@Transactional
	public void save(final Event event) throws DataAccessException {
		this.eventRepository.save(event);
	}

	@Transactional
	public void saveParticipation(final Participation participation) throws DataAccessException {
		this.participationRepository.save(participation);
	}

	public Collection<Event> findAllPublishedEvents() {
		return this.eventRepository.findAllPublishedEvents();
	}

	public void delete(final Event event) {
		List<Participation> participations = new ArrayList<>(this.findParticipationsByEventId(event.getId()));
		participations.stream().forEach(x -> this.deleteParticipation(x));
		this.eventRepository.delete(event);
	}

	public void deleteParticipation(final Participation participation) {
		this.participationRepository.delete(participation);
	}

}
