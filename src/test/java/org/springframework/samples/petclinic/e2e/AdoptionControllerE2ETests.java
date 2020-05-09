
package org.springframework.samples.petclinic.e2e;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.web.OwnerController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test class for {@link OwnerController}
 *
 * @author Colin But
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class AdoptionControllerE2ETests {

	private static final int	ida1	= 1;
	private static final int	ida2	= 2;
	private static final int	ida3	= 3;
	private static final int	ido1	= 1;
	private static final int	ido2	= 2;
	private static final int	idp1	= 1;
	private static final int	idp2	= 2;
	private static final int	idp3	= 3;
	private static final int	idp4	= 4;

	@MockBean
	private OwnerService		ownerService;

	@MockBean
	private AdoptionService		adoptionService;

	@MockBean
	private UserService			userService;

	@MockBean
	private PetService			petService;

	@Autowired
	private MockMvc				mockMvc;

	private Adoption			adoption1;
	private Adoption			adoption2;
	private Adoption			adoption3;
	private PetType				type1;
	private Owner				owner1;
	private Owner				owner2;
	private Pet					pet1;
	private Pet					pet2;
	private Pet					pet3;
	private Pet					pet4;


	@Transactional
	@BeforeEach
	void init() throws DataAccessException, DuplicatedPetNameException {
		this.type1 = new PetType();
		this.type1.setName("Testing Type");
		this.type1.setId(1);
		List<PetType> types = new ArrayList<>();
		types.add(this.type1);
		BDDMockito.given(this.petService.findPetTypes()).willReturn(types);

		this.owner1 = new Owner();
		this.owner1.setAddress("Address1");
		this.owner1.setCity("city1");
		this.owner1.setFirstName("first1");
		this.owner1.setLastName("last1");
		this.owner1.setTelephone("857687980");
		this.owner1.setId(AdoptionControllerE2ETests.ido1);
		BDDMockito.given(this.ownerService.findOwnerById(AdoptionControllerE2ETests.ido1)).willReturn(this.owner1);

		Owner ownerVet = new Owner();
		ownerVet.setAddress("Address1");
		ownerVet.setCity("city1");
		ownerVet.setFirstName("Vet");
		ownerVet.setLastName("");
		ownerVet.setTelephone("857687980");
		ownerVet.setId(11);
		BDDMockito.given(this.ownerService.findOwnerByFirstName("Vet")).willReturn(ownerVet);

		this.owner2 = new Owner();
		this.owner2.setAddress("Address2");
		this.owner2.setCity("city2");
		this.owner2.setFirstName("first2");
		this.owner2.setLastName("last2");
		this.owner2.setTelephone("857687980");
		this.owner2.setId(AdoptionControllerE2ETests.ido2);
		BDDMockito.given(this.ownerService.findOwnerById(AdoptionControllerE2ETests.ido2)).willReturn(this.owner2);

		this.pet1 = new Pet();
		this.pet1.setBirthDate(LocalDate.now().minusDays(10));
		this.pet1.setName("nameee1");
		this.pet1.setOwner(this.ownerService.findOwnerById(AdoptionControllerE2ETests.ido1));
		this.pet1.setType(this.type1);
		this.pet1.setId(AdoptionControllerE2ETests.idp1);
		BDDMockito.given(this.petService.findPetById(AdoptionControllerE2ETests.idp1)).willReturn(this.pet1);

		this.pet2 = new Pet();
		this.pet2.setBirthDate(LocalDate.now().minusDays(10));
		this.pet2.setName("nameee2");
		this.pet2.setOwner(this.ownerService.findOwnerById(AdoptionControllerE2ETests.ido2));
		this.pet2.setType(this.type1);
		this.pet2.setId(AdoptionControllerE2ETests.idp2);
		BDDMockito.given(this.petService.findPetById(AdoptionControllerE2ETests.idp2)).willReturn(this.pet2);

		this.pet3 = new Pet();
		this.pet3.setBirthDate(LocalDate.now().minusDays(10));
		this.pet3.setName("nameee3");
		this.pet3.setOwner(this.ownerService.findOwnerById(AdoptionControllerE2ETests.ido1));
		this.pet3.setType(this.type1);
		this.pet3.setId(AdoptionControllerE2ETests.idp3);
		BDDMockito.given(this.petService.findPetById(AdoptionControllerE2ETests.idp3)).willReturn(this.pet3);

		this.pet4 = new Pet();
		this.pet4.setBirthDate(LocalDate.now().minusDays(10));
		this.pet4.setName("nameee4");
		this.pet4.setOwner(this.ownerService.findOwnerByFirstName("Vet"));
		this.pet4.setType(this.type1);
		this.pet4.setId(AdoptionControllerE2ETests.idp4);
		BDDMockito.given(this.petService.findPetById(AdoptionControllerE2ETests.idp4)).willReturn(this.pet4);

		this.adoption1 = new Adoption();
		this.adoption1.setDate(LocalDate.now().plusDays(3));
		this.adoption1.setOwner(this.ownerService.findOwnerById(AdoptionControllerE2ETests.ido1));
		this.adoption1.setPet(this.petService.findPetById(AdoptionControllerE2ETests.idp1));
		this.adoption1.setId(AdoptionControllerE2ETests.ida1);
		BDDMockito.given(this.adoptionService.findById(AdoptionControllerE2ETests.ida1)).willReturn(this.adoption1);

		this.adoption2 = new Adoption();
		this.adoption2.setDate(LocalDate.now().plusDays(3));
		this.adoption2.setEnd(LocalDate.now().plusDays(30));
		this.adoption2.setOwner(this.ownerService.findOwnerById(AdoptionControllerE2ETests.ido2));
		this.adoption2.setPet(this.petService.findPetById(AdoptionControllerE2ETests.idp2));
		this.adoption2.setId(AdoptionControllerE2ETests.ida2);
		BDDMockito.given(this.adoptionService.findById(AdoptionControllerE2ETests.ida2)).willReturn(this.adoption2);

		this.adoption3 = new Adoption();
		this.adoption2.setDate(LocalDate.now().plusDays(3));
		this.adoption2.setEnd(LocalDate.now().plusDays(30));
		this.adoption2.setOwner(this.ownerService.findOwnerById(AdoptionControllerE2ETests.ido1));
		this.adoption2.setPet(this.petService.findPetById(AdoptionControllerE2ETests.idp3));
		this.adoption2.setId(AdoptionControllerE2ETests.ida3);
		BDDMockito.given(this.adoptionService.findById(AdoptionControllerE2ETests.ida3)).willReturn(this.adoption3);

		List<Pet> adoptables = new ArrayList<>();
		adoptables.add(this.pet4);
		BDDMockito.given(this.petService.findAdoptablePets()).willReturn(adoptables);

		BDDMockito.given(this.ownerService.findOwnerByUsername("owner1")).willReturn(this.owner1);

		List<Adoption> adoptions = new ArrayList<>();
		adoptions.add(this.adoption1);
		adoptions.add(this.adoption3);
		BDDMockito.given(this.adoptionService.findAdoptionsByOwner(AdoptionControllerE2ETests.ido1)).willReturn(adoptions);

		List<Adoption> allAdoptions = new ArrayList<>();
		allAdoptions.add(this.adoption1);
		allAdoptions.add(this.adoption2);
		allAdoptions.add(this.adoption3);
		BDDMockito.given(this.adoptionService.findAllAdoptions()).willReturn(allAdoptions);
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowAdoptions() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/adoptions")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attribute("pets", this.petService.findAdoptablePets()))
			.andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "owner")
	@Test
	void testShowAdoptionsAsOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/adoptions")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attribute("pets", this.petService.findAdoptablePets()))
			.andExpect(MockMvcResultMatchers.model().attribute("isOwner", true)).andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "owner", username = "owner1")
	@Test
	void testShowMyAdoptions() throws Exception {
		List<Adoption> adoptions = new ArrayList<>();
		adoptions.add(this.adoption1);
		adoptions.add(this.adoption3);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/adoptions/myAdoptions")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attribute("adoptions", adoptions))
			.andExpect(MockMvcResultMatchers.view().name("adoptions/myAdoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian", username = "owner1")
	@Test
	void testShowMyAdoptionsAsVet() throws Exception {
		List<Adoption> adoptions = new ArrayList<>();
		adoptions.add(this.adoption1);
		adoptions.add(this.adoption3);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/adoptions/myAdoptions")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attribute("error", "Only owners can access to this feature"))
			.andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testShowAllAdoptions() throws Exception {
		List<Adoption> adoptions = new ArrayList<>();
		adoptions.add(this.adoption1);
		adoptions.add(this.adoption2);
		adoptions.add(this.adoption3);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/adoptions/allAdoptions")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attribute("adoptions", adoptions))
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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/adoptions/new/{petId}", AdoptionControllerE2ETests.idp4)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("adoption"))
			.andExpect(MockMvcResultMatchers.view().name("adoptions/createOrUpdateAdoptionForm"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testInitCreationFormAsVet() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/adoptions/new/{petId}", AdoptionControllerE2ETests.idp4)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("error", "You can't adopt a pet if you are not an owner")).andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "owner", username = "owner1")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/adoptions/new/{petId}", AdoptionControllerE2ETests.idp4).requestAttr("end", LocalDate.parse("2030-02-02")).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian", username = "owner1")
	@Test
	void testProcessCreationFormAsVet() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/adoptions/new/{petId}", AdoptionControllerE2ETests.idp4).requestAttr("end", LocalDate.parse("2030-02-02")).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.model().attribute("error", "You can't adopt a pet if you are not an owner")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "owner", username = "owner1")
	@Test
	void testProcessCreationFormFromAnotherOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/adoptions/new/{petId}", AdoptionControllerE2ETests.idp2).requestAttr("end", LocalDate.parse("2030-02-02")).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.model().attribute("error", "You can't adopt a pet which another person owns")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "owner", username = "owner1")
	@Test
	void testProcessEmptyCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/adoptions/new/{petId}", AdoptionControllerE2ETests.idp4).with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error"))
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
			.perform(MockMvcRequestBuilders.post("/adoptions/newAdoptable", AdoptionControllerE2ETests.idp4).requestAttr("birthDate", LocalDate.parse("2010-02-02")).param("name", "prueba").requestAttr("type", this.petService.findPetTypes().iterator().next())
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testProcessAdoptableCreationFormEmpty() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/adoptions/newAdoptable", AdoptionControllerE2ETests.idp4).with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdatePetForm"));
	}

	@WithMockUser(value = "spring", authorities = "owner")
	@Test
	void testProcessAdoptableCreationFormAsOwner() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/adoptions/newAdoptable", AdoptionControllerE2ETests.idp4).requestAttr("birthDate", LocalDate.parse("2010-02-02")).param("name", "prueba").requestAttr("type", this.petService.findPetTypes().iterator().next())
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.model().attribute("error", "You can't register a new adoptable pet if you are not a veterinarian or an admin")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "admin")
	@Test
	void testDeleteAdoptionSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/adoptions/delete/{adoptionId}", AdoptionControllerE2ETests.ida1)).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("adoptions/allAdoptionsList"));
	}

	@WithMockUser(value = "spring", authorities = "owner")
	@Test
	void testDeleteAdoptionAsOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/adoptions/delete/{adoptionId}", AdoptionControllerE2ETests.ida1)).andExpect(MockMvcResultMatchers.model().attribute("error", "Only administrators can delete adoptions"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("adoptions/adoptionsList"));
	}
}
