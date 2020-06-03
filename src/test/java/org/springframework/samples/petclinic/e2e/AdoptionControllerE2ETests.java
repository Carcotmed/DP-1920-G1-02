
package org.springframework.samples.petclinic.e2e;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.web.OwnerController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test class for {@link OwnerController}
 *
 * @author Colin But
 */
@TestPropertySource(locations = "classpath:application-mysql.properties") //Decoment to try the MySql tests

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class AdoptionControllerE2ETests {
	
	@Autowired
	private MockMvc				mockMvc;
	
	@Autowired
	private PetService petService;

	@Autowired
	private AdoptionService adoptionService;

	@WithMockUser(value = "spring")
	@Test
	void testShowAdoptions() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/adoptions")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attribute("pets", org.hamcrest.Matchers.hasSize(2)))
			.andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "owner")
	@Test
	void testShowAdoptionsAsOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/adoptions")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attribute("pets", org.hamcrest.Matchers.hasSize(2)))
			.andExpect(MockMvcResultMatchers.model().attribute("isOwner", true)).andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "owner", username = "owner1")
	@Test
	void testShowMyAdoptions() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/adoptions/myAdoptions")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attribute("adoptions", org.hamcrest.Matchers.hasSize(2)))
			.andExpect(MockMvcResultMatchers.view().name("adoptions/myAdoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian", username = "owner1")
	@Test
	void testShowMyAdoptionsAsVet() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/adoptions/myAdoptions")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attribute("error", "Only owners can access to this feature"))
			.andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testShowAllAdoptions() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/adoptions/allAdoptions")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attribute("adoptions", org.hamcrest.Matchers.hasSize(2)))
			.andExpect(MockMvcResultMatchers.view().name("adoptions/allAdoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "owner")
	@Test
	void testShowAllAdoptionsAsOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/adoptions/allAdoptions")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attribute("error", "Only vets and admins can access to this feature"))
			.andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "owner")
	@Test
	void testInitCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/adoptions/new/{petId}", 14)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("adoption"))
			.andExpect(MockMvcResultMatchers.view().name("adoptions/createOrUpdateAdoptionForm"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testInitCreationFormAsVet() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/adoptions/new/{petId}", 14)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("error", "You can't adopt a pet if you are not an owner")).andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "owner", username = "owner1")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/adoptions/new/{petId}", 14).requestAttr("end", LocalDate.parse("2030-02-02")).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian", username = "owner1")
	@Test
	void testProcessCreationFormAsVet() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/adoptions/new/{petId}", 15).requestAttr("end", LocalDate.parse("2030-02-02")).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.model().attribute("error", "You can't adopt a pet if you are not an owner")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "owner", username = "owner1")
	@Test
	void testProcessCreationFormFromAnotherOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/adoptions/new/{petId}", 2).requestAttr("end", LocalDate.parse("2030-02-02")).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.model().attribute("error", "You can't adopt a pet which another person owns")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "owner", username = "owner1")
	@Test
	void testProcessEmptyCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/adoptions/new/{petId}", 15).with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testInitAdoptableCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/adoptions/newAdoptable")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("pet"))
			.andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdatePetForm"));
	}

	@WithMockUser(value = "spring", authorities = "owner")
	@Test
	void testInitAdoptableCreationFormAsOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/adoptions/newAdoptable")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("error", "You can't register a new adoptable pet if you are not a veterinarian or an admin")).andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testProcessAdoptableCreationFormSuccess() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/adoptions/newAdoptable").requestAttr("birthDate", LocalDate.parse("2010-02-02")).param("name", "prueba").requestAttr("type", this.petService.findPetTypes().iterator().next())
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testProcessAdoptableCreationFormEmpty() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/adoptions/newAdoptable").with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdatePetForm"));
	}

	@WithMockUser(value = "spring", authorities = "owner")
	@Test
	void testProcessAdoptableCreationFormAsOwner() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/adoptions/newAdoptable").requestAttr("birthDate", LocalDate.parse("2010-02-02")).param("name", "prueba").requestAttr("type", this.petService.findPetTypes().iterator().next())
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.model().attribute("error", "You can't register a new adoptable pet if you are not a veterinarian or an admin")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "admin")
	@Test
	void testDeleteAdoptionSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/adoptions/delete/{adoptionId}", 1)).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("adoptions/allAdoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "owner")
	@Test
	void testDeleteAdoptionAsOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/adoptions/delete/{adoptionId}", 1)).andExpect(MockMvcResultMatchers.model().attribute("error", "Only administrators can delete adoptions"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}
}
