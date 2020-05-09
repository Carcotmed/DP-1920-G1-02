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

import java.time.LocalDate;

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
import org.springframework.samples.petclinic.model.Order;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Provider;
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
	
	private Provider provider = new Provider();
	private Product product = new Product();
	private Discount discount = new Discount();
	
	Order order1 = new Order();
	Order order2 = new Order();



	
	@BeforeEach
	void setup() {
		provider.setId(99);
		provider.setAddress("pipo");
		provider.setEmail("pipo@gmail.com");
		provider.setName("ProvPrueba");
		provider.setPhone("123456789");

		product.setId(99);
		product.setEnabled(true);
		product.setAllAvailable(true);
		product.setName("test");
		product.setPrice(20.02);
		product.setProvider(provider);
		product.setQuantity(19);

		discount.setId(99);
		discount.setPercentage(10.0);
		discount.setProduct(product);
		discount.setProvider(provider);
		discount.setQuantity(1);
		discount.setEnabled(true);

		order1.setArrivalDate(null);
		order1.setDiscount(discount);
		order1.setId(98);
		order1.setOrderDate(LocalDate.of(2019, 2, 1));
		order1.setProduct(product);
		order1.setProvider(provider);
		order1.setQuantity(1);
		order1.setSent(false);
		
		order2.setArrivalDate(null);
		order2.setDiscount(discount);
		order2.setId(99);
		order2.setOrderDate(LocalDate.of(2019, 3, 1));
		order2.setProduct(product);
		order2.setProvider(provider);
		order2.setQuantity(1);
		order2.setSent(false);
		
		
		given(this.discountService.findDiscountById(99)).willReturn(discount);
		given(this.productService.findProductById(99)).willReturn(product);
		given(this.providerService.findProviderById(99)).willReturn(provider);
		
		given(this.orderService.findOrderById(99)).willReturn(order2);
		given(this.orderService.findOrderById(98)).willReturn(order1);

		
		given(this.orderService.findAllOrders()).willReturn(Lists.newArrayList(order1, order2));
		given(this.providerService.findProviders()).willReturn(Lists.newArrayList(provider));
		given(this.discountService.findDiscounts()).willReturn(Lists.newArrayList(discount));
		given(this.productService.findProducts()).willReturn(Lists.newArrayList(product));

		given(this.productService.findAllByProviderId(99)).willReturn(Lists.newArrayList(product));
		given(this.discountService.findAllByProductId(99)).willReturn(Lists.newArrayList(discount));
		given(this.discountService.findAllByProviderId(99)).willReturn(Lists.newArrayList(discount));


	}
	
	
		
		// ========================== Create ===========================

		@WithMockUser(value = "spring")
		@Test
		void testOrderInitCreate() throws Exception {
			mockMvc.perform(get("/orders/new")).andExpect(status().isOk())
					.andExpect(model().attributeExists("providers")).andExpect(model().attributeExists("products"))
					.andExpect(model().attributeExists("discounts")).andExpect(view().name("orders/editOrder"));
		}

		@WithMockUser(value = "spring")
		@Test
		void testOrderProcessCreateSuccessful() throws Exception {
			mockMvc.perform(post("/orders/new").with(csrf()).requestAttr("orderDate", LocalDate.of(2020, 2, 1))
					.requestAttr("arrivalDate", LocalDate.of(2022, 2, 1)).param("quantity", "1").param("sent", "true")
					.param("provider", "99").param("product", "99").param("discount", "99"))
					.andExpect(status().is2xxSuccessful())
					.andExpect(view().name("orders/editOrder"));
		}

		@WithMockUser(value = "spring")
		@Test
		void testOrderProcessCreateFail() throws Exception {
			mockMvc.perform(post("/orders/new").with(csrf()).requestAttr("orderDate", LocalDate.of(2020, 2, 1))
					.requestAttr("arrivalDate", LocalDate.of(2022, 2, 1)).param("quantity", "1").param("sent", "true")
					.param("provider", "99").param("product", "99")).andExpect(status().isOk())
					.andExpect(model().attributeHasErrors("order"))
					.andExpect(model().attributeHasFieldErrors("order", "discount"))
					.andExpect(view().name("orders/editOrder"));
		}
		
		
		// ========================== List ===========================

		@WithMockUser(value = "spring")
		@Test
		void testShowOrderListHtml() throws Exception {
			given(this.orderService.findAllOrders()).willReturn(Lists.newArrayList( new Order()));
			
			mockMvc.perform(get("/orders")).andExpect(status().isOk()).andExpect(model().attributeExists("orders"))
					.andExpect(view().name("orders/ordersList"));
		}
		

		// ========================== Delete ===========================

		@WithMockUser(value = "spring")
		@Test
		void testOrderDeleteSuccessful() throws Exception {	
			given(this.orderService.findOrderById(98)).willReturn(order1);
			
			mockMvc.perform(get("/orders/delete/{orderId}", 98)).andExpect(status().isOk())
					.andExpect(view().name("orders/ordersList"));
		}

		// ========================== Update ===========================

		@WithMockUser(value = "spring")
		@Test
		void testOrderInitUpdate() throws Exception {
			mockMvc.perform(get("/orders/edit/{orderId}", 98)).andExpect(status().isOk())
					.andExpect(model().attributeExists("order"))
					.andExpect(model().attribute("order", hasProperty("orderDate", is(LocalDate.of(2019, 2, 1)))))
					.andExpect(model().attribute("order", hasProperty("quantity", is(1))))
					.andExpect(model().attribute("order", hasProperty("product", is(product))))
					.andExpect(model().attribute("order", hasProperty("provider", is(provider))))
					.andExpect(model().attribute("order", hasProperty("discount", is(discount))))
					.andExpect(model().attribute("order", hasProperty("sent", is(false))))
					.andExpect(status().isOk())
					.andExpect(view().name("orders/editOrder"));
		}

		@WithMockUser(value = "spring")
		@Test
		void testOrderProcessUpdateSuccessful() throws Exception {
			mockMvc.perform(post("/orders/edit/{orderId}", 98).with(csrf()).param("orderDate", "LocalDate.of(2019, 2, 1)")
			.param("quantity", "10").param("provider", "99").param("product", "99").param("discount", "99")
			.param("sent", "false").param("arrivalDate", "null")).andExpect(status().is2xxSuccessful())
			.andExpect(view().name("orders/editOrder"));
		}
		
		@WithMockUser(value = "spring")
		@Test
		void testOrderProcessUpdateFail() throws Exception {
			mockMvc.perform(post("/orders/edit/{orderId}", 98).with(csrf()).param("orderDate", "LocalDate.of(2019, 2, 1)")
					.param("quantity", "-1").param("provider", "99").param("product", "99").param("discount", "99")
					.param("sent", "false").param("arrivalDate", "null")).andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("order"))
			.andExpect(model().attributeHasFieldErrors("order", "quantity"))
			.andExpect(view().name("orders/editOrder"));
		}
}
