package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.petclinic.model.Discount;
import org.springframework.samples.petclinic.model.Order;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

public class OrderServiceTests {

	@Autowired
	protected OrderService orderService;
	
	@Autowired
	protected ProviderService providerService;
	
	@Autowired
	protected ProductService productService;
	
	@Autowired
	protected DiscountService discountService;

	Provider provider = new Provider();
	Product product = new Product();
	Discount discount = new Discount();


	@BeforeEach
	void init() {
		provider.setAddress("pipo");
		provider.setEmail("pipo@gmail.com");
		provider.setName("ProvPrueba");
		provider.setPhone(123456789);
		this.providerService.saveProvider(provider);

		product.setAllAvailable(true);
		product.setName("test");
		product.setProvider(provider);
		product.setQuantity(19);
		this.productService.save(product);

		discount.setPercentage(10.1);
		discount.setProduct(product);
		this.discountService.save(discount);
	}
	
	//=============== Create: [1 - 12] ( 9- | 3+ ) =======================

	
	// 1 +
	@Test
	@Transactional
	void shouldInsertDBAndGenerateId() {
		Integer found = this.orderService.findAllOrders().size();
		Order order = new Order();
		order.setQuantity(1);
		order.setArrivalDate(LocalDate.of(2020, 01, 01));
		order.setOrderDate(LocalDate.of(2019, 01, 01));
		order.setDiscount(discount);
		order.setProduct(product);
		order.setProvider(provider);
		order.setSent(false);

		orderService.save(order);

		assertThat(order.getId().longValue()).isNotEqualTo(0);
		assertThat(this.orderService.findAllOrders().size()).isEqualTo(found+1);
	}

	// 2 +
	@Test
	void shouldInsertDBAndGenerateIdWhenQuantityZero() {
		Integer found = this.orderService.findAllOrders().size();
		Order order = new Order();
		order.setQuantity(0);
		order.setArrivalDate(LocalDate.of(2020, 01, 01));
		order.setOrderDate(LocalDate.of(2019, 01, 01));
		order.setDiscount(discount);
		order.setProduct(product);
		order.setProvider(provider);
		order.setSent(false);

		orderService.save(order);

		assertThat(order.getId().longValue()).isNotEqualTo(0);
		assertThat(this.orderService.findAllOrders().size()).isEqualTo(found+1);

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

		assertThrows(ConstraintViolationException.class, () -> orderService.save(order));
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

			assertThrows(ConstraintViolationException.class, () -> orderService.save(order));

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

		assertThrows(ConstraintViolationException.class, () -> orderService.save(order));
	
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

		assertThrows(ConstraintViolationException.class, () -> orderService.save(order));

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

			assertThrows(ConstraintViolationException.class, () -> orderService.save(order));
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

			assertThrows(ConstraintViolationException.class, () -> orderService.save(order));
		}
	

	// 9 +
	@Test
	void shouldInsertIntoDBWhenDiscountNull() {
		Integer found = this.orderService.findAllOrders().size();
		Order order = new Order();
		order.setQuantity(1);
		order.setArrivalDate(LocalDate.of(2020, 01, 01));
		order.setOrderDate(LocalDate.of(2019, 01, 01));
		order.setDiscount(null);
		order.setProduct(product);
		order.setProvider(provider);
		order.setSent(true);

		orderService.save(order);

		assertThat(order.getId().longValue()).isNotEqualTo(0);
		assertThat(this.orderService.findAllOrders().size()).isEqualTo(found+1);

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


		assertThrows(DataIntegrityViolationException.class, () -> orderService.save(order));
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


		assertThrows(DataIntegrityViolationException.class, () -> orderService.save(order));
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

			assertThrows(ConstraintViolationException.class, () -> orderService.save(order));
		}
	
	//=================================================================
	

}
