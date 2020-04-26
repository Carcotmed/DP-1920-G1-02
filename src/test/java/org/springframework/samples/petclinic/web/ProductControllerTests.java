package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.service.ProviderService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ProductController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class ProductControllerTests {

	@Autowired
	private ProductController productController;

	@MockBean
	private ProductService productService;

	@MockBean
	private ProviderService providerService;

	@Autowired
	private MockMvc mockMvc;

	private Provider provider = new Provider();
	private Product product1 = new Product();
	private Product product2 = new Product();


	@BeforeEach
	void setup() {

		provider.setAddress("Calle pipo numero 1");
		provider.setEmail("pipo@gmail.com");
		provider.setId(99);
		provider.setName("Pipo");
		provider.setPhone("123456789");

		product1.setName("producto1");
		product1.setId(98);
		product1.setPrice(1.1);
		product1.setQuantity(1);
		product1.setEnabled(true);
		product1.setProvider(provider);

		product2.setName("producto2");
		product2.setId(99);
		product2.setPrice(2.2);
		product1.setQuantity(2);
		product2.setEnabled(true);
		product2.setProvider(provider);

		given(this.providerService.findProviders()).willReturn(Lists.newArrayList(provider));

	}


	// ========================== Create ===========================

	@WithMockUser(value = "spring")
	@Test
	void testProductInitCreate() throws Exception {
		mockMvc.perform(get("/products/new")).andExpect(status().isOk())
				.andExpect(model().attributeExists("providers"))
				.andExpect(view().name("products/editProduct"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProductProcessCreateSuccessful() throws Exception {
		mockMvc.perform(post("/products/new").with(csrf()).param("price", "10.0").param("quantity", "1")
				.param("provider", "99").param("enabled", "true").param("name", "nombre")).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("products/editProduct"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProductProcessCreateFail() throws Exception {
		mockMvc.perform(post("/products/new").with(csrf()).param("price", "10.0").param("quantity", "1")
				.param("provider", "99").param("name", "nombre")).andExpect(status().isOk()).andExpect(model().attributeHasErrors("product"))
				.andExpect(model().attributeHasFieldErrors("product", "enabled"))
				.andExpect(view().name("products/editProduct"));
	}
	// ========================== List ===========================

	@WithMockUser(value = "spring")
	@Test
	void testProductList() throws Exception {
		given(this.productService.findProducts()).willReturn(Lists.newArrayList(product1, product2));
		
		mockMvc.perform(get("/products")).andExpect(status().isOk()).andExpect(model().attributeExists("products"))
				.andExpect(view().name("products/productsList"));
	}

	// ========================== Delete ===========================

	@WithMockUser(value = "spring")
	@Test
	void testProductDeleteSuccessful() throws Exception {
		mockMvc.perform(get("/products/delete/{productId}", 98)).andExpect(status().isOk())
				.andExpect(view().name("products/productsList"));
	}

	// ========================== Update ===========================

	@WithMockUser(value = "spring")
	@Test
	void testProductInitUpdate() throws Exception {
		mockMvc.perform(get("/products/edit/{productId}", 98)).andExpect(status().isOk())
				.andExpect(model().attributeExists("product"))
				.andExpect(model().attribute("product", hasProperty("price", is(1.1))))
				.andExpect(model().attribute("product", hasProperty("quantity", is(1))))
				.andExpect(model().attribute("product", hasProperty("enabled", is(true))))
				.andExpect(model().attribute("product", hasProperty("name", is("producto1"))))
				.andExpect(model().attribute("product", hasProperty("provider", is(provider))))
				.andExpect(status().isOk()).andExpect(view().name("discounts/editDiscount"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProductProcessUpdateSuccessful() throws Exception {
		mockMvc.perform(post("/products/edit/{productId}", 98).with(csrf()).param("price", "1.1")
				.param("quantity", "10").param("provider", "99").param("enabled", "true").param("name", "producto1Upt"))
				.andExpect(status().is2xxSuccessful()).andExpect(view().name("products/editProduct"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProductProcessUpdateFail() throws Exception {
		mockMvc.perform(post("/products/edit/{productId}", 98).with(csrf()).param("price", "1.1")
				.param("quantity", "10").param("provider", "99").param("enabled", "true")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("product"))
				.andExpect(model().attributeHasFieldErrors("product", "name"))
				.andExpect(view().name("products/editProduct"));
	}

}
