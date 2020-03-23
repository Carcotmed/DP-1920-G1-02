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
import org.springframework.samples.petclinic.service.ProviderService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.samples.petclinic.service.VisitService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

@WebMvcTest(controllers = ProviderController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class ProviderControllerTests {

	@Autowired
	private ProviderController providerController;

	@MockBean
	private ProviderService providerService;

	@MockBean
	private ProductService productService;

	@Autowired
	private MockMvc mockMvc;

	private Provider provider;

	private static final int TEST_PROVIDER_ID = 99;

	@BeforeEach
	void setup() {

		provider = new Provider();
		provider.setId(TEST_PROVIDER_ID);
		provider.setName("Proveedor");
		provider.setAddress("Address");
		provider.setEmail("email@email.com");
		provider.setPhone("123456789");

		given(this.providerService.findProviderById(TEST_PROVIDER_ID)).willReturn(provider);
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowProviders() throws Exception {
		mockMvc.perform(get("/providers")).andExpect(status().isOk()).andExpect(model().attributeExists("providers"))
				.andExpect(view().name("providers/providersList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/providers/new")).andExpect(status().isOk()).andExpect(model().attributeExists("provider"))
				.andExpect(view().name("providers/createOrUpdateProviderForm"));
	}

	@WithMockUser(value = "spring")
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

	@WithMockUser(value = "spring")
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
	
	
	
	@WithMockUser(value = "spring")
	@Test
	void testInitEditForm() throws Exception {
		mockMvc.perform(get("/providers/{providerId}/edit",
				TEST_PROVIDER_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("provider"))
				.andExpect(model().attribute("provider", hasProperty("name", is("Proveedor"))))
				.andExpect(model().attribute("provider", hasProperty("email", is("email@email.com"))))
				.andExpect(model().attribute("provider", hasProperty("phone", is("123456789"))))
				.andExpect(model().attribute("provider", hasProperty("address", is("Address"))))

				.andExpect(view().name("providers/createOrUpdateProviderForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessEditFormSuccess() throws Exception {
		mockMvc.perform(get("/providers/{providerId}/edit",
				TEST_PROVIDER_ID).with(csrf())
						.param("name", "Provider updated")
						.param("email", "emailUpdated@email.com")
						.param("phone", "987654486")
						.param("address", "Address updated"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("providers/createOrUpdateProviderForm"));
		
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessEditFormHasErrors() throws Exception {
		mockMvc.perform(post("/providers/{providerId}/edit",
				TEST_PROVIDER_ID).with(csrf())
						.param("name", "Je")
						.param("email", "email.com")
						.param("phone", "-987654486")
						.param("address", "Address updated"))
				.andExpect(status().isOk()).andExpect(model().attributeHasErrors("provider"))
				.andExpect(model().attributeHasFieldErrors("provider", "name", "email", "phone"))
				.andExpect(view().name("providers/createOrUpdateProviderForm"));
	}
	
	
	
	@WithMockUser(value = "spring")
	@Test
	void testDelete() throws Exception {
		mockMvc.perform(get("/providers/{providerId}/delete",
				TEST_PROVIDER_ID)).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("providers/providersList"));
	}

}
