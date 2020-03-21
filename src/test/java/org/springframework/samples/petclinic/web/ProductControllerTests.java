package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
	
	@BeforeEach
	void setup() {

		Product product1 = new Product();
		product1.setName("producto1");
		product1.setId(98);
		product1.setPrice(1.1);
		product1.setQuantity(1);
		
		Product product2 = new Product();
		product1.setName("producto2");
		product1.setId(99);
		product1.setPrice(2.2);
		product1.setQuantity(2);
		
		Provider provider = new Provider();
		provider.setAddress("Calle pipo numero 1");
		provider.setEmail("pipo@gmail.com");
		provider.setId(99);
		provider.setName("Pipo");
		provider.setPhone(123456789);
		
		product1.setProvider(provider);

		given(this.productService.findProducts()).willReturn(Lists.newArrayList(product1, product2));

	}

	// ========================== Create ===========================

	@WithMockUser(value = "spring")
	@Test
	void testShowProductCreateHtml() throws Exception {
		mockMvc.perform(get("/products/new")).andExpect(status().isOk()).andExpect(model().attributeExists("providers"))
				.andExpect(view().name("products/editProduct"));
	}

	// ========================== List ===========================

	@WithMockUser(value = "spring")
	@Test
	void testShowProductListHtml() throws Exception {
		mockMvc.perform(get("/products")).andExpect(status().isOk()).andExpect(model().attributeExists("products"))
				.andExpect(view().name("products/productsList"));
	}

}
