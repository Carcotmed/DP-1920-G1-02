
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.service.EventService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.ProviderService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test class for {@link OwnerController}
 *
 * @author Colin But
 */

@WebMvcTest(controllers = EventController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class EventControllerTests {

	private static final int TEST_EVENT_ID1 = 1;

	private static final int TEST_EVENT_ID2 = 2;

	private static final int TEST_EVENT_ID3 = 3;

	private static final int TEST_EVENT_ID4 = 4;

	@MockBean
	private OwnerService ownerService;

	@MockBean
	private EventService eventService;

	@MockBean
	private UserService userService;

	@MockBean
	private ProviderService providerService;

	@Autowired
	private MockMvc mockMvc;

	private Event event1;

	private Event event2;

	private Event event3;

	private Event event4;

	@BeforeEach
	void setup() {

		this.event1 = new Event();
		this.event1.setId(EventControllerTests.TEST_EVENT_ID1);
		this.event1.setCapacity(4);
		this.event1.setDate(LocalDate.parse("2030-01-01"));
		this.event1.setDescription("Descr");
		this.event1.setPlace("Place");
		this.event1.setPublished(true);
		BDDMockito.given(this.eventService.findEventById(EventControllerTests.TEST_EVENT_ID1)).willReturn(this.event1);

		this.event2 = new Event();
		this.event2.setId(EventControllerTests.TEST_EVENT_ID2);
		this.event2.setCapacity(4);
		this.event2.setDate(LocalDate.now().plusMonths(4));
		this.event2.setDescription("Descr");
		this.event2.setPlace("Place");
		this.event2.setPublished(false);
		BDDMockito.given(this.eventService.findEventById(EventControllerTests.TEST_EVENT_ID2)).willReturn(this.event2);

		this.event3 = new Event();
		this.event3.setId(EventControllerTests.TEST_EVENT_ID3);
		this.event3.setCapacity(4);
		this.event3.setDate(LocalDate.now().plusMonths(4));
		this.event3.setPublished(false);
		BDDMockito.given(this.eventService.findEventById(EventControllerTests.TEST_EVENT_ID3)).willReturn(this.event3);

		Provider p1 = new Provider();
		p1.setAddress("address");
		p1.setEmail("email@jdbhjkf.dhjkbdj");
		p1.setName("name");
		p1.setPhone("748576879");
		this.event4 = new Event();
		this.event4.setId(EventControllerTests.TEST_EVENT_ID4);
		this.event4.setCapacity(4);
		this.event4.setDate(LocalDate.parse("2030-01-01"));
		this.event4.setDescription("Descr");
		this.event4.setPlace("Place");
		this.event4.setPublished(true);
		this.event4.setSponsor(p1);
		BDDMockito.given(this.eventService.findEventById(EventControllerTests.TEST_EVENT_ID4)).willReturn(this.event4);

		List<Event> list1 = new ArrayList<>();
		list1.add(this.event1);
		list1.add(this.event2);
		list1.add(this.event3);
		BDDMockito.given(this.eventService.findAllEvents()).willReturn(list1);
		List<Event> list2 = new ArrayList<>();
		list2.add(this.event1);
		list2.add(this.event4);
		BDDMockito.given(this.eventService.findAllPublishedEvents()).willReturn(list2);
		Owner owner1 = new Owner();
		owner1.setId(1);
		owner1.setAddress("110 W. Liberty St.");
		owner1.setCity("Madison");
		owner1.setFirstName("George");
		owner1.setLastName("Franklin");
		owner1.setTelephone("6085551023");
		BDDMockito.given(this.ownerService.findOwnerByUsername("user")).willReturn(owner1);
		BDDMockito.given(this.eventService.findParticipationByIds(2, 1)).willReturn(null);

	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testInitCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/new")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("event"))
				.andExpect(MockMvcResultMatchers.view().name("events/createOrUpdateEventForm"));
	}

	@WithMockUser(value = "spring", authorities = "owner")
	@Test
	void testInitCreationFormAsOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/new")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("event"))
				.andExpect(MockMvcResultMatchers.view().name("events/eventsList"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian", username = "user", password = "pass")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/events/new").requestAttr("date", LocalDate.parse("2030-02-02"))
						.param("description", "desc").with(SecurityMockMvcRequestPostProcessors.csrf())
						.requestAttr("capacity", 6).param("place", "London"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/events"));
	}

	@WithMockUser(value = "spring", authorities = "owner", username = "user", password = "pass")
	@Test
	void testProcessCreationFormAsOwner() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/events/new").requestAttr("date", LocalDate.parse("2030-02-02"))
						.param("description", "desc").with(SecurityMockMvcRequestPostProcessors.csrf())
						.requestAttr("capacity", 6).param("place", "London"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("events/eventsList"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testProcessCreationEmptyFormSuccess() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/events/new").with(SecurityMockMvcRequestPostProcessors.csrf()))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/events"));
	}

	@WithMockUser(value = "spring", authorities = "owner")
	@Test
	void testProcessCreationEmptyFormAsOwner() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/events/new").with(SecurityMockMvcRequestPostProcessors.csrf()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("events/eventsList"));
	}

	@WithMockUser(authorities = "owner", username = "user", password = "pass")
	@Test
	void testShowEventsAsOwner() throws Exception {
		List<Event> expected = new ArrayList<>();
		expected.add(this.event1);
		expected.add(this.event4);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("events", expected))
				.andExpect(MockMvcResultMatchers.view().name("events/eventsList"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian", username = "user", password = "pass")
	@Test
	void testShowEventsAsVet() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("events",
						Matchers.contains(this.event1, this.event2, this.event3)))
				.andExpect(MockMvcResultMatchers.view().name("events/eventsList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowEvent() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/{eventId}", EventControllerTests.TEST_EVENT_ID1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("event", this.event1))
				.andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowIncorrectEvent() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/{eventId}", 5678979))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("error"))
				.andExpect(MockMvcResultMatchers.view().name("events/eventsList"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testInitUpdateFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/edit/{eventId}", EventControllerTests.TEST_EVENT_ID2))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("event"))
				.andExpect(MockMvcResultMatchers.view().name("events/createOrUpdateEventForm"));
	}

	@WithMockUser(value = "spring", authorities = "owner")
	@Test
	void testInitUpdateFormAsOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/edit/{eventId}", EventControllerTests.TEST_EVENT_ID2))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("error"))
				.andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testInitUpdateFormOnPublishedEvent() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/edit/{eventId}", EventControllerTests.TEST_EVENT_ID1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("error"))
				.andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/events/edit/{eventId}", EventControllerTests.TEST_EVENT_ID2)
				.requestAttr("date", LocalDate.parse("2030-02-02")).param("description", "desc")
				.with(SecurityMockMvcRequestPostProcessors.csrf()).requestAttr("capacity", 6).param("place", "London"))
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(
						MockMvcResultMatchers.view().name("redirect:/events/" + EventControllerTests.TEST_EVENT_ID2));
	}

	@WithMockUser(value = "spring", authorities = "owner")
	@Test
	void testProcessUpdateFormAsOwner() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/events/edit/{eventId}", EventControllerTests.TEST_EVENT_ID2)
						.requestAttr("date", LocalDate.parse("2030-02-02")).param("description", "desc")
						.with(SecurityMockMvcRequestPostProcessors.csrf()).requestAttr("capacity", 6)
						.param("place", "London"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("error"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testProcessUpdateFormOnPublishedEvent() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/events/edit/{eventId}", EventControllerTests.TEST_EVENT_ID1)
						.requestAttr("date", LocalDate.parse("2030-02-02")).param("description", "desc")
						.with(SecurityMockMvcRequestPostProcessors.csrf()).requestAttr("capacity", 6)
						.param("place", "London"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("error"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testPublishEvent() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/events/publish/{eventId}", EventControllerTests.TEST_EVENT_ID2))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "owner")
	@Test
	void testPublishEventAsOwner() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/events/publish/{eventId}", EventControllerTests.TEST_EVENT_ID2))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("error"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("error"))
				.andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testPublishIncompletedEvent() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/events/publish/{eventId}", EventControllerTests.TEST_EVENT_ID3))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("error"))
				.andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testDeleteEvent() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/events/delete/{eventId}", EventControllerTests.TEST_EVENT_ID2))
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("events/eventsList"));
	}

	@WithMockUser(value = "spring", authorities = "owner")
	@Test
	void testDeleteEventAsOwner() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/events/delete/{eventId}", EventControllerTests.TEST_EVENT_ID2))
				.andExpect(MockMvcResultMatchers.model().attributeExists("error"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "owner", username = "user")
	@Test
	void testInitCreationParticipationFormSuccess() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/events/newParticipation/{eventId}",
						EventControllerTests.TEST_EVENT_ID1))
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("events/createOrUpdateParticipationForm"));
	}

	@WithMockUser(value = "spring", authorities = "owner", username = "user")
	@Test
	void testInitCreationParticipationFormOnUnplublishedEvent() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/events/newParticipation/{eventId}",
						EventControllerTests.TEST_EVENT_ID2))
				.andExpect(MockMvcResultMatchers.model().attributeExists("error"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "owner", username = "user")
	@Test
	void testProcessCreationParticipationFormSuccess() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/events/newParticipation/{eventId}", EventControllerTests.TEST_EVENT_ID1)
						.requestAttr("pets", new ArrayList<>()).with(SecurityMockMvcRequestPostProcessors.csrf()))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error")).andExpect(
						MockMvcResultMatchers.view().name("redirect:/events/" + EventControllerTests.TEST_EVENT_ID1));
	}

	@WithMockUser(value = "spring", authorities = "owner", username = "user")
	@Test
	void testProcessCreationParticipationFormOnUnpublishedEvent() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/events/newParticipation/{eventId}", EventControllerTests.TEST_EVENT_ID2)
						.requestAttr("pets", new ArrayList<>()).with(SecurityMockMvcRequestPostProcessors.csrf()))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error")).andExpect(
						MockMvcResultMatchers.view().name("redirect:/events/" + EventControllerTests.TEST_EVENT_ID2));
	}

	@WithMockUser(value = "spring", authorities = "admin")
	@Test
	void testInitSelectSponsorFormSuccess() throws Exception {
		this.mockMvc
				.perform(
						MockMvcRequestBuilders.get("/events/newSponsor/{eventId}", EventControllerTests.TEST_EVENT_ID1))
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("sponsors"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("events/sponsor"));
	}

	@WithMockUser(value = "spring", authorities = "admin")
	@Test
	void testInitSelectSponsorAlreadySelected() throws Exception {
		this.mockMvc
				.perform(
						MockMvcRequestBuilders.get("/events/newSponsor/{eventId}", EventControllerTests.TEST_EVENT_ID4))
				.andExpect(MockMvcResultMatchers.model().attributeExists("error"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testInitSelectSponsorAsVet() throws Exception {
		this.mockMvc
				.perform(
						MockMvcRequestBuilders.get("/events/newSponsor/{eventId}", EventControllerTests.TEST_EVENT_ID1))
				.andExpect(MockMvcResultMatchers.model().attributeExists("error"))
				.andExpect(MockMvcResultMatchers.status().isOk())
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
		this.mockMvc
				.perform(
						MockMvcRequestBuilders.post("/events/newSponsor/{eventId}", EventControllerTests.TEST_EVENT_ID1)
								.requestAttr("sponsor", p1).with(SecurityMockMvcRequestPostProcessors.csrf()))
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("events/eventsList"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian", username = "owner1")
	@Test
	void testProcessSelectSponsorAsVet() throws Exception {
		Provider p1 = new Provider();
		p1.setAddress("address");
		p1.setEmail("email@djbvj.hgdjkfh");
		p1.setName("name");
		p1.setPhone("958675847");
		this.event1.setSponsor(p1);
		this.mockMvc
				.perform(
						MockMvcRequestBuilders.post("/events/newSponsor/{eventId}", EventControllerTests.TEST_EVENT_ID1)
								.requestAttr("event", this.event1).requestAttr("sponsor", p1)
								.with(SecurityMockMvcRequestPostProcessors.csrf()))
				.andExpect(MockMvcResultMatchers.model().attributeExists("error"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}

	@WithMockUser(value = "spring", authorities = "admin", username = "owner1")
	@Test
	void testProcessSelectSponsorAlreadySelected() throws Exception {
		Provider p1 = new Provider();
		p1.setAddress("address");
		p1.setEmail("email@djbvj.hgdjkfh");
		p1.setName("name");
		p1.setPhone("958675847");
		this.event1.setSponsor(p1);
		this.mockMvc
				.perform(
						MockMvcRequestBuilders.post("/events/newSponsor/{eventId}", EventControllerTests.TEST_EVENT_ID4)
								.requestAttr("event", this.event1).requestAttr("sponsor", p1)
								.with(SecurityMockMvcRequestPostProcessors.csrf()))
				.andExpect(MockMvcResultMatchers.model().attributeExists("error"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("events/eventDetails"));
	}
}
