package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Intervention;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.InterventionService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.samples.petclinic.service.VisitService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = InterventionController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class InterventionControllerTests {

	private static final int TEST_INTERVENTION_ID = 1;

	@Autowired
	private InterventionController interventionController;

	@MockBean
	private InterventionService interventionService;

	@MockBean
	private ProductService productService;

	@MockBean
	private VisitService visitService;

	@MockBean
	private VetService vetService;

	@Autowired
	private MockMvc mockMvc;

	private Intervention intervention;

	@BeforeEach
	void setup() {

		intervention = new Intervention();
		intervention.setId(TEST_INTERVENTION_ID);
		intervention.setName("Castración");
		intervention.setDescription("Descripción de la intervencion");
		intervention.setVet(new Vet());
		intervention.setVisit(new Visit());
		intervention.setRequiredProducts(Arrays.asList(new Product(), new Product()));
		given(this.interventionService.findInterventionById(TEST_INTERVENTION_ID)).willReturn(intervention);

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/owners/*/pets/*/visits/*/interventions/new")).andExpect(status().isOk())
				.andExpect(model().attributeExists("intervention"))
				.andExpect(view().name("interventions/createOrUpdateInterventionForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/owners/*/pets/*/visits/*/interventions/new").with(csrf())
				.param("name", "Castracion nueva").param("description", "Nueva descripcion").param("vet", "1")
				.param("visit", "1").param("requiredProducts", "01316761638")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/owners/*/pets/*/visits/*/interventions/new").with(csrf()).param("description", "Nueva descripcion").param("vet", "1")
				.param("visit", "1").param("requiredProducts", "01316761638")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("intervention"))
				.andExpect(model().attributeHasFieldErrors("intervention", "name"))
				.andExpect(view().name("interventions/createOrUpdateInterventionForm"));
	}
}