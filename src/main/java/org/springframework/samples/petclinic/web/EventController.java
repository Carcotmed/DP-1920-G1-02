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

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.service.EventService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.ProviderService;
import org.springframework.samples.petclinic.service.exceptions.NotAuthorizedAccessException;
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

	private final EventService		eventService;
	private final OwnerService		ownerService;
	private final ProviderService	providerService;

	private final static String VET_ROL = "veterinarian";
	private final static String EVENTS = "events";
	private final static String EVENT_TAG = "event";
	private final static String ERROR = "error";
	private final static String REGISTERED = "registered";
	private final static String CREATE_UPDATE_EVENT = "events/createOrUpdateEventForm";


	@Autowired
	public EventController(final ProviderService providerService, final EventService eventService, final OwnerService ownerService) {
		this.eventService = eventService;
		this.ownerService = ownerService;
		this.providerService = providerService;
	}

	@GetMapping()
	public String showEvents(final ModelMap model) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user.getAuthorities().contains(new SimpleGrantedAuthority(VET_ROL))) {
				model.put(EVENTS, this.eventService.findAllEvents());
			} else {
				model.put(EVENTS, this.eventService.findAllPublishedEvents());
			}
		} catch (Exception e) {
			model.put(EVENTS, this.eventService.findAllPublishedEvents());
		}
		return "events/eventsList";
	}

	@GetMapping("/{eventId}")
	public String showEvent(@PathVariable("eventId") final int eventId, final ModelMap model) {
		Event event = this.eventService.findEventById(eventId);
		if (event != null) {
			if (event.getCapacity() <= this.eventService.findParticipationsByEventId(eventId).size()) {
				model.put(REGISTERED, true);
			} else {
				try {
					User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					Owner owner = this.ownerService.findOwnerByUsername(user.getUsername());
					Boolean registered = this.eventService.findParticipationByIds(eventId, owner.getId()) != null;
					model.put(REGISTERED, registered);
				} catch (Exception e) {
					model.put(REGISTERED, true);
				}
			}
			model.put(EVENT_TAG, event);
			model.put("reserved", this.eventService.findParticipationsByEventId(eventId).size());
			model.put("hasSponsor", event.getSponsor() != null);
			return "events/eventDetails";
		} else {
			model.put(ERROR, "The event you are trying to see doesn't exist");
			return this.showEvents(model);
		}
	}

	@GetMapping(value = "/new")
	public String initCreationForm(final ModelMap model) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user.getAuthorities().contains(new SimpleGrantedAuthority(VET_ROL))) {
				Event event = new Event();
				event.setPublished(false);
				model.put(EVENT_TAG, event);
				return CREATE_UPDATE_EVENT;
			} else {
				throw new NotAuthorizedAccessException();
			}
		} catch (Exception e) {
			model.put(ERROR, "You can't create an event if you are not a veterinarian");
			return this.showEvents(model);
		}
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid final Event event, final BindingResult result, final ModelMap model) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user.getAuthorities().contains(new SimpleGrantedAuthority(VET_ROL))) {
				if (result.hasErrors()) {
					model.put(EVENT_TAG, event);
					return CREATE_UPDATE_EVENT;
				} else {
					if (event.getCapacity() == null) {
						event.setCapacity(0);
					}
					if (event.getDate() == null) {
						event.setDate(LocalDate.now().plusDays(1));
					}
					event.setPublished(false);
					this.eventService.save(event);
					return "redirect:/events";
				}
			} else {
				throw new NotAuthorizedAccessException();
			}
		} catch (Exception e) {
			model.put(ERROR, "You can't create an event if you are not a veterinarian");
		}
		return this.showEvents(model);
	}

	@GetMapping(value = "/edit/{eventId}")
	public String initUpdateForm(@PathVariable("eventId") final int eventId, final ModelMap model) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user.getAuthorities().contains(new SimpleGrantedAuthority(VET_ROL))) {
				Event event = this.eventService.findEventById(eventId);
				if (Boolean.TRUE.equals(event.getPublished())) {
					model.put(ERROR, "You can't edit an already published event");
					return this.showEvent(eventId, model);
				} else {
					model.put(EVENT_TAG, event);
					return CREATE_UPDATE_EVENT;
				}
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			model.put(ERROR, "You can't update an event if you are not a veterinarian");
		}
		return this.showEvent(eventId, model);
	}

	@PostMapping(value = "/edit/{eventId}")
	public String processUpdateForm(@PathVariable("eventId") final int eventId, @Valid Event event, final BindingResult result, final ModelMap model) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user.getAuthorities().contains(new SimpleGrantedAuthority(VET_ROL))) {
				if (Boolean.FALSE.equals(this.eventService.findEventById(eventId).getPublished())) {
					if (result.hasErrors()) {
						model.put(EVENT_TAG, event);
						return CREATE_UPDATE_EVENT;
					} else {
						event = validateEvent(event, eventId);
						return "redirect:/events/" + eventId;
					}
				} else {
					throw new NotAuthorizedAccessException();
				}
			} else {
				model.put(ERROR, "You can't update a published event");
				return this.showEvent(eventId, model);
			}
		} catch (Exception e) {
			model.put(ERROR, "You can't update an event if you are not a veterinarian");
		}
		return this.showEvent(eventId, model);
	}
	
	private Event validateEvent(Event event, int eventId) {
		if (event.getCapacity() == null) {
			event.setCapacity(0);
		}
		if (event.getDate() == null) {
			event.setDate(LocalDate.now().plusDays(1));
		}
		event.setPublished(false);
		event.setId(eventId);
		this.eventService.save(event);
		return event;
	}

	@GetMapping(value = "/publish/{eventId}")
	public String publishEvent(@PathVariable("eventId") final int eventId, final ModelMap model) {
		Event event = this.eventService.findEventById(eventId);
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user.getAuthorities().contains(new SimpleGrantedAuthority(VET_ROL))) {
				if (!event.getDescription().equals("") && !event.getPlace().equals("") && event.getDescription() != null && event.getPlace() != null && !event.getPlace().isEmpty() && !event.getDescription().isEmpty()) {
					event.setPublished(true);
					this.eventService.save(event);
				} else {
					throw new NullPointerException();
				}
			} else {
				throw new IllegalAccessException();
			}
		} catch (NullPointerException e) {
			model.addAttribute(ERROR, "Every field must be completed to publish the event");
		} catch (Exception e) {
			model.put(ERROR, "You can't publish an event if you are not a veterinarian");
		}
		return this.showEvent(eventId, model);
	}

	@GetMapping(value = "/newSponsor/{eventId}")
	public String initSponsorEvent(@PathVariable("eventId") final int eventId, final ModelMap model) {
		Event event = this.eventService.findEventById(eventId);
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user.getAuthorities().contains(new SimpleGrantedAuthority("admin")) && Boolean.TRUE.equals(event.getPublished())) {
				if (event.getSponsor() != null) {
					model.put(ERROR, "This event already has an sponsor");
					return this.showEvent(eventId, model);
				}
				model.put("sponsors", this.providerService.findProviders());
				model.put(EVENT_TAG, event);
				return "events/sponsor";
			} else {
				throw new IllegalAccessException();
			}
		} catch (Exception e) {
			model.put(ERROR, "You can't select an sponsor if you are not an admin or the event is not published");
		}
		return this.showEvent(eventId, model);
	}

	@PostMapping(value = "/newSponsor/{eventId}")
	public String processSponsorEvent(@PathVariable("eventId") final int eventId, final Event event, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			model.put(ERROR, "You can't leave this field empty");
			model.put("sponsors", this.providerService.findProviders());
			model.put(EVENT_TAG, event);
			return "events/sponsor";
		} else {
			try {
				Event event2 = this.eventService.findEventById(eventId);
				User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (user.getAuthorities().contains(new SimpleGrantedAuthority("admin")) && Boolean.TRUE.equals(event2.getPublished())) {
					if (this.eventService.findEventById(eventId).getSponsor() != null) {
						model.put(ERROR, "This event already has an sponsor");
						return this.showEvent(eventId, model);
					}
					event2.setSponsor(event.getSponsor());
					this.eventService.save(event2);
					return this.showEvents(model);
				} else {
					throw new IllegalAccessException();
				}
			} catch (Exception e) {
				model.put(ERROR, "You can't select an sponsor if you are not an admin or the event is not published");
			}
			return this.showEvent(eventId, model);
		}
	}

	@GetMapping(value = "/delete/{eventId}")
	public String deleteEvent(@PathVariable("eventId") final int eventId, final ModelMap model) {
		Event event = this.eventService.findEventById(eventId);
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user.getAuthorities().contains(new SimpleGrantedAuthority(VET_ROL))) {
				if (Boolean.TRUE.equals(event.getPublished())) {
					model.put(ERROR, "You can't delete an event already published");
					return this.showEvent(eventId, model);
				} else {
					this.eventService.delete(event);
					return this.showEvents(model);
				}
			} else {
				throw new NotAuthorizedAccessException();
			}
		} catch (Exception e) {
			model.put(ERROR, "You can't delete an event unless you are veterinarian");
		}
		return this.showEvent(eventId, model);
	}

	@GetMapping(value = "/newParticipation/{eventId}")
	public String initCreationParticipationForm(@PathVariable("eventId") final int eventId, final ModelMap model) {
		Event event = this.eventService.findEventById(eventId);
		Boolean registered = false;
		if (event.getCapacity() <= this.eventService.findParticipationsByEventId(eventId).size()) {
			model.put(ERROR, "This event is already full");
			return this.showEvent(eventId, model);
		} else {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Owner owner = this.ownerService.findOwnerByUsername(user.getUsername());
			registered = this.eventService.findParticipationByIds(eventId, owner.getId()) != null;
			model.put("petsOwned", owner.getPets());
			if (Boolean.TRUE.equals(registered)) {
				model.put(ERROR, "You are already registered in this event");
				return this.showEvent(eventId, model);
			} else {
				if (Boolean.TRUE.equals(event.getPublished())) {
					Participation participation = new Participation();
					model.put("participation", participation);
					return "events/createOrUpdateParticipationForm";
				} else {
					model.put(ERROR, "You cant participate in this event until it is published");
					return this.showEvent(eventId, model);
				}
			}
		}
	}

	@PostMapping(value = "/newParticipation/{eventId}")
	public String processCreationParticipationForm(@PathVariable("eventId") final int eventId, final Participation participation, final BindingResult result, final ModelMap model) {
		Owner owner;
		if (result.hasErrors()) {
			model.put("participation", participation);
			return "events/createOrUpdateParticipationForm";
		} else {
			Event event = this.eventService.findEventById(eventId);
			try {
				User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				owner = this.ownerService.findOwnerByUsername(user.getUsername());
			} catch (Exception e) {
				owner = new Owner();
				owner.setAddress("dgsggfdg");
				owner.setCity("city");
				owner.setFirstName("first");
				owner.setLastName("last");
				owner.setTelephone("758697048");
			}
			participation.setEvent(event);
			participation.setOwner(owner);
			this.eventService.saveParticipation(participation);
			return "redirect:/events/" + eventId;
		}
	}

}
