package org.springframework.samples.petclinic.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.service.DiscountService;
import org.springframework.samples.petclinic.service.OrderService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.service.ProviderService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=OrderController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class OrderControllerTests {

	
	@Autowired
	private OrderController orderController;
	
	@MockBean
	private OrderService orderService;
	
	@MockBean
	private ProductService productService;
	
	@MockBean
	private ProviderService providerService;
	
	@MockBean
	private DiscountService discountService;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	// ========================== Create ===========================
	
		@WithMockUser(value = "spring")
	    @Test
	    void testShowOrderCreateHtml() throws Exception{
			mockMvc.perform(get("/orders/new")).andExpect(status().isOk())
			.andExpect(model().attributeExists("providers"))
			.andExpect(model().attributeExists("discounts"))
			.andExpect(model().attributeExists("products"))
			.andExpect(view().name("orders/editOrder"));
		}
}
