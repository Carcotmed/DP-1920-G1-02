package org.springframework.samples.petclinic.e2e;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.model.Discount;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.service.DiscountService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.service.ProviderService;
import org.springframework.samples.petclinic.web.DiscountController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class DiscountControllerE2ETests {

	@Autowired
	private DiscountController discountController;

	@Autowired
	private DiscountService discountService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProviderService providerService;

	@Autowired
	private MockMvc mockMvc;
	
	// ========================== Create ===========================

	@WithMockUser(username="admin1", authorities= {"admin"})
	@Test
	void testDiscountInitCreate() throws Exception {
		mockMvc.perform(get("/discounts/new")).andExpect(status().isOk())
				.andExpect(model().attributeExists("providers")).andExpect(model().attributeExists("products"))
				.andExpect(view().name("discounts/editDiscount"));
	}

	@WithMockUser(username="admin1", authorities= {"admin"})
	@Test
	void testDiscountProcessCreateSuccessful() throws Exception {
		mockMvc.perform(post("/discounts/new").with(csrf()).param("percentage", "10.0").param("quantity", "1")
				.param("provider", "1").param("product", "1").param("enabled","true")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/discounts"));
	}

	@WithMockUser(username="admin1", authorities= {"admin"})
	@Test
	void testDiscountProcessCreateFail() throws Exception {
		mockMvc.perform(post("/discounts/new").with(csrf()).param("quantity", "1").param("provider", "1")
				.param("product", "1").param("enabled", "true")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("discount"))
				.andExpect(model().attributeHasFieldErrors("discount", "percentage"))
				.andExpect(view().name("discounts/editDiscount"));
	}
	// ========================== List ===========================

	@WithMockUser(username="admin1", authorities= {"admin"})
	@Test
	void testDiscountList() throws Exception {
		mockMvc.perform(get("/discounts")).andExpect(status().isOk()).andExpect(model().attributeExists("discounts"))
				.andExpect(view().name("discounts/discountsList"));
	}

	// ========================== Delete ===========================

	@WithMockUser(username="admin1", authorities= {"admin"})
	@Test
	void testDiscountDeleteSuccessful() throws Exception {
		mockMvc.perform(get("/discounts/delete/{discountId}", 1)).andExpect(status().isOk())
				.andExpect(view().name("discounts/discountsList"));
	}

	// ========================== Update ===========================

	@WithMockUser(username="admin1", authorities= {"admin"})
	@Test
	void testDiscountInitUpdate() throws Exception {
		mockMvc.perform(get("/discounts/edit/{discountId}", 2)).andExpect(status().isOk())
				.andExpect(model().attributeExists("discount"))
				.andExpect(model().attribute("discount", hasProperty("percentage", is(55.0))))
				.andExpect(model().attribute("discount", hasProperty("quantity", is(50))))
				.andExpect(model().attribute("discount", hasProperty("product", is(this.productService.findProductById(2)))))
				.andExpect(model().attribute("discount", hasProperty("provider", is(this.providerService.findProviderById(1)))))
				.andExpect(model().attribute("discount", hasProperty("enabled", is(true))))
				.andExpect(status().isOk())
				.andExpect(view().name("discounts/editDiscount"));
	}

	@WithMockUser(username="admin1", authorities= {"admin"})
	@Test
	void testDiscountProcessUpdateSuccessful() throws Exception {
		mockMvc.perform(post("/discounts/edit/{discountId}", 3).with(csrf()).param("percentage", "10.0")
		.param("quantity", "10").param("enabled", "true").param("provider", "1").param("product", "1"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/discounts"));
	}
	
	@WithMockUser(username="admin1", authorities= {"admin"})
	@Test
	void testDiscountProcessUpdateFail() throws Exception {
		mockMvc.perform(post("/discounts/edit/{discountId}", 3).with(csrf()).param("percentage", "10.0")
		.param("quantity", "-1").param("enabled", "true").param("provider", "1").param("product", "1"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("discount"))
		.andExpect(model().attributeHasFieldErrors("discount", "quantity"))
		.andExpect(view().name("discounts/editDiscount"));
	}
}
