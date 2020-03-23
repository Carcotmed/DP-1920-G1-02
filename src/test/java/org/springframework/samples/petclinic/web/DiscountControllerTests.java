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
import org.springframework.samples.petclinic.model.Discount;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.service.DiscountService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.service.ProviderService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = DiscountController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class DiscountControllerTests {

	@Autowired
	private DiscountController discountController;

	@MockBean
	private DiscountService discountService;

	@MockBean
	private ProductService productService;

	@MockBean
	private ProviderService providerService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		Provider provider = new Provider();
		provider.setId(99);
		provider.setAddress("pipo");
		provider.setEmail("pipo@gmail.com");
		provider.setName("ProvPrueba");
		provider.setPhone(123456789);

		Product product = new Product();
		product.setId(99);
		product.setAllAvailable(true);
		product.setName("test");
		product.setPrice(20.02);
		product.setProvider(provider);
		product.setQuantity(19);

		Discount discount1 = new Discount();
		discount1.setId(98);
		discount1.setPercentage(10.0);
		discount1.setProduct(product);
		discount1.setProvider(provider);
		discount1.setQuantity(1);

		Discount discount2 = new Discount();
		discount2.setId(99);
		discount2.setPercentage(20.0);
		discount2.setProduct(product);
		discount2.setProvider(provider);
		discount2.setQuantity(2);
				
		given(this.discountService.findDiscounts()).willReturn(Lists.newArrayList(discount1, discount2));
		given(this.providerService.findProviders()).willReturn(Lists.newArrayList(provider));
		given(this.productService.findProducts()).willReturn(Lists.newArrayList(product));
		given(this.discountService.findDiscountById(50)).willReturn(new Discount());

	}

	// ========================== Create ===========================

	@WithMockUser(value = "spring")
	@Test
	void testShowDiscountCreateHtml() throws Exception {
		mockMvc.perform(get("/discounts/new")).andExpect(status().isOk())
				.andExpect(model().attributeExists("providers")).andExpect(model().attributeExists("products"))
				.andExpect(view().name("discounts/editDiscount"));
	}

	// ========================== List ===========================

	@WithMockUser(value = "spring")
	@Test
	void testShowProductListHtml() throws Exception {
		mockMvc.perform(get("/discounts")).andExpect(status().isOk()).andExpect(model().attributeExists("discounts"))
				.andExpect(view().name("discounts/discountsList"));
	}

}
