
package org.springframework.samples.petclinic.e2e;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@TestPropertySource(locations = "classpath:application-mysql.properties") //Decoment to try the MySql tests


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class InterventionControllerE2ETests {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private VetService vetService;

	@WithMockUser(value = "spring", authorities = "admin")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/new",
						1, 1,
						6))
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
								1,
								1,
								6)
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
						3, 8,
						3)
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("vet", "1").param("visit", "1")
				.param("requiredProducts", "{1}")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("intervention"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("intervention", "name"))
				.andExpect(MockMvcResultMatchers.view().name("interventions/createOrUpdateInterventionForm"));
	}

	@WithMockUser(value = "spring", authorities = "admin")
	@Test
	void testProcessCreateFormNotEnoughProduct() throws Exception {


		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/new",
								3,
								8,
								3)
						.with(SecurityMockMvcRequestPostProcessors.csrf()).param("vet", "1").param("visit", "1")
						.param("requiredProducts", "{" + 4 + "}"))
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
						1, 1,
						1,
						1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("intervention"))
				.andExpect(MockMvcResultMatchers.model().attribute("intervention",
						Matchers.hasProperty("name", Matchers.is("Castracion"))))
				.andExpect(MockMvcResultMatchers.model().attribute("intervention",
						Matchers.hasProperty("vet", Matchers.hasProperty("id", Matchers.is(1)))))
				.andExpect(MockMvcResultMatchers.model().attribute("intervention",
						Matchers.hasProperty("visit", Matchers.hasProperty("id", Matchers.is(1)))))
				.andExpect(MockMvcResultMatchers.model().attribute("intervention",
						Matchers.hasProperty("requiredProducts", Matchers.hasSize(0))))

				.andExpect(MockMvcResultMatchers.view().name("interventions/createOrUpdateInterventionForm"));
	}

	@WithMockUser(value = "spring", authorities = "admin")
	@Test
	void testProcessEditFormSuccess() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/{interventionId}/edit",
								1,
								1,
								1,
								1)
						.with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Castración updated")
						.param("vet", "" + 1)
						.param("visit", "" + 1))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("interventions/createOrUpdateInterventionForm"));

	}

	@WithMockUser(value = "spring", authorities = "admin")
	@Test
	void testProcessEditFormHasErrors() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/{interventionId}/edit",
								1,
								1,
								1,
								1)
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
						1, 1,
						9,
						6))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/owners/{ownerId}/pets/{petId}"));
	}

}
