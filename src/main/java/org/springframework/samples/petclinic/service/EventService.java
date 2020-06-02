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

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

	@Cacheable(cacheNames = "findAllEvents")
	public Collection<Event> findAllEvents() {
		return this.eventRepository.findAllEvents();
	}

	public Event findEventById(final int eventId) {
		return this.eventRepository.findEventById(eventId);
	}

	public Participation findParticipationByIds(final int eventId, final int ownerId) {
		return this.participationRepository.findParticipationByIds(eventId, ownerId);
	}
	
	public Collection<Participation> findParticipationsByEventIdWithPets(final int eventId) {
		return this.eventRepository.findParticipationsByEventIdWithPets(eventId);
	}
	
	public Collection<Participation> findParticipationsByEventId(final int eventId) {
		return this.eventRepository.findParticipationsByEventId(eventId);
	}

	@Transactional
	@CacheEvict (allEntries = true, cacheNames = {"findAllEvents", "findAllPublishedEvents"})
	public Event save(final Event event) {
		if (Boolean.TRUE.equals(event.getPublished())) {
			if (Boolean.TRUE.equals(event.getCapacity() == null) || Boolean.TRUE.equals(event.getDate() ==null) || Boolean.TRUE.equals(event.getDescription() == null) || Boolean.TRUE.equals(event.getPlace() == null)) {
				throw new InvalidParameterException("Event must not be empty if published");
			}
		}
		if (event.getDate().isBefore(LocalDate.now())) {
			throw new InvalidParameterException("Event must not have a past date");
		}
		return this.eventRepository.save(event);
	}

	@Transactional
	@CacheEvict (allEntries = true, cacheNames = {"findAllEvents", "findAllPublishedEvents"})
	public Participation saveParticipation(final Participation participation) {
		return this.participationRepository.save(participation);
	}

	@Cacheable(cacheNames = "findAllPublishedEvents")
	public Collection<Event> findAllPublishedEvents() {
		return this.eventRepository.findAllPublishedEvents();
	}

	@CacheEvict (allEntries = true, cacheNames = {"findAllEvents", "findAllPublishedEvents"})
	public void delete(final Event event) {
		List<Participation> participations = new ArrayList<>(this.findParticipationsByEventId(event.getId()));
		participations.stream().forEach(this::deleteParticipation);
		this.eventRepository.delete(event);
	}

	@CacheEvict (allEntries = true, cacheNames = {"findAllEvents", "findAllPublishedEvents"})
	public void deleteParticipation(final Participation participation) {
		this.participationRepository.delete(participation);
	}

}
