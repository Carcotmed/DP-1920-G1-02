package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
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
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.InterventionService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.samples.petclinic.service.VisitService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

@WebMvcTest(controllers = InterventionController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class InterventionControllerTests {

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

	private static final int TEST_INTERVENTION_ID = 99;
	private static final int TEST_VISIT_ID = 98;
	private static final int TEST_VET_ID = 97;
	private static final int TEST_PET_ID = 96;
	private static final int TEST_OWNER_ID = 95;
	private static final int TEST_PRODUCT_ID = 94;

	private Intervention intervention;
	private Visit visit;
	private Vet vet;
	private List<Product> requiredProducts;
	private Product product;

	@BeforeEach
	void setup() {

		visit = new Visit();
		visit.setId(TEST_VISIT_ID);
		visit.setDescription("Visit Description");

		vet = new Vet();
		vet.setFirstName("Vet First Name");
		vet.setLastName("Vet Last Name");
		vet.setId(TEST_VET_ID);

		requiredProducts = new ArrayList<Product>();

		intervention = new Intervention();
		intervention.setId(TEST_INTERVENTION_ID);
		intervention.setName("Castración");
		intervention.setVet(vet);
		intervention.setVisit(visit);
		intervention.setRequiredProducts(requiredProducts);

		product = new Product();
		product.setAllAvailable(true);
		product.setEnabled(true);
		product.setName("Test Product");
		product.setPrice(12.0);
		product.setProvider(new Provider());
		product.setQuantity(1);
		product.setId(TEST_PRODUCT_ID);

		given(this.productService.findProductById(TEST_INTERVENTION_ID)).willReturn(product);
		given(this.interventionService.findInterventionById(TEST_INTERVENTION_ID)).willReturn(intervention);
		given(this.visitService.findVisitById(TEST_VISIT_ID)).willReturn(visit);
		given(interventionService.findInterventionWithProductsById(TEST_INTERVENTION_ID)).willReturn(intervention);
		given(interventionService.findInterventionWithVisitAndProductsById(TEST_INTERVENTION_ID))
				.willReturn(intervention);
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/new", TEST_OWNER_ID,
				TEST_PET_ID, TEST_VISIT_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("intervention"))
				.andExpect(view().name("interventions/createOrUpdateInterventionForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/new", TEST_OWNER_ID,
				TEST_PET_ID, TEST_VISIT_ID).with(csrf()).param("name", "Castracion nueva").param("vet", "1")
						.param("visit", "1").param("requiredProducts", "{1}"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("interventions/createOrUpdateInterventionForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/new", TEST_OWNER_ID,
				TEST_PET_ID, TEST_VISIT_ID).with(csrf()).param("vet", "1").param("visit", "1").param("requiredProducts",
						"{1}"))
				.andExpect(status().isOk()).andExpect(model().attributeHasErrors("intervention"))
				.andExpect(model().attributeHasFieldErrors("intervention", "name"))
				.andExpect(view().name("interventions/createOrUpdateInterventionForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreateFormNotEnoughProduct() throws Exception {

		product.setQuantity(0);

		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/new", TEST_OWNER_ID,
				TEST_PET_ID, TEST_VISIT_ID).with(csrf()).param("vet", "1").param("visit", "1").param("requiredProducts",
						"{" + TEST_PRODUCT_ID + "}"))
				.andExpect(status().isOk()).andExpect(model().attributeHasErrors("intervention"))
				.andExpect(model().attributeHasFieldErrors("intervention", "requiredProducts"))
				.andExpect(view().name("interventions/createOrUpdateInterventionForm"));

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitEditForm() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/{interventionId}/edit",
				TEST_OWNER_ID, TEST_PET_ID, TEST_VISIT_ID, TEST_INTERVENTION_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("intervention"))
				.andExpect(model().attribute("intervention", hasProperty("name", is("Castración"))))
				.andExpect(model().attribute("intervention", hasProperty("vet", is(vet))))
				.andExpect(model().attribute("intervention", hasProperty("visit", is(visit))))
				.andExpect(model().attribute("intervention", hasProperty("requiredProducts", is(requiredProducts))))

				.andExpect(view().name("interventions/createOrUpdateInterventionForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessEditFormSuccess() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/{interventionId}/edit",
				TEST_OWNER_ID, TEST_PET_ID, TEST_VISIT_ID, TEST_INTERVENTION_ID).with(csrf())
						.param("name", "Castración updated").param("vet", "" + TEST_VET_ID)
						.param("visit", "" + TEST_VISIT_ID))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("interventions/createOrUpdateInterventionForm"));

	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessEditFormHasErrors() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/{interventionId}/edit",
				TEST_OWNER_ID, TEST_PET_ID, TEST_VISIT_ID, TEST_INTERVENTION_ID).with(csrf()).param("name", "aa")
						.param("vet", ""))
				.andExpect(status().isOk()).andExpect(model().attributeHasErrors("intervention"))
				.andExpect(model().attributeHasFieldErrors("intervention", "name", "vet"))
				.andExpect(view().name("interventions/createOrUpdateInterventionForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testDelete() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/{interventionId}/delete",
				TEST_OWNER_ID, TEST_PET_ID, TEST_VISIT_ID, TEST_INTERVENTION_ID)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/{ownerId}/pets/{petId}"));
	}

}