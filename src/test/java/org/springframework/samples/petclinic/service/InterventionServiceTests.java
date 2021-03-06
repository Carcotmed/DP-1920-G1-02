package org.springframework.samples.petclinic.service;

import static generatedAssertions.org.assertj.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;

import javax.validation.ConstraintViolationException;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.model.Intervention;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class InterventionServiceTests {

	@Autowired
	protected InterventionService interventionService;

	@Autowired
	protected VetService vetService;

	@Autowired
	protected VisitService visitService;

	@Autowired
	protected ProductService productService;

	Intervention intervention;

	private static Integer TEST_VET_ID = 1;

	@BeforeEach
	void instantiateIntervention() {

		intervention = new Intervention();
		intervention.setName("Castración");
		// intervention.setDescription("Descripción de la intervencion");
		intervention.setVet(vetService.findVetById(TEST_VET_ID));
		Visit visit = visitService.findVisitById(2);
		visit.setIntervention(intervention);
		intervention.setVisit(visit);
		List<Product> productList = new ArrayList<>();
		productList.addAll(productService.findProducts());
		intervention.setRequiredProducts(productList);

	}

	@AfterEach
	void recreateIntervention() {

		interventionService.deleteIntervention(intervention);

		intervention = null;

	}

	// ----------------- Save Intervention ----------------

	@Test
	@Transactional
	void shouldInsertInterventionIntoDBAndGenerateId() {

		interventionService.saveIntervention(intervention);

		Integer id = null;
		id = intervention.getId();
		Integer visitId = null;
		visitId = intervention.getVisit().getId();

		assertThat (intervention).hasNoNullFieldsOrProperties();
		assertThat(intervention).isEqualTo(interventionService.findInterventionById(id));
		assertThat(intervention).isEqualTo(visitService.findVisitById(visitId).getIntervention());
		
		assertThat(intervention).hasName(this.intervention.getName());

	}

	@Test
	void shouldUpdateIntervention() {

		interventionService.saveIntervention(intervention);

		Integer id = null;
		id = intervention.getId();

		Intervention interventionToUpdate = interventionService.findInterventionById(id);
		assertThat(interventionToUpdate).isNotNull();
		interventionToUpdate.setName("Nombre modificado");
		// interventionToUpdate.setDescription("Descripcion modificada");
		List<Product> modifiedList = new ArrayList<>(productService.findProducts());
		modifiedList.remove(0);
		interventionToUpdate.setRequiredProducts(modifiedList);

		interventionService.saveIntervention(interventionToUpdate);

		assertThat (interventionService.findInterventionById(id)).hasName("Nombre modificado");
		// assertThat(interventionService.findInterventionById(id).getDescription()).isEqualTo("Descripcion
		// modificada");

	}

	@ParameterizedTest
	@Transactional
	@ValueSource(strings = { "Intervención 24", "Castración", "aaa", "aaaaaaaaaaaaaaaaaaaaaaaaa",
			"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
	void shouldInsertInterventionIntoDatabaseWithParametizedNames(String name) {

		intervention.setName(name);
		assertThat(intervention).hasName(name);

		interventionService.saveIntervention(intervention);

		Integer id = null;
		id = intervention.getId();
		Integer visitId = null;
		visitId = intervention.getVisit().getId();

		assertThat (intervention).hasNoNullFieldsOrProperties();
		assertThat(intervention).isEqualTo(interventionService.findInterventionById(id));
		assertThat(intervention).isEqualTo(visitService.findVisitById(visitId).getIntervention());

	}

	@Test
	@Transactional
	void shouldNotInsertInterventionIntoDatabaseWithNullProducts() {

		Intervention newIntervention = new Intervention();
		newIntervention.setName("Castración");
		// newIntervention.setDescription("Descripción de la intervencion");
		newIntervention.setVet(vetService.findVetById(1));
		newIntervention.setVisit(visitService.findVisitById(2));
		newIntervention.setRequiredProducts(null);

		// newIntervention.setDescription(null);

		assertThat (newIntervention).hasFieldOrPropertyWithValue("requiredProducts", null);

		assertThrows(ConstraintViolationException.class, () -> interventionService.saveIntervention(newIntervention));

	}

	@Test
	@Transactional
	void shouldInsertInterventionIntoDatabaseWithEmptyProductList() {

		List<Product> list = new ArrayList<>();
		intervention.setRequiredProducts(list);
		//assertThat(intervention.getRequiredProducts()).isEmpty();
		assertThat (intervention).hasFieldOrPropertyWithValue("requiredProducts", new ArrayList <> ());

		interventionService.saveIntervention(intervention);

		Integer id = null;
		id = intervention.getId();
		Integer visitId = null;
		visitId = intervention.getVisit().getId();

		assertThat (intervention).hasNoNullFieldsOrProperties();
		assertThat(intervention).isEqualTo(interventionService.findInterventionById(id));
		assertThat(intervention).isEqualTo(visitService.findVisitById(visitId).getIntervention());

	}

	// ----------------- Find Intervention By Id --------------------

	@Test
	void shouldFindInterventionById() {

		interventionService.saveIntervention(intervention);

		Intervention interventionFound = interventionService.findInterventionById(intervention.getId());

		assertThat(interventionFound).isNotNull();

	}

	@Test
	void shouldNotFindInterventionById() {

		interventionService.saveIntervention(intervention);

		Intervention interventionFound = interventionService.findInterventionById(9999);

		assertThat(interventionFound).isNull();

	}

	// ---------------------- Delete Intervention -------------------------

	@Test
	void shouldDeleteIntervention() {

		Intervention intervention = interventionService.findInterventionById(1);
		assertThat(intervention).isNotNull();
		interventionService.deleteIntervention(intervention);

		assertThat(interventionService.findInterventionById(1)).isNull();
	}

	// ----------------------- Find Interventions of Date ------------------------

	@Test
	void shouldGetInterventionsOfDay() {

		LocalDate date = LocalDate.of(2019, 1, 1);

		org.assertj.core.api.Assertions.assertThat(interventionService.getInterventionsOfDay(date).size()).isEqualTo(3);

	}

	// ---------------------- Get Available Vets
	// ---------------------------------------

	@Test
	void shouldGetAllVetsButOne() {

		LocalDate date = LocalDate.of(2019, 1, 1);

		
		org.assertj.core.api.Assertions.assertThat(vetService.findVets().size()).isEqualTo(6);
		org.assertj.core.api.Assertions.assertThat(interventionService.getAvailableVets(date).size()).isEqualTo(5);

	}

	@Test
	void shouldGetAllVets() {

		LocalDate date = LocalDate.of(2000, 1, 1);

		org.assertj.core.api.Assertions.assertThat(vetService.findVets().size()).isEqualTo(6);
		org.assertj.core.api.Assertions.assertThat(interventionService.getAvailableVets(date).size()).isEqualTo(6);

	}

}
