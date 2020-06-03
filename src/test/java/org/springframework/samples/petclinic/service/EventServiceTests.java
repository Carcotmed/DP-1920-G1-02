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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Participation;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test of the Service and the Repository layer.
 * <p>
 * ClinicServiceSpringDataJpaTests subclasses benefit from the following
 * services provided by the Spring TestContext Framework:
 * </p>
 * <ul>
 * <li><strong>Spring IoC container caching</strong> which spares us unnecessary
 * set up time between test execution.</li>
 * <li><strong>Dependency Injection</strong> of test fixture instances, meaning
 * that we don't need to perform application context lookups. See the use of
 * {@link Autowired @Autowired} on the <code>{@link
 * ClinicServiceTests#clinicService clinicService}</code> instance variable,
 * which uses autowiring <em>by type</em>.
 * <li><strong>Transaction management</strong>, meaning each test method is
 * executed in its own transaction, which is automatically rolled back by
 * default. Thus, even if tests insert or otherwise change database state, there
 * is no need for a teardown or cleanup script.
 * <li>An {@link org.springframework.context.ApplicationContext
 * ApplicationContext} is also inherited and can be used for explicit bean
 * lookup if necessary.</li>
 * </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Dave Syer
 */

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class EventServiceTests {

	@Autowired
	protected EventService eventService;

	@Autowired
	protected OwnerService ownerService;

	private static int ide1;
	private static int ide2;
	private static int ide3;
	private static int idp1;
	private static int idp2;

	@Transactional
	@BeforeEach
	void init() {
		Event event1 = new Event();
		event1.setCapacity(1);
		event1.setDate(LocalDate.now().plusDays(2));
		event1.setDescription("Description1");
		event1.setPlace("Place1");
		event1.setPublished(true);
		ide1 = this.eventService.save(event1).getId();

		EventServiceTests.ide1 = event1.getId();
		Event event2 = new Event();
		event2.setCapacity(2);
		event2.setDate(LocalDate.of(2040, 1, 1));
		event2.setDescription("Description2");
		event2.setPlace("Place2");
		event2.setPublished(false);
		ide2 = this.eventService.save(event2).getId();

		Event event3 = new Event();
		event3.setCapacity(3);
		event3.setDate(LocalDate.of(2030, 1, 1));
		event3.setDescription("Description3");
		event3.setPlace("Place3");
		event3.setPublished(true);
		ide3 = this.eventService.save(event3).getId();

		Owner owner1 = this.ownerService.findOwnerById(1);
		Participation part1 = new Participation();
		part1.setEvent(event1);
		part1.setOwner(owner1);
		part1.setPets(owner1.getPets());
		EventServiceTests.idp1 = this.eventService.saveParticipation(part1).getId();

		Participation part2 = new Participation();
		part2.setEvent(event1);
		part2.setOwner(this.ownerService.findOwnerById(2));
		part2.setPets(new ArrayList<>());
		EventServiceTests.idp2 = this.eventService.saveParticipation(part2).getId();
	}

	@Transactional
	@AfterEach
	void tearDown() {
		this.eventService.delete(this.eventService.findEventById(EventServiceTests.ide1));
		this.eventService.delete(this.eventService.findEventById(EventServiceTests.ide2));
		this.eventService.delete(this.eventService.findEventById(EventServiceTests.ide3));
	}

	@Test
	void shouldFindEventWithCorrectId() {
		Event event2 = this.eventService.findEventById(EventServiceTests.ide2);
		assertThat(event2.getCapacity()).isEqualTo(2);
		assertThat(event2.getDescription()).isEqualTo("Description2");
	}

	@Test
	void shouldNotFindEventWithIncorrectId() {
		assertThat(this.eventService.findEventById(7584589)).isEqualTo(null);
	}

	@Test
	void shouldFindParticipationWithCorrectEventId() {
		Collection<Participation> participations = this.eventService
				.findParticipationsByEventId(EventServiceTests.ide1);
		Participation participation1 = EntityUtils.getById(participations, Participation.class, EventServiceTests.idp1);
		assertThat(participation1.getEvent().getId()).isEqualTo(EventServiceTests.ide1);
		Participation participation3 = EntityUtils.getById(participations, Participation.class, EventServiceTests.idp2);
		assertThat(participation3.getEvent().getId()).isEqualTo(EventServiceTests.ide1);
	}

	@Test
	void shouldNotFindParticipationWithIncorrectEventId() {
		assertThat(this.eventService.findParticipationsByEventId(7584589)).isEqualTo(new ArrayList<>());
	}

	@Test
	void shouldFindParticipationWithCorrectEventIdAndOwnerId() {
		Participation participation11 = this.eventService.findParticipationByIds(EventServiceTests.ide1, 1);
		assertThat(participation11.getId()).isEqualTo(EventServiceTests.idp1);
		Participation participation13 = this.eventService.findParticipationByIds(EventServiceTests.ide1, 2);
		assertThat(participation13.getId()).isEqualTo(EventServiceTests.idp2);
	}

	@Test
	void shouldNotFindParticipationWithIncorrectEventIdAndIncorrectOwnerId() {
		assertThat(this.eventService.findParticipationByIds(7584589, 475869)).isEqualTo(null);
	}

	@Test
	void shouldFindAllEvents() {
		Collection<Event> events = this.eventService.findAllEvents();

		Event event1 = EntityUtils.getById(events, Event.class, EventServiceTests.ide1);
		assertThat(event1.getDescription()).isEqualTo("Description1");
		Event event3 = EntityUtils.getById(events, Event.class, EventServiceTests.ide3);
		assertThat(event3.getDescription()).isEqualTo("Description3");
	}

	@Test
	void shouldSaveEvent() {
		Event event = new Event();
		event.setCapacity(15);
		event.setDate(LocalDate.now().plusDays(4));
		event.setDescription("Descripcion");
		event.setPlace("place");
		event.setPublished(false);
		Event event2 = this.eventService.save(event);
		assertThat(event).isEqualTo(event2);
		this.eventService.delete(event2);
	}

	@Test
	void shouldNotSavePublishedEmptyEvent() {
		Event event = new Event();
		event.setPublished(true);
		assertThrows(Exception.class, () -> this.eventService.save(event));
	}

	@Test
	void shouldNotSavePastEvent() {
		Event event = new Event();
		event.setDate(LocalDate.of(2000, 2, 1));
		assertThrows(Exception.class, () -> this.eventService.save(event));
	}

	@Test
	void shouldDeleteEvent() {
		Event event = new Event();
		event.setCapacity(15);
		event.setDate(LocalDate.now().plusMonths(1));
		event.setDescription("Descripcion");
		event.setPlace("place");
		event.setPublished(false);
		Event event2 = this.eventService.save(event);
		assertThat(event).isEqualTo(event2);
		this.eventService.delete(event2);
		assertThat(this.eventService.findEventById(event2.getId())).isEqualTo(null);
	}

	@Test
	void shouldSaveParticipation() {
		Participation part = new Participation();
		part.setEvent(this.eventService.findEventById(EventServiceTests.ide3));
		part.setOwner(this.ownerService.findOwnerById(1));
		part.setPets(new ArrayList<>());
		part = this.eventService.saveParticipation(part);
		assertThat(this.eventService.findParticipationByIds(part.getEvent().getId(), part.getOwner().getId()))
				.isEqualTo(part);
	}

	@Test
	void shouldDeleteParticipation() {
		Participation part = this.eventService.findParticipationByIds(EventServiceTests.ide1, 1);
		this.eventService.deleteParticipation(part);
		assertThat(this.eventService.findParticipationByIds(part.getEvent().getId(), part.getOwner().getId()))
				.isEqualTo(null);
	}

}
