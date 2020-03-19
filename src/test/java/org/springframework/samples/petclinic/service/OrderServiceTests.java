package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Discount;
import org.springframework.samples.petclinic.model.Order;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

public class OrderServiceTests {

	@Autowired
	protected OrderService orderService;

	Provider provider = new Provider();
	Product product = new Product();
	Discount discount = new Discount();


	@BeforeEach
	void init() {
		provider.setAddress("pipo");
		provider.setEmail("pipo@gmail.com");
		provider.setId(99);
		provider.setName("ProvPrueba");
		provider.setPhone(123456789);

		product.setAllAvailable(true);
		product.setId(99);
		product.setName("test");
		product.setPrice(99.0);
		product.setProvider(provider);
		product.setQuantity(19);

		discount.setId(99);
		discount.setPercentage(10.1);
		discount.setProduct(product);

	}
	
	//=============== Create: [1 - 12] ( 9- | 3+ ) =======================

	
	// 1 +
	@Test
	void shouldInsertDBAndGenerateId() {
		Order order = new Order();
		order.setQuantity(1);
		order.setArrivalDate(LocalDate.of(2020, 01, 01));
		order.setOrderDate(LocalDate.of(2019, 01, 01));
		order.setDiscount(discount);
		order.setProduct(product);
		order.setProvider(provider);
		order.setSent(false);

		orderService.save(order);

		assertThat(orderService.findOrderById(4).equals(order));
	}

	// 2 +
	@Test
	void shouldInsertDBAndGenerateIdWhenQuantityZero() {
		Order order = new Order();
		order.setQuantity(0);
		order.setArrivalDate(LocalDate.of(2020, 01, 01));
		order.setOrderDate(LocalDate.of(2019, 01, 01));
		order.setDiscount(discount);
		order.setProduct(product);
		order.setProvider(provider);
		order.setSent(false);

		orderService.save(order);

		assertThat(orderService.findOrderById(4).equals(order));
	}

	// 3 -
	@Test
	void shouldNotInsertIntoDBWhenNegativeQuantity() {
		Order order = new Order();
		order.setQuantity(-1); // Fail
		order.setArrivalDate(LocalDate.of(2020, 01, 01));
		order.setOrderDate(LocalDate.of(2019, 01, 01));
		order.setDiscount(discount);
		order.setProduct(product);
		order.setProvider(provider);
		order.setSent(false);

		orderService.save(order);

		assertThat(orderService.findOrderById(4) == null);
	}

	// 4 -
		@Test
		void shouldNotInsertIntoDBWhenQuantityNull() {
			Order order = new Order();
			order.setQuantity(null); // Fail
			order.setArrivalDate(LocalDate.of(2020, 01, 01));
			order.setOrderDate(LocalDate.of(2019, 01, 01));
			order.setDiscount(discount);
			order.setProduct(product);
			order.setProvider(provider);
			order.setSent(false);

			orderService.save(order);

			assertThat(orderService.findOrderById(4) == null);
		}

		
	// 5 -
	@Test
	void shouldNotInsertIntoDBWhenArrivalDateIsBeforeOrderDate() {
		Order order = new Order();
		order.setQuantity(1);
		order.setArrivalDate(LocalDate.of(2019, 01, 01)); // Fail
		order.setOrderDate(LocalDate.of(2020, 01, 01));
		order.setDiscount(discount);
		order.setProduct(product);
		order.setProvider(provider);
		order.setSent(false);

		orderService.save(order);

		assertThat(orderService.findOrderById(4) == null);
	}
	
	// 6 -
	@Test
	void shouldNotInsertIntoDBWhenArrivalDateEqualsOrderDate() {
		Order order = new Order();
		order.setQuantity(1);
		order.setArrivalDate(LocalDate.of(2020, 01, 01)); // Fail
		order.setOrderDate(LocalDate.of(2020, 01, 01));
		order.setDiscount(discount);
		order.setProduct(product);
		order.setProvider(provider);
		order.setSent(false);

		orderService.save(order);

		assertThat(orderService.findOrderById(4) == null);
	}
	
	// 7 -
		@Test
		void shouldNotInsertIntoDBWhenArrivalDatelsNull() {
			Order order = new Order();
			order.setQuantity(1);
			order.setArrivalDate(null); // Fail
			order.setOrderDate(LocalDate.of(2020, 01, 01));
			order.setDiscount(discount);
			order.setProduct(product);
			order.setProvider(provider);
			order.setSent(false);

			orderService.save(order);

			assertThat(orderService.findOrderById(4) == null);
		}
		
		// 8 -
		@Test
		void shouldNotInsertIntoDBWhenOrderDatelsNull() {
			Order order = new Order();
			order.setQuantity(1);
			order.setArrivalDate(LocalDate.of(2020, 01, 01));
			order.setOrderDate(null); //Fail
			order.setDiscount(discount);
			order.setProduct(product);
			order.setProvider(provider);
			order.setSent(false);

			orderService.save(order);

			assertThat(orderService.findOrderById(4) == null);
		}
	

	// 9 +
	@Test
	void shouldInsertIntoDBWhenDiscountNull() {
		Order order = new Order();
		order.setQuantity(1);
		order.setArrivalDate(LocalDate.of(2020, 01, 01));
		order.setOrderDate(LocalDate.of(2019, 01, 01));
		order.setDiscount(null);
		order.setProduct(product);
		order.setProvider(provider);
		order.setSent(true);

		orderService.save(order);

		assertThat(orderService.findOrderById(4).equals(order));
	}

	// 10 -
	@Test
	void shouldNotInsertIntoDBWhenProviderNull() {
		Order order = new Order();
		order.setQuantity(1);
		order.setArrivalDate(LocalDate.of(2020, 01, 01));
		order.setOrderDate(LocalDate.of(2019, 01, 01));
		order.setDiscount(discount);
		order.setProduct(product);
		order.setProvider(null); //Fail
		order.setSent(false);

		orderService.save(order);

		assertThat(orderService.findOrderById(4) == null);
	}
	
	// 11 -
	@Test
	void shouldNotInsertIntoDBWhenProductNull() {
		Order order = new Order();
		order.setQuantity(1);
		order.setArrivalDate(LocalDate.of(2020, 01, 01));
		order.setOrderDate(LocalDate.of(2019, 01, 01));
		order.setDiscount(discount);
		order.setProduct(null); //Fail
		order.setProvider(provider);
		order.setSent(false);

		orderService.save(order);

		assertThat(orderService.findOrderById(4) == null);
	}
	
	// 12 -
		@Test
		void shouldNotInsertIntoDBWhenSentNull() {
			Order order = new Order();
			order.setQuantity(1);
			order.setArrivalDate(LocalDate.of(2020, 01, 01));
			order.setOrderDate(LocalDate.of(2019, 01, 01));
			order.setDiscount(discount);
			order.setProduct(null);
			order.setProvider(provider);
			order.setSent(null); //Fail

			orderService.save(order);

			assertThat(orderService.findOrderById(4) == null);
		}
	
	//=================================================================
	

}
