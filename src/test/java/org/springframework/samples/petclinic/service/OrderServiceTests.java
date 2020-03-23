package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Collection;

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
import org.springframework.samples.petclinic.util.EntityUtils;
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
		provider.setPhone("123456789");
		this.providerService.saveProvider(provider);

		product.setAllAvailable(true);
		product.setName("test");
		product.setPrice(20.02);
		product.setProvider(provider);
		product.setQuantity(19);
		this.productService.save(product);

		discount.setPercentage(10.1);
		discount.setProvider(provider);
		discount.setQuantity(10);
		discount.setProduct(product);
		this.discountService.save(discount);
	}

	// 1 Save+
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
		assertThat(this.orderService.findAllOrders().size()).isEqualTo(found + 1);
	}

	// 2 Save-
	@Test
	@Transactional
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

	// 3 findAllOrders+
	@Test
	@Transactional
	void shouldFindAllOrders() {
		Collection<Order> orders = this.orderService.findAllOrders();

		Order order1 = EntityUtils.getById(orders, Order.class, 1);
		assertThat(order1.getQuantity()).isEqualTo(3);
		Order order2 = EntityUtils.getById(orders, Order.class, 2);
		assertThat(order2.getQuantity()).isEqualTo(55);
		Order order3 = EntityUtils.getById(orders, Order.class, 3);
		assertThat(order3.getQuantity()).isEqualTo(7);
	}

	// 4 findOrderById+
	@Test
	@Transactional
	public void shouldFindOrderById() {
		Order order1 = this.orderService.findOrderById(1);
		assertThat(order1.getQuantity().equals(3));
		Order order2 = this.orderService.findOrderById(1);
		assertThat(order2.getQuantity().equals(55));
		Order order3 = this.orderService.findOrderById(1);
		assertThat(order3.getQuantity().equals(7));
	}

	// 5 findAllOrdersByDiscountId
	@Test
	@Transactional
	public void shouldFindAllOrdersByDiscountId() {
		Collection<Order> orders = this.orderService.findAllOrdersByDiscountId(1); 
		assertThat(orders.size()).isEqualTo(1);
		Order order1 = EntityUtils.getById(orders, Order.class, 1);
		assertThat(order1.getQuantity()).isEqualTo(3);
		
	}

}
