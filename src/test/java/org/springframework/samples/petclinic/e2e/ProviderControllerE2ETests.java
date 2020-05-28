package org.springframework.samples.petclinic.e2e;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.service.DiscountService;
import org.springframework.samples.petclinic.service.OrderService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.service.ProviderService;
import org.springframework.samples.petclinic.web.ProviderController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@TestPropertySource(locations = "classpath:application-mysql.properties") //Decoment to try the MySql tests

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ProviderControllerE2ETests {

	@Autowired
	private ProviderController providerController;

	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private DiscountService discountService;
	
	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	@Autowired
	private MockMvc mockMvc;
	

	@WithMockUser(username="admin1", authorities= {"admin"})
	@Test
	void testShowProviders() throws Exception {
		mockMvc.perform(get("/providers")).andExpect(status().isOk()).andExpect(model().attributeExists("providers"))
				.andExpect(view().name("providers/providersList"));
	}

	@WithMockUser(username="admin1", authorities= {"admin"})
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/providers/new")).andExpect(status().isOk()).andExpect(model().attributeExists("provider"))
				.andExpect(view().name("providers/createOrUpdateProviderForm"));
	}

	@WithMockUser(username="admin1", authorities= {"admin"})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/providers/new").with(csrf())
				.param("name", "Provider")
				.param("phone", "123456789")
				.param("address", "Address")
				.param("email", "email@email.com"))
		.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/providers"));
	}

	@WithMockUser(username="admin1", authorities= {"admin"})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/providers/new").with(csrf())
				.param("phone", "123456789")
				.param("address", "Address")
				.param("email", "email@email.com"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("provider"))
				.andExpect(model().attributeHasFieldErrors("provider", "name"))
				.andExpect(view().name("providers/createOrUpdateProviderForm"));
	}
	
	@WithMockUser(username="admin1", authorities= {"admin"})
	@Test
	void testInitEditForm() throws Exception {
		mockMvc.perform(get("/providers/{providerId}/edit",
				1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("provider"))
				.andExpect(model().attribute("provider", hasProperty("name", is("Pipo1"))))
				.andExpect(model().attribute("provider", hasProperty("email", is("pipo1@gmail.com"))))
				.andExpect(model().attribute("provider", hasProperty("phone", is("123456789"))))
				.andExpect(model().attribute("provider", hasProperty("address", is("Calle Pipo n1"))))

				.andExpect(view().name("providers/createOrUpdateProviderForm"));
	}

	@WithMockUser(username="admin1", authorities= {"admin"})
	@Test
	void testProcessEditFormSuccess() throws Exception {
		mockMvc.perform(get("/providers/{providerId}/edit",
				1).with(csrf())
						.param("name", "Provider updated")
						.param("email", "emailUpdated@email.com")
						.param("phone", "987654486")
						.param("address", "Address updated"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("providers/createOrUpdateProviderForm"));
		
	}

	@WithMockUser(username="admin1", authorities= {"admin"})
	@Test
	void testProcessEditFormHasErrors() throws Exception {
		mockMvc.perform(post("/providers/{providerId}/edit",
				1).with(csrf())
						.param("name", "Je")
						.param("email", "email.com")
						.param("phone", "-987654486")
						.param("address", "Address updated"))
				.andExpect(status().isOk()).andExpect(model().attributeHasErrors("provider"))
				.andExpect(model().attributeHasFieldErrors("provider", "name", "email", "phone"))
				.andExpect(view().name("providers/createOrUpdateProviderForm"));
	}
	
	
	
	@WithMockUser(username="admin1", authorities= {"admin"})
	@Test
	void testDelete() throws Exception {
		mockMvc.perform(get("/providers/{providerId}/delete",
				2)).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("providers/providersList"));
	}

}
