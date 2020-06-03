
package org.springframework.samples.petclinic.e2e;

import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.service.EventService;
import org.springframework.samples.petclinic.web.OwnerController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test class for {@link OwnerController}
 *
 * @author Colin But
 */
@TestPropertySource(locations = "classpath:application-mysql.properties") //Decoment to try the MySql tests

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class EventControllerE2ETests {

	@Autowired
	private MockMvc				mockMvc;
	
	@Autowired
	private EventService 		eventService;

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testInitCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("event"))
			.andExpect(MockMvcResultMatchers.view().name("events/createOrUpdateEventForm"));
	}

	@WithMockUser(value = "spring", authorities = "owner")
	@Test
	void testInitCreationFormAsOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("event"))
			.andExpect(MockMvcResultMatchers.view().name("events/eventsList"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian", username = "user", password = "pass")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/events/new").requestAttr("date", LocalDate.parse("2030-02-02")).param("description", "desc").with(SecurityMockMvcRequestPostProcessors.csrf()).requestAttr("capacity", 6).param("place", "London"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/events"));
	}

	@WithMockUser(value = "spring", authorities = "owner", username = "user", password = "pass")
	@Test
	void testProcessCreationFormAsOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/events/new").requestAttr("date", LocalDate.parse("2030-02-02")).param("description", "desc").with(SecurityMockMvcRequestPostProcessors.csrf()).requestAttr("capacity", 6).param("place", "London"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("events/eventsList"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testProcessCreationEmptyFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/events/new").with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/events"));
	}

	@WithMockUser(value = "spring", authorities = "owner")
	@Test
	void testProcessCreationEmptyFormAsOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/events/new").with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("events/eventsList"));
	}

	@WithMockUser(authorities = "owner", username = "user", password = "pass")
	@Test
	void testShowEventsAsOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attribute("events", this.eventService.findAllPublishedEvents())).andExpect(MockMvcResultMatchers.view().name("events/eventsList"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian", username = "user", password = "pass")
	@Test
	void testShowEventsAsVet() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attribute("events", this.eventService.findAllEvents()))
			.andExpect(MockMvcResultMatchers.view().name("events/eventsList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowEvent() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/{eventId}", 1)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attribute("event", this.eventService.findEventById(1)))
			.andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowIncorrectEvent() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/{eventId}", 5678979)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("error"))
			.andExpect(MockMvcResultMatchers.view().name("events/eventsList"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testInitUpdateFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/edit/{eventId}", 5)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("event"))
			.andExpect(MockMvcResultMatchers.view().name("events/createOrUpdateEventForm"));
	}

	@WithMockUser(value = "spring", authorities = "owner")
	@Test
	void testInitUpdateFormAsOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/edit/{eventId}", 4)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("error"))
			.andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testInitUpdateFormOnPublishedEvent() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/edit/{eventId}", 2)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("error"))
			.andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/events/edit/{eventId}", 5).requestAttr("date", LocalDate.parse("2031-02-02")).param("description", "desc").with(SecurityMockMvcRequestPostProcessors.csrf())
				.requestAttr("capacity", 6).param("place", "London"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/events/" + 5));
	}

	@WithMockUser(value = "spring", authorities = "owner")
	@Test
	void testProcessUpdateFormAsOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/events/edit/{eventId}", 5).requestAttr("date", LocalDate.parse("2030-02-02")).param("description", "desc").with(SecurityMockMvcRequestPostProcessors.csrf())
			.requestAttr("capacity", 6).param("place", "London")).andExpect(MockMvcResultMatchers.model().attributeExists("error")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testProcessUpdateFormOnPublishedEvent() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/events/edit/{eventId}", 2).requestAttr("date", LocalDate.parse("2030-02-02")).param("description", "desc").with(SecurityMockMvcRequestPostProcessors.csrf())
			.requestAttr("capacity", 6).param("place", "London")).andExpect(MockMvcResultMatchers.model().attributeExists("error")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testPublishEvent() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/publish/{eventId}", 5)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "owner")
	@Test
	void testPublishEventAsOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/publish/{eventId}", 5)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("error"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("error")).andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testPublishIncompletedEvent() throws Exception {
		Event event5 = this.eventService.findEventById(5);
		event5.setPlace(null);
		event5.setPublished(false);
		event5.setDate(LocalDate.now().plusDays(10));
		this.eventService.save(event5);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/publish/{eventId}", 5)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("error"))
			.andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testDeleteEvent() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/delete/{eventId}", 4)).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("events/eventsList"));
	}

	@WithMockUser(value = "spring", authorities = "owner")
	@Test
	void testDeleteEventAsOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/delete/{eventId}", 5)).andExpect(MockMvcResultMatchers.model().attributeExists("error")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "owner", username = "owner1")
	@Test
	void testInitCreationParticipationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/newParticipation/{eventId}", 3)).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("events/createOrUpdateParticipationForm"));
	}

	@WithMockUser(value = "spring", authorities = "owner", username = "owner1")
	@Test
	void testInitCreationParticipationFormOnUnplublishedEvent() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/newParticipation/{eventId}", 5)).andExpect(MockMvcResultMatchers.model().attributeExists("error")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "owner", username = "owner1")
	@Test
	void testProcessCreationParticipationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/events/newParticipation/{eventId}", 2).requestAttr("pets", new ArrayList<>()).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error")).andExpect(MockMvcResultMatchers.view().name("redirect:/events/" + 2));
	}

	@WithMockUser(value = "spring", authorities = "owner", username = "owner1")
	@Test
	void testProcessCreationParticipationFormOnUnpublishedEvent() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/events/newParticipation/{eventId}", 5).requestAttr("pets", new ArrayList<>()).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error")).andExpect(MockMvcResultMatchers.view().name("redirect:/events/" + 5));
	}

	@WithMockUser(value = "spring", authorities = "admin")
	@Test
	void testInitSelectSponsorFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/newSponsor/{eventId}", 2)).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("sponsors")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("events/sponsor"));
	}

	@WithMockUser(value = "spring", authorities = "admin")
	@Test
	void testInitSelectSponsorAlreadySelected() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/newSponsor/{eventId}", 1)).andExpect(MockMvcResultMatchers.model().attributeExists("error")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testInitSelectSponsorAsVet() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/newSponsor/{eventId}", 2)).andExpect(MockMvcResultMatchers.model().attributeExists("error")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "admin", username = "owner1")
	@Test
	void testProcessSelectSponsorFormSuccess() throws Exception {
		Provider p1 = new Provider();
		p1.setAddress("address");
		p1.setEmail("email@djbvj.hgdjkfh");
		p1.setName("name");
		p1.setPhone("958675847");
		this.mockMvc.perform(MockMvcRequestBuilders.post("/events/newSponsor/{eventId}", 2).requestAttr("sponsor", p1).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("events/eventsList"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian", username = "owner1")
	@Test
	void testProcessSelectSponsorAsVet() throws Exception {
		Provider p1 = new Provider();
		p1.setAddress("address");
		p1.setEmail("email@djbvj.hgdjkfh");
		p1.setName("name");
		p1.setPhone("958675847");
		Event event1 = this.eventService.findEventById(3);
		event1.setSponsor(p1);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/events/newSponsor/{eventId}", 3).requestAttr("event", event1).requestAttr("sponsor", p1).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.model().attributeExists("error")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "admin", username = "owner1")
	@Test
	void testProcessSelectSponsorAlreadySelected() throws Exception {
		Provider p1 = new Provider();
		p1.setAddress("address");
		p1.setEmail("email@djbvj.hgdjkfh");
		p1.setName("name");
		p1.setPhone("958675847");
		Event event1 = this.eventService.findEventById(1);
		event1.setSponsor(p1);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/events/newSponsor/{eventId}", 1).requestAttr("event", event1).requestAttr("sponsor", p1).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.model().attributeExists("error")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}
}
