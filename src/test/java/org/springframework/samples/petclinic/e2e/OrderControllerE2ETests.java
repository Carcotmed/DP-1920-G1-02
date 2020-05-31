package org.springframework.samples.petclinic.e2e;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.service.DiscountService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.service.ProviderService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
@TestPropertySource(locations = "classpath:application-mysql.properties") //Decoment to try the MySql tests

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class OrderControllerE2ETests {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProviderService providerService;

	@Autowired
	private DiscountService discountService;

	@Autowired
	private MockMvc mockMvc;

	// ========================== Create ===========================

	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testOrderInitCreate() throws Exception {
		mockMvc.perform(get("/orders/new")).andExpect(status().isOk()).andExpect(model().attributeExists("providers"))
				.andExpect(model().attributeExists("products")).andExpect(model().attributeExists("discounts"))
				.andExpect(view().name("orders/editOrder"));
	}

	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testOrderProcessCreateSuccessful() throws Exception {
		mockMvc.perform(post("/orders/new").with(csrf()).requestAttr("orderDate", LocalDate.of(2020, 2, 1))
				.requestAttr("arrivalDate", LocalDate.of(2022, 2, 1)).param("quantity", "1").param("sent", "true")
				.param("provider", "1").param("product", "1").param("discount", "1"))
				.andExpect(status().is2xxSuccessful()).andExpect(view().name("orders/editOrder"));
	}

	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testOrderProcessCreateFail() throws Exception {
		mockMvc.perform(post("/orders/new").with(csrf()).requestAttr("orderDate", LocalDate.of(2020, 2, 1))
				.requestAttr("arrivalDate", LocalDate.of(2022, 2, 1)).param("quantity", "-1").param("sent", "true")
				.param("provider", "1").param("product", "1").param("discount", "1")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("order"))
				.andExpect(model().attributeHasFieldErrors("order", "quantity"))
				.andExpect(view().name("orders/editOrder"));
	}

	// ========================== List ===========================

	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testShowOrderListHtml() throws Exception {

		mockMvc.perform(get("/orders")).andExpect(status().isOk()).andExpect(model().attributeExists("orders"))
				.andExpect(view().name("orders/ordersList"));
	}

	// ========================== Delete ===========================

	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testOrderDeleteSuccessful() throws Exception {

		mockMvc.perform(get("/orders/delete/{orderId}", 1)).andExpect(status().isOk())
				.andExpect(view().name("orders/ordersList"));
	}

	// ========================== Update ===========================

	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testOrderInitUpdate() throws Exception {
		mockMvc.perform(get("/orders/edit/{orderId}", 2)).andExpect(status().isOk())
				.andExpect(model().attributeExists("order"))
				.andExpect(model().attribute("order", hasProperty("orderDate", is(LocalDate.of(2013, 1, 31)))))
				.andExpect(model().attribute("order", hasProperty("quantity", is(55))))
				.andExpect(model().attribute("order", hasProperty("product", is(this.productService.findProductWithProviderById(2)))))
				.andExpect(model().attribute("order", hasProperty("provider", is(this.providerService.findProviderById(2)))))
				.andExpect(model().attribute("order", hasProperty("discount", is(this.discountService.findDiscountById(2)))))
				.andExpect(model().attribute("order", hasProperty("sent", is(false)))).andExpect(status().isOk())
				.andExpect(view().name("orders/editOrder"));
	}

	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testOrderProcessUpdateSuccessful() throws Exception {
		mockMvc.perform(post("/orders/edit/{orderId}", 98).with(csrf()).requestAttr("orderDate", LocalDate.of(2019, 2, 1))
				.param("quantity", "10").param("provider", "2").param("product", "2").param("discount", "2")
				.param("sent", "false")).andExpect(status().is2xxSuccessful()) //no arrival date for null
				.andExpect(view().name("orders/editOrder"));
	}

	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testOrderProcessUpdateFail() throws Exception {
		mockMvc.perform(post("/orders/edit/{orderId}", 98).with(csrf()).requestAttr("orderDate", LocalDate.of(2019, 2, 1))
				.param("quantity", "-10").param("provider", "2").param("product", "2").param("discount", "2")
				.param("sent", "false")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("order"))
				.andExpect(model().attributeHasFieldErrors("order", "quantity"))
				.andExpect(view().name("orders/editOrder"));
	}
}
