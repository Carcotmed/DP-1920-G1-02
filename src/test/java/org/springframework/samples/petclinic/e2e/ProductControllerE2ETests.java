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
import org.springframework.samples.petclinic.service.ProviderService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@TestPropertySource(locations = "classpath:application-mysql.properties") //Decoment to try the MySql tests
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ProductControllerE2ETests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ProviderService providerService;

	
	// ========================== Create ===========================

	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testProductInitCreate() throws Exception {
		mockMvc.perform(get("/products/new")).andExpect(status().isOk())
				.andExpect(model().attributeExists("providers"))
				.andExpect(view().name("products/editProduct"));
	}

	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testProductProcessCreateSuccessful() throws Exception {
		mockMvc.perform(post("/products/new").with(csrf()).param("price", "10.0").param("quantity", "1")
				.param("provider", "1").param("enabled", "true").param("allAvailable", "true")
				.param("name", "nombre")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/products"));
	}

	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testProductProcessCreateFail() throws Exception {
		mockMvc.perform(post("/products/new").with(csrf()).param("price", "10.0").param("quantity", "1")
				.param("provider", "1").param("allAvailable", "true").param("name", "nombre"))
				.andExpect(status().isOk()).andExpect(model().attributeHasErrors("product"))
				.andExpect(model().attributeHasFieldErrors("product", "enabled"))
				.andExpect(view().name("products/editProduct"));
	}
	// ========================== List ===========================

	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testProductList() throws Exception {
		
		mockMvc.perform(get("/products")).andExpect(status().isOk()).andExpect(model().attributeExists("products"))
				.andExpect(view().name("products/productsList"));
	}

	// ========================== Delete ===========================

	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testProductDeleteSuccessful() throws Exception {
		mockMvc.perform(get("/products/delete/{productId}", 2)).andExpect(status().isOk())
				.andExpect(view().name("products/productsList"));
	}

	// ========================== Update ===========================

	/*
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testProductInitUpdate() throws Exception {
		mockMvc.perform(get("/products/edit/{productId}", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("product"))
				.andExpect(model().attribute("product", hasProperty("price", is(20.5))))
				.andExpect(model().attribute("product", hasProperty("quantity", is(5))))
				.andExpect(model().attribute("product", hasProperty("enabled", is(true))))
				.andExpect(model().attribute("product", hasProperty("name", is("Pomadita"))))
				.andExpect(model().attribute("product", hasProperty("provider", is(this.providerService.findProviderById(1)))))
				.andExpect(model().attribute("product", hasProperty("allAvailable", is(true))))
				.andExpect(status().isOk()).andExpect(view().name("products/editProduct"));
	}
*/
	
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testProductProcessUpdateSuccessful() throws Exception {
		mockMvc.perform(post("/products/edit/{productId}", 3).with(csrf()).param("price", "1.1")
				.param("quantity", "10").param("provider", "1").param("enabled", "true").param("name", "producto3Upt")
				.param("allAvailable", "true")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/products"));
	}

	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testProductProcessUpdateFail() throws Exception {
		mockMvc.perform(post("/products/edit/{productId}", 1).with(csrf()).param("price", "1.1")
				.param("quantity", "10").param("provider", "99")
				.param("allAvailable", "true").param("allAvailable", "true")
				.param("enabled", "true")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("product"))
				.andExpect(model().attributeHasFieldErrors("product", "name"))
				.andExpect(view().name("products/editProduct"));
	}

}
