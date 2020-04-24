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
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test of the Service and the Repository layer.
 * <p>
 * ClinicServiceSpringDataJpaTests subclasses benefit from the following services provided
 * by the Spring TestContext Framework:
 * </p>
 * <ul>
 * <li><strong>Spring IoC container caching</strong> which spares us unnecessary set up
 * time between test execution.</li>
 * <li><strong>Dependency Injection</strong> of test fixture instances, meaning that we
 * don't need to perform application context lookups. See the use of
 * {@link Autowired @Autowired} on the <code>{@link
 * ClinicServiceTests#clinicService clinicService}</code> instance variable, which uses
 * autowiring <em>by type</em>.
 * <li><strong>Transaction management</strong>, meaning each test method is executed in
 * its own transaction, which is automatically rolled back by default. Thus, even if tests
 * insert or otherwise change database state, there is no need for a teardown or cleanup
 * script.
 * <li>An {@link org.springframework.context.ApplicationContext ApplicationContext} is
 * also inherited and can be used for explicit bean lookup if necessary.</li>
 * </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Dave Syer
 */

@SuppressWarnings("unused")
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class AdoptionServiceTests {

	@Autowired
	protected AdoptionService	adoptionService;

	@Autowired
	protected OwnerService		ownerService;

	@Autowired
	protected PetService		petService;

	private static int			ida1;
	private static int			ida2;
	private static int			ido1;
	private static int			ido2;
	private static int			idp1;
	private static int			idp2;


	@Transactional
	@BeforeEach
	void init() throws DataAccessException, DuplicatedPetNameException {
		Owner owner1 = new Owner();
		owner1.setAddress("Address1");
		owner1.setCity("city1");
		owner1.setFirstName("first1");
		owner1.setLastName("last1");
		owner1.setTelephone("857687980");
		this.ownerService.saveOwner(owner1);
		AdoptionServiceTests.ido1 = this.ownerService.findOwnerByFirstName("first1").getId();
		Owner owner2 = new Owner();
		owner2.setAddress("Address2");
		owner2.setCity("city2");
		owner2.setFirstName("first2");
		owner2.setLastName("last2");
		owner2.setTelephone("857687980");
		this.ownerService.saveOwner(owner2);
		AdoptionServiceTests.ido2 = this.ownerService.findOwnerByFirstName("first2").getId();

		Pet pet1 = new Pet();
		pet1.setBirthDate(LocalDate.now().minusDays(10));
		pet1.setName("nameee1");
		pet1.setOwner(this.ownerService.findOwnerByFirstName("first1"));
		pet1.setType(this.petService.findPetTypes().iterator().next());
		AdoptionServiceTests.idp1 = this.petService.savePet(pet1).getId();
		Pet pet2 = new Pet();
		pet2.setBirthDate(LocalDate.now().minusDays(10));
		pet2.setName("nameee2");
		pet2.setOwner(this.ownerService.findOwnerByFirstName("first2"));
		pet2.setType(this.petService.findPetTypes().iterator().next());
		AdoptionServiceTests.idp2 = this.petService.savePet(pet1).getId();

		Adoption adoption1 = new Adoption();
		adoption1.setDate(LocalDate.now().plusDays(3));
		adoption1.setOwner(this.ownerService.findOwnerById(AdoptionServiceTests.ido1));
		adoption1.setPet(this.petService.findPetById(AdoptionServiceTests.idp1));
		AdoptionServiceTests.ida1 = this.adoptionService.save(adoption1).getId();
		Adoption adoption2 = new Adoption();
		adoption2.setDate(LocalDate.now().plusDays(3));
		adoption2.setEnd(LocalDate.now().plusDays(30));
		adoption2.setOwner(this.ownerService.findOwnerById(AdoptionServiceTests.ido2));
		adoption2.setPet(this.petService.findPetById(AdoptionServiceTests.idp2));
		AdoptionServiceTests.ida2 = this.adoptionService.save(adoption2).getId();
	}
	@Transactional
	@AfterEach
	void tearDown() {
		this.adoptionService.delete(this.adoptionService.findById(AdoptionServiceTests.ida1));
		this.adoptionService.delete(this.adoptionService.findById(AdoptionServiceTests.ida2));
		this.petService.delete(this.petService.findPetById(AdoptionServiceTests.idp1));
		this.petService.delete(this.petService.findPetById(AdoptionServiceTests.idp2));
		this.ownerService.delete(this.ownerService.findOwnerById(AdoptionServiceTests.ido1));
		this.ownerService.delete(this.ownerService.findOwnerById(AdoptionServiceTests.ido2));
	}

	@Test
	void shouldFindAdoptionWithCorrectId() {
		Adoption adoption1 = this.adoptionService.findById(AdoptionServiceTests.ida1);
		Assertions.assertThat(adoption1.getDate()).isEqualTo(LocalDate.now().plusDays(3));
		Assertions.assertThat(adoption1.getPet()).isEqualTo(this.petService.findPetById(AdoptionServiceTests.idp1));
	}

	@Test
	void shouldNotFindAdoptionWithIncorrectId() {
		Assertions.assertThat(this.adoptionService.findById(7584589)).isEqualTo(null);
	}

	@Test
	void shouldSaveAdoption() throws DataAccessException, DuplicatedPetNameException {
		Adoption adoption = new Adoption();
		adoption.setDate(LocalDate.now().plusDays(2));
		adoption.setOwner(this.ownerService.findOwnerById(AdoptionServiceTests.ido1));
		adoption.setPet(this.petService.findPetById(AdoptionServiceTests.idp2));
		Adoption adoption2 = this.adoptionService.save(adoption);
		Assertions.assertThat(adoption).isEqualTo(adoption2);
		this.adoptionService.delete(adoption2);
	}

	@Test
	void shouldFindAdoptionsByOwner() {
		Collection<Adoption> adoptions = this.adoptionService.findAdoptionsByOwner(AdoptionServiceTests.ido1);
		Adoption adoption1 = EntityUtils.getById(adoptions, Adoption.class, AdoptionServiceTests.ida1);
		Assertions.assertThat(adoption1.getDate()).isEqualTo(LocalDate.now().plusDays(3));
		Assertions.assertThat(adoption1.getPet()).isEqualTo(this.petService.findPetById(AdoptionServiceTests.idp1));
	}

	@Test
	void shouldFindAdoptionsByPet() {
		Collection<Adoption> adoptions = this.adoptionService.findAdoptionsByPet(AdoptionServiceTests.idp1);
		Adoption adoption1 = EntityUtils.getById(adoptions, Adoption.class, AdoptionServiceTests.ida1);
		Assertions.assertThat(adoption1.getDate()).isEqualTo(LocalDate.now().plusDays(3));
		Assertions.assertThat(adoption1.getPet()).isEqualTo(this.petService.findPetById(AdoptionServiceTests.idp1));
	}

	@Test
	void shouldFindAllAdoptions() {
		Collection<Adoption> adoptions = this.adoptionService.findAllAdoptions();
		Adoption adoption1 = EntityUtils.getById(adoptions, Adoption.class, AdoptionServiceTests.ida1);
		Assertions.assertThat(adoption1.getDate()).isEqualTo(LocalDate.now().plusDays(3));
		Assertions.assertThat(adoption1.getPet()).isEqualTo(this.petService.findPetById(AdoptionServiceTests.idp1));
		Adoption adoption2 = EntityUtils.getById(adoptions, Adoption.class, AdoptionServiceTests.ida2);
		Assertions.assertThat(adoption1.getDate()).isEqualTo(LocalDate.now().plusDays(3));
		Assertions.assertThat(adoption1.getPet()).isEqualTo(this.petService.findPetById(AdoptionServiceTests.idp2));
	}
	@Test
	void shouldDeleteAdoption() {
		Adoption adoption = new Adoption();
		adoption.setDate(LocalDate.now().plusDays(2));
		adoption.setOwner(this.ownerService.findOwnerById(AdoptionServiceTests.ido1));
		adoption.setPet(this.petService.findPetById(AdoptionServiceTests.idp2));
		adoption.setId(18);
		Adoption adoption2 = this.adoptionService.save(adoption);
		Assertions.assertThat(this.adoptionService.findById(adoption2.getId())).isEqualTo(adoption2);
		this.adoptionService.delete(adoption2);
		Assertions.assertThat(this.adoptionService.findById(adoption2.getId())).isNull();
	}
}
