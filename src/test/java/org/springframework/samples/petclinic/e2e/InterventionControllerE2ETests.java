
package org.springframework.samples.petclinic.e2e;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.model.Intervention;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.InterventionService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.samples.petclinic.service.VisitService;
import org.springframework.samples.petclinic.web.InterventionController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class InterventionControllerE2ETests {

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

		this.visit = new Visit();
		this.visit.setId(InterventionControllerE2ETests.TEST_VISIT_ID);
		this.visit.setDescription("Visit Description");

		this.vet = new Vet();
		this.vet.setFirstName("Vet First Name");
		this.vet.setLastName("Vet Last Name");
		this.vet.setId(InterventionControllerE2ETests.TEST_VET_ID);

		this.requiredProducts = new ArrayList<Product>();

		this.intervention = new Intervention();
		this.intervention.setId(InterventionControllerE2ETests.TEST_INTERVENTION_ID);
		this.intervention.setName("Castración");
		this.intervention.setVet(this.vet);
		this.intervention.setVisit(this.visit);
		this.intervention.setRequiredProducts(this.requiredProducts);

		this.product = new Product();
		this.product.setAllAvailable(true);
		this.product.setEnabled(true);
		this.product.setName("Test Product");
		this.product.setPrice(12.0);
		this.product.setProvider(new Provider());
		this.product.setQuantity(1);
		this.product.setId(InterventionControllerE2ETests.TEST_PRODUCT_ID);

		BDDMockito.given(this.productService.findProductById(InterventionControllerE2ETests.TEST_INTERVENTION_ID))
				.willReturn(this.product);
		BDDMockito
				.given(this.interventionService
						.findInterventionById(InterventionControllerE2ETests.TEST_INTERVENTION_ID))
				.willReturn(this.intervention);
		BDDMockito.given(this.visitService.findVisitById(InterventionControllerE2ETests.TEST_VISIT_ID))
				.willReturn(this.visit);
	}

	@WithMockUser(value = "spring", authorities = "admin")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/new",
						InterventionControllerE2ETests.TEST_OWNER_ID, InterventionControllerE2ETests.TEST_PET_ID,
						InterventionControllerE2ETests.TEST_VISIT_ID))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("intervention"))
				.andExpect(MockMvcResultMatchers.view().name("interventions/createOrUpdateInterventionForm"));
	}

	@WithMockUser(value = "spring", authorities = "admin")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/new",
								InterventionControllerE2ETests.TEST_OWNER_ID,
								InterventionControllerE2ETests.TEST_PET_ID,
								InterventionControllerE2ETests.TEST_VISIT_ID)
						.with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Castracion nueva")
						.param("vet", "1").param("visit", "1").param("requiredProducts", "{1}"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("interventions/createOrUpdateInterventionForm"));
	}

	@WithMockUser(value = "spring", authorities = "admin")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/new",
						InterventionControllerE2ETests.TEST_OWNER_ID, InterventionControllerE2ETests.TEST_PET_ID,
						InterventionControllerE2ETests.TEST_VISIT_ID)
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("vet", "1").param("visit", "1")
				.param("requiredProducts", "{1}")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("intervention"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("intervention", "name"))
				.andExpect(MockMvcResultMatchers.view().name("interventions/createOrUpdateInterventionForm"));
	}

	@WithMockUser(value = "spring", authorities = "admin")
	@Test
	void testProcessCreateFormNotEnoughProduct() throws Exception {

		this.product.setQuantity(0);

		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/new",
								InterventionControllerE2ETests.TEST_OWNER_ID,
								InterventionControllerE2ETests.TEST_PET_ID,
								InterventionControllerE2ETests.TEST_VISIT_ID)
						.with(SecurityMockMvcRequestPostProcessors.csrf()).param("vet", "1").param("visit", "1")
						.param("requiredProducts", "{" + InterventionControllerE2ETests.TEST_PRODUCT_ID + "}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("intervention"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("intervention", "requiredProducts"))
				.andExpect(MockMvcResultMatchers.view().name("interventions/createOrUpdateInterventionForm"));

	}

	@WithMockUser(value = "spring", authorities = "admin")
	@Test
	void testInitEditForm() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(
						"/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/{interventionId}/edit",
						InterventionControllerE2ETests.TEST_OWNER_ID, InterventionControllerE2ETests.TEST_PET_ID,
						InterventionControllerE2ETests.TEST_VISIT_ID,
						InterventionControllerE2ETests.TEST_INTERVENTION_ID))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("intervention"))
				.andExpect(MockMvcResultMatchers.model().attribute("intervention",
						Matchers.hasProperty("name", Matchers.is("Castración"))))
				.andExpect(MockMvcResultMatchers.model().attribute("intervention",
						Matchers.hasProperty("vet", Matchers.is(this.vet))))
				.andExpect(MockMvcResultMatchers.model().attribute("intervention",
						Matchers.hasProperty("visit", Matchers.is(this.visit))))
				.andExpect(MockMvcResultMatchers.model().attribute("intervention",
						Matchers.hasProperty("requiredProducts", Matchers.is(this.requiredProducts))))

				.andExpect(MockMvcResultMatchers.view().name("interventions/createOrUpdateInterventionForm"));
	}

	@WithMockUser(value = "spring", authorities = "admin")
	@Test
	void testProcessEditFormSuccess() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/{interventionId}/edit",
								InterventionControllerE2ETests.TEST_OWNER_ID,
								InterventionControllerE2ETests.TEST_PET_ID,
								InterventionControllerE2ETests.TEST_VISIT_ID,
								InterventionControllerE2ETests.TEST_INTERVENTION_ID)
						.with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Castración updated")
						.param("vet", "" + InterventionControllerE2ETests.TEST_VET_ID)
						.param("visit", "" + InterventionControllerE2ETests.TEST_VISIT_ID))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("interventions/createOrUpdateInterventionForm"));

	}

	@WithMockUser(value = "spring", authorities = "admin")
	@Test
	void testProcessEditFormHasErrors() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/{interventionId}/edit",
								InterventionControllerE2ETests.TEST_OWNER_ID,
								InterventionControllerE2ETests.TEST_PET_ID,
								InterventionControllerE2ETests.TEST_VISIT_ID,
								InterventionControllerE2ETests.TEST_INTERVENTION_ID)
						.with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "aa").param("vet", ""))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("intervention"))
				.andExpect(
						MockMvcResultMatchers.model().attributeHasFieldErrors("intervention", "name", "vet"))
				.andExpect(MockMvcResultMatchers.view().name("interventions/createOrUpdateInterventionForm"));
	}

	@WithMockUser(value = "spring", authorities = "admin")
	@Test
	void testDelete() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(
						"/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/{interventionId}/delete",
						InterventionControllerE2ETests.TEST_OWNER_ID, InterventionControllerE2ETests.TEST_PET_ID,
						InterventionControllerE2ETests.TEST_VISIT_ID,
						InterventionControllerE2ETests.TEST_INTERVENTION_ID))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/owners/{ownerId}/pets/{petId}"));
	}

}
