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
import org.springframework.samples.petclinic.service.EventService;
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

	private final EventService eventService;


	@Autowired
	public EventController(final EventService eventService) {
		this.eventService = eventService;
	}

	@GetMapping()
	public String showEvents(final ModelMap model) {
		model.put("events", this.eventService.findAllEvents());
		return "events/eventsList";
	}

	@GetMapping("/{eventId}")
	public String showEvent(@PathVariable("eventId") final int eventId, final ModelMap model) {
		model.put("event", this.eventService.findEventById(eventId));
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
	public String processCreationForm(@Valid final Event event, final BindingResult result) {
		if (result.hasErrors()) {
			return "events/createOrUpdateEventForm";
		} else {
			event.setPublished(false);
			this.eventService.save(event);
			return "redirect:/events";
		}
	}

	@GetMapping(value = "/edit/{eventId}")
	public String initUpdateForm(@PathVariable("eventId") final int eventId, final Map<String, Object> model) {
		Event event = this.eventService.findEventById(eventId);
		model.put("event", event);
		return "events/createOrUpdateEventForm";
	}

	@PostMapping(value = "/edit/{eventId}")
	public String processUpdateForm(@PathVariable("eventId") final int eventId, @Valid final Event event, final BindingResult result) {
		if (result.hasErrors()) {
			return "events/createOrUpdateEventForm";
		} else {
			event.setPublished(false);
			event.setId(eventId);
			this.eventService.save(event);
			return "redirect:/events/" + eventId;
		}
	}

}
