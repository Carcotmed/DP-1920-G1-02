package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
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
	
	
	@Test
	void shouldFindInterventionById () {
		
		Intervention intervention = interventionService.findInterventionById(1);
		
		assertThat (intervention).isNotNull();
		
	}
	
	@Test
	void shouldNotFindInterventionById () {
		
		Intervention intervention = interventionService.findInterventionById(9999);
		
		assertThat (intervention).isNull();
		
	}

	@Test
	@Transactional
	void shouldInsertInterventionIntoDBAndGenerateId () {
		
		Intervention intervention = new Intervention ();
		intervention.setName("Castración");
		intervention.setDescription("Descripción de la intervencion");
		intervention.setVet(vetService.findVetById(1));
		Visit visit = visitService.findVisitById(2);
		visit.setIntervention(intervention);
		intervention.setVisit(visit);
		intervention.setRequiredProducts(new ArrayList <Product> (productService.findProducts()));
		
		interventionService.saveIntervention(intervention);
		
		assertThat (visitService.findVisitById(2).getIntervention()).isEqualTo(intervention);
		assertThat (intervention.getId()).isNotNull();
		
	}
	
	@Test
	void shouldUpdateIntervention () {
		
		Intervention intervention = interventionService.findInterventionById(1);
		intervention.setName("Nombre modificado");
		intervention.setDescription("Descripcion modificada");
		List <Product> modifiedList = new ArrayList <Product> (productService.findProducts());
		modifiedList.remove(0);
		intervention.setRequiredProducts(modifiedList);
		
		interventionService.saveIntervention(intervention);
		
		assertThat (interventionService.findInterventionById(1).getName()).isEqualTo("Nombre modificado");
		assertThat (interventionService.findInterventionById(1).getDescription()).isEqualTo("Descripcion modificada");
		
	}
	
	@Test
	void shouldDeleteIntervention () {
		
		Intervention intervention = interventionService.findInterventionById(1);
		assertThat(intervention).isNotNull();
		System.out.println(intervention);
		interventionService.deleteIntervention(intervention);
		
		assertThat(interventionService.findInterventionById(1)).isNull();
	}

}
