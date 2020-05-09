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
			if (user.getAuthorities().contains(new SimpleGrantedAuthority("veterinarian"))) {
				model.put("events", this.eventService.findAllEvents());
			} else {
				model.put("events", this.eventService.findAllPublishedEvents());
			}
		} catch (Exception e) {
			model.put("events", this.eventService.findAllPublishedEvents());
		}
		return "events/eventsList";
	}

	@GetMapping("/{eventId}")
	public String showEvent(@PathVariable("eventId") final int eventId, final ModelMap model) {
		Event event = this.eventService.findEventById(eventId);
		if (event != null) {
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
			model.put("hasSponsor", event.getSponsor() != null);
			return "events/eventDetails";
		} else {
			model.put("error", "The event you are trying to see doesn't exist");
			return this.showEvents(model);
		}
	}

	@GetMapping(value = "/new")
	public String initCreationForm(final ModelMap model) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user.getAuthorities().contains(new SimpleGrantedAuthority("veterinarian"))) {
				Event event = new Event();
				event.setPublished(false);
				model.put("event", event);
				return "events/createOrUpdateEventForm";
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			model.put("error", "You can't create an event if you are not a veterinarian");
			return this.showEvents(model);
		}
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid final Event event, final BindingResult result, final ModelMap model) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user.getAuthorities().contains(new SimpleGrantedAuthority("veterinarian"))) {
				if (result.hasErrors()) {
					model.put("event", event);
					return "events/createOrUpdateEventForm";
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
				throw new Exception();
			}
		} catch (Exception e) {
			model.put("error", "You can't create an event if you are not a veterinarian");
		}
		return this.showEvents(model);
	}

	@GetMapping(value = "/edit/{eventId}")
	public String initUpdateForm(@PathVariable("eventId") final int eventId, final ModelMap model) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user.getAuthorities().contains(new SimpleGrantedAuthority("veterinarian"))) {
				Event event = this.eventService.findEventById(eventId);
				if (event.getPublished()) {
					model.put("error", "You can't edit an already published event");
					return this.showEvent(eventId, model);
				} else {
					model.put("event", event);
					return "events/createOrUpdateEventForm";
				}
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			model.put("error", "You can't update an event if you are not a veterinarian");
		}
		return this.showEvent(eventId, model);
	}

	@PostMapping(value = "/edit/{eventId}")
	public String processUpdateForm(@PathVariable("eventId") final int eventId, @Valid final Event event, final BindingResult result, final ModelMap model) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user.getAuthorities().contains(new SimpleGrantedAuthority("veterinarian"))) {
				if (!this.eventService.findEventById(eventId).getPublished()) {
					if (result.hasErrors()) {
						model.put("event", event);
						return "events/createOrUpdateEventForm";
					} else {
						if (event.getCapacity() == null) {
							event.setCapacity(0);
						}
						if (event.getDate() == null) {
							event.setDate(LocalDate.now().plusDays(1));
						}
						event.setPublished(false);
						event.setId(eventId);
						this.eventService.save(event);
						return "redirect:/events/" + eventId;
					}
				} else {
					throw new Exception();
				}
			} else {
				model.put("error", "You can't update a published event");
				return this.showEvent(eventId, model);
			}
		} catch (Exception e) {
			model.put("error", "You can't update an event if you are not a veterinarian");
		}
		return this.showEvent(eventId, model);
	}

	@GetMapping(value = "/publish/{eventId}")
	public String publishEvent(@PathVariable("eventId") final int eventId, final ModelMap model) {
		Event event = this.eventService.findEventById(eventId);
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user.getAuthorities().contains(new SimpleGrantedAuthority("veterinarian"))) {
				if (event.getDescription() != "" && event.getPlace() != "" && event.getDescription() != null && event.getPlace() != null) {
					event.setPublished(true);
					this.eventService.save(event);
				} else {
					throw new NullPointerException();
				}
			} else {
				throw new IllegalAccessException();
			}
		} catch (NullPointerException e) {
			model.addAttribute("error", "Every field must be completed to publish the event");
		} catch (Exception e) {
			model.put("error", "You can't publish an event if you are not a veterinarian");
		}
		return this.showEvent(eventId, model);
	}

	@GetMapping(value = "/newSponsor/{eventId}")
	public String initSponsorEvent(@PathVariable("eventId") final int eventId, final ModelMap model) {
		Event event = this.eventService.findEventById(eventId);
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user.getAuthorities().contains(new SimpleGrantedAuthority("admin")) && event.getPublished() == true) {
				if (event.getSponsor() != null) {
					model.put("error", "This event already has an sponsor");
					return this.showEvent(eventId, model);
				}
				model.put("sponsors", this.providerService.findProviders());
				model.put("event", event);
				return "events/sponsor";
			} else {
				throw new IllegalAccessException();
			}
		} catch (Exception e) {
			model.put("error", "You can't select an sponsor if you are not an admin or the event is not published");
		}
		return this.showEvent(eventId, model);
	}

	@PostMapping(value = "/newSponsor/{eventId}")
	public String processSponsorEvent(@PathVariable("eventId") final int eventId, final Event event, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("error", "You can't leave this field empty");
			model.put("sponsors", this.providerService.findProviders());
			model.put("event", event);
			return "events/sponsor";
		} else {
			try {
				Event event2 = this.eventService.findEventById(eventId);
				User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (user.getAuthorities().contains(new SimpleGrantedAuthority("admin")) && event2.getPublished()) {
					if (this.eventService.findEventById(eventId).getSponsor() != null) {
						model.put("error", "This event already has an sponsor");
						return this.showEvent(eventId, model);
					}
					event2.setSponsor(event.getSponsor());
					this.eventService.save(event2);
					return this.showEvents(model);
				} else {
					throw new IllegalAccessException();
				}
			} catch (Exception e) {
				model.put("error", "You can't select an sponsor if you are not an admin or the event is not published");
			}
			return this.showEvent(eventId, model);
		}
	}

	@GetMapping(value = "/delete/{eventId}")
	public String deleteEvent(@PathVariable("eventId") final int eventId, final ModelMap model) {
		Event event = this.eventService.findEventById(eventId);
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user.getAuthorities().contains(new SimpleGrantedAuthority("veterinarian"))) {
				if (event.getPublished()) {
					model.put("error", "You can't delete an event already published");
					return this.showEvent(eventId, model);
				} else {
					this.eventService.delete(event);
					return this.showEvents(model);
				}
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			model.put("error", "You can't delete an event unless you are veterinarian");
		}
		return this.showEvent(eventId, model);
	}

	@GetMapping(value = "/newParticipation/{eventId}")
	public String initCreationParticipationForm(@PathVariable("eventId") final int eventId, final ModelMap model) {
		Event event = this.eventService.findEventById(eventId);
		Boolean registered = false;
		if (event.getCapacity() <= this.eventService.findParticipationsByEventId(eventId).size()) {
			model.put("error", "This event is already full");
			return this.showEvent(eventId, model);
		} else {
			try {
				User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				Owner owner = this.ownerService.findOwnerByUsername(user.getUsername());
				registered = this.eventService.findParticipationByIds(eventId, owner.getId()) != null;
				model.put("petsOwned", owner.getPets());
			} catch (Exception e) {

			}
			if (registered) {
				model.put("error", "You are already registered in this event");
				return this.showEvent(eventId, model);
			} else {
				if (event.getPublished()) {
					Participation participation = new Participation();
					model.put("participation", participation);
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
