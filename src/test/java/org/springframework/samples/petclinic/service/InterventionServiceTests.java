package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.validation.ConstraintViolationException;

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
public class InterventionServiceTests {

	@Autowired
	protected InterventionService interventionService;

	@Autowired
	protected VetService vetService;

	@Autowired
	protected VisitService visitService;

	@Autowired
	protected ProductService productService;

	Intervention intervention;

	@BeforeEach
	void instantiateIntervention() {

		intervention = new Intervention();
		intervention.setName("Castración");
		intervention.setDescription("Descripción de la intervencion");
		intervention.setVet(vetService.findVetById(1));
		Visit visit = visitService.findVisitById(2);
		visit.setIntervention(intervention);
		intervention.setVisit(visit);
		intervention.setRequiredProducts(new ArrayList<Product>(productService.findProducts()));

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

		assertThat(id).isNotNull();
		assertThat(intervention).isEqualTo(interventionService.findInterventionById(id));
		assertThat(intervention).isEqualTo(visitService.findVisitById(visitId).getIntervention());

	}

	@Test
	void shouldUpdateIntervention() {
		
		interventionService.saveIntervention(intervention);
		
		Integer id = null;
		id = intervention.getId();
		
		Intervention interventionToUpdate = interventionService.findInterventionById(id);
		assertThat(interventionToUpdate).isNotNull();
		interventionToUpdate.setName("Nombre modificado");
		interventionToUpdate.setDescription("Descripcion modificada");
		List<Product> modifiedList = new ArrayList<Product>(productService.findProducts());
		modifiedList.remove(0);
		interventionToUpdate.setRequiredProducts(modifiedList);

		interventionService.saveIntervention(interventionToUpdate);

		assertThat(interventionService.findInterventionById(id).getName()).isEqualTo("Nombre modificado");
		assertThat(interventionService.findInterventionById(id).getDescription()).isEqualTo("Descripcion modificada");

	}

	@ParameterizedTest
	@Transactional
	@ValueSource(strings = { "Intervención 24", "Castración", "aaa", "aaaaaaaaaaaaaaaaaaaaaaaaa",
			"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
	public void shouldInsertProviderIntoDatabaseWithParametizedNames(String name) {

		intervention.setName(name);
		assertThat(intervention.getName()).isEqualTo(name);

		interventionService.saveIntervention(intervention);

		Integer id = null;
		id = intervention.getId();
		Integer visitId = null;
		visitId = intervention.getVisit().getId();

		assertThat(id).isNotNull();
		assertThat(intervention).isEqualTo(interventionService.findInterventionById(id));
		assertThat(intervention).isEqualTo(visitService.findVisitById(visitId).getIntervention());

	}
	
	@ParameterizedTest
	@Transactional
	@ValueSource(strings = { "", " " })
	public void shouldNotInsertProviderIntoDatabaseWithParametizedNames(String name) {

		intervention.setName(name);
		assertThat(intervention.getName()).isEqualTo(name);

		assertThrows(ConstraintViolationException.class, () -> interventionService.saveIntervention(intervention));

	}
	
	@Test
	@Transactional
	void shouldInsertInterventionIntoDatabaseWithEmptyProductList() {

		List <Product> list = new ArrayList<Product>();
		intervention.setRequiredProducts(list);
		assertThat(intervention.getRequiredProducts()).isEmpty();

		interventionService.saveIntervention(intervention);

		Integer id = null;
		id = intervention.getId();
		Integer visitId = null;
		visitId = intervention.getVisit().getId();

		assertThat(id).isNotNull();
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

}
