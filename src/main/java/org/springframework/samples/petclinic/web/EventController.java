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

package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.service.EventService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/events")
public class EventController {

	private final EventService	eventService;
	private final OwnerService	ownerService;


	@Autowired
	public EventController(final EventService eventService, final OwnerService ownerService) {
		this.eventService = eventService;
		this.ownerService = ownerService;
	}

	@GetMapping()
	public String showEvents(final ModelMap model) {
		GrantedAuthority authority = new SimpleGrantedAuthority("veterinarian");
		if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(authority)) {
			model.put("events", this.eventService.findAllEvents());
		} else {
			model.put("events", this.eventService.findAllPublishedEvents());
		}
		return "events/eventsList";
	}

	@GetMapping("/{eventId}")
	public String showEvent(@PathVariable("eventId") final int eventId, final ModelMap model) {
		Event event = this.eventService.findEventById(eventId);
		if (event.getCapacity() <= this.eventService.findParticipationsByEventId(eventId).size()) {
			model.put("registered", true);
		} else {
			try {
				User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				Owner owner = this.ownerService.findOwnerByUsername(user.getUsername());
				Boolean registered = this.eventService.findParticipationByIds(eventId, owner.getId()) != null;
				model.put("registered", registered);
			} catch (Exception e) {
				model.put("registered", true);
			}
		}
		model.put("event", event);
		model.put("reserved", this.eventService.findParticipationsByEventId(eventId).size());
		return "events/eventDetails";
	}

	@GetMapping(value = "/new")
	public String initCreationForm(final Map<String, Object> model) {
		Event event = new Event();
		event.setPublished(false);
		model.put("event", event);
		return "events/createOrUpdateEventForm";
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid final Event event, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("event", event);
			return "events/createOrUpdateEventForm";
		} else {
			event.setPublished(false);
			this.eventService.save(event);
			return "redirect:/events";
		}
	}

	@GetMapping(value = "/edit/{eventId}")
	public String initUpdateForm(@PathVariable("eventId") final int eventId, final ModelMap model) {
		Event event = this.eventService.findEventById(eventId);
		if (event.getPublished()) {
			model.put("error", "No se puede editar un evento ya publicado");
			return this.showEvent(eventId, model);
		} else {
			model.put("event", event);
			return "events/createOrUpdateEventForm";
		}
	}

	@PostMapping(value = "/edit/{eventId}")
	public String processUpdateForm(@PathVariable("eventId") final int eventId, @Valid final Event event, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("event", event);
			return "events/createOrUpdateEventForm";
		} else {
			event.setPublished(false);
			event.setId(eventId);
			this.eventService.save(event);
			return "redirect:/events/" + eventId;
		}
	}

	@GetMapping(value = "/publish/{eventId}")
	public String publishEvent(@PathVariable("eventId") final int eventId, final ModelMap model) {
		Event event = this.eventService.findEventById(eventId);
		try {
			Boolean b = event.getCapacity() != null && event.getDate() != null && event.getDescription() != null && event.getPlace() != null;
			if (b) {
				event.setPublished(true);
				this.eventService.save(event);
			} else {
				model.addAttribute("publishError", "Every field must be completed to publish the event");
			}
		} catch (NullPointerException e) {
			model.addAttribute("error", "Every field must be completed to publish the event");
		}
		return this.showEvent(eventId, model);
	}

	@GetMapping(value = "/delete/{eventId}")
	public String deleteEvent(@PathVariable("eventId") final int eventId, final ModelMap model) {
		Event event = this.eventService.findEventById(eventId);
		if (event.getPublished()) {
			model.put("error", "No se puede eliminar un evento ya publicado");
			return this.showEvent(eventId, model);
		} else {
			this.eventService.delete(event);
			return this.showEvents(model);
		}
	}

	@GetMapping(value = "/newParticipation/{eventId}")
	public String initCreationParticipationForm(@PathVariable("eventId") final int eventId, final ModelMap model) {
		Event event = this.eventService.findEventById(eventId);
		if (event.getCapacity() <= this.eventService.findParticipationsByEventId(eventId).size()) {
			model.put("error", "This event is already full");
			return this.showEvent(eventId, model);
		} else {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Owner owner = this.ownerService.findOwnerByUsername(user.getUsername());
			Boolean registered = this.eventService.findParticipationByIds(eventId, owner.getId()) != null;
			if (registered) {
				model.put("error", "You are already registered in this event");
				return this.showEvent(eventId, model);
			} else {
				if (event.getPublished()) {
					Participation participation = new Participation();
					model.put("participation", participation);
					model.put("petsOwned", owner.getPets());
					return "events/createOrUpdateParticipationForm";
				} else {
					model.put("error", "You cant participate in this event until it is published");
					return this.showEvent(eventId, model);
				}
			}
		}
	}

	@PostMapping(value = "/newParticipation/{eventId}")
	public String processCreationParticipationForm(@PathVariable("eventId") final int eventId, final Participation participation, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("participation", participation);
			return "events/createOrUpdateParticipationForm";
		} else {
			Event event = this.eventService.findEventById(eventId);
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Owner owner = this.ownerService.findOwnerByUsername(user.getUsername());
			participation.setEvent(event);
			participation.setOwner(owner);
			this.eventService.saveParticipation(participation);
			return "redirect:/events/" + eventId;
		}
	}

}
