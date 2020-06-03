package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

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

class DiscountControllerTests {

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

	private Provider provider = new Provider();
	private Product product = new Product();

	@BeforeEach
	void setup() {
		provider.setId(99);
		provider.setAddress("pipo");
		provider.setEmail("pipo@gmail.com");
		provider.setName("ProvPrueba");
		provider.setPhone("123456789");

		product.setId(99);
		product.setAllAvailable(true);
		product.setName("test");
		product.setPrice(20.02);
		product.setProvider(provider);
		product.setQuantity(19);
		product.setEnabled(true);

		Discount discount1 = new Discount();
		discount1.setId(98);
		discount1.setPercentage(10.0);
		discount1.setProduct(product);
		discount1.setProvider(provider);
		discount1.setQuantity(1);
		discount1.setEnabled(true);

		Discount discount2 = new Discount();
		discount2.setId(99);
		discount2.setPercentage(20.0);
		discount2.setProduct(product);
		discount2.setProvider(provider);
		discount2.setQuantity(2);
		discount2.setEnabled(true);

		given(this.discountService.findDiscounts()).willReturn(Lists.newArrayList(discount1, discount2));
		given(this.providerService.findProviders()).willReturn(Lists.newArrayList(provider));
		given(this.productService.findProducts()).willReturn(Lists.newArrayList(product));
		given(this.discountService.findDiscountById(98)).willReturn(discount1);

	}

	// ========================== Create ===========================

	@WithMockUser(value = "spring")
	@Test
	void testDiscountInitCreate() throws Exception {
		mockMvc.perform(get("/discounts/new")).andExpect(status().isOk())
				.andExpect(model().attributeExists("providers")).andExpect(model().attributeExists("products"))
				.andExpect(view().name("discounts/editDiscount"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testDiscountProcessCreateSuccessful() throws Exception {
		mockMvc.perform(post("/discounts/new").with(csrf()).param("percentage", "10.0").param("quantity", "1")
				.param("provider", "99").param("product", "99")).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("discounts/editDiscount"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testDiscountProcessCreateFail() throws Exception {
		mockMvc.perform(post("/discounts/new").with(csrf()).param("quantity", "1").param("provider", "99")
				.param("product", "99")).andExpect(status().isOk()).andExpect(model().attributeHasErrors("discount"))
				.andExpect(model().attributeHasFieldErrors("discount", "percentage"))
				.andExpect(view().name("discounts/editDiscount"));
	}
	// ========================== List ===========================

	@WithMockUser(value = "spring")
	@Test
	void testDiscountList() throws Exception {
		mockMvc.perform(get("/discounts")).andExpect(status().isOk()).andExpect(model().attributeExists("discounts"))
				.andExpect(view().name("discounts/discountsList"));
	}

	// ========================== Delete ===========================

	@WithMockUser(value = "spring")
	@Test
	void testDiscountDeleteSuccessful() throws Exception {
		mockMvc.perform(get("/discounts/delete/{discountId}", 98)).andExpect(status().isOk())
				.andExpect(view().name("discounts/discountsList"));
	}

	// ========================== Update ===========================

	@WithMockUser(value = "spring")
	@Test
	void testDiscountInitUpdate() throws Exception {
		mockMvc.perform(get("/discounts/edit/{discountId}", 98)).andExpect(status().isOk())
				.andExpect(model().attributeExists("discount"))
				.andExpect(model().attribute("discount", hasProperty("percentage", is(10.0))))
				.andExpect(model().attribute("discount", hasProperty("quantity", is(1))))
				.andExpect(model().attribute("discount", hasProperty("product", is(product))))
				.andExpect(model().attribute("discount", hasProperty("provider", is(provider))))
				.andExpect(status().isOk()).andExpect(view().name("discounts/editDiscount"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testDiscountProcessUpdateSuccessful() throws Exception {
		mockMvc.perform(post("/discounts/edit/{discountId}", 98).with(csrf()).param("percentage", "10.0")
				.param("quantity", "10").param("provider", "99").param("product", "99"))
				.andExpect(status().is2xxSuccessful()).andExpect(view().name("discounts/editDiscount"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testDiscountProcessUpdateFail() throws Exception {
		mockMvc.perform(post("/discounts/edit/{discountId}", 98).with(csrf()).param("percentage", "10.0")
				.param("quantity", "-1").param("provider", "99").param("product", "99")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("discount"))
				.andExpect(model().attributeHasFieldErrors("discount", "quantity"))
				.andExpect(view().name("discounts/editDiscount"));
	}
}
