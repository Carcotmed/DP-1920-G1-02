package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.samples.petclinic.model.Discount;
import org.springframework.samples.petclinic.model.Order;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
class OrderServiceTests {

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
		product.setEnabled(true);
		this.productService.save(product);

		discount.setPercentage(10.1);
		discount.setProvider(provider);
		discount.setQuantity(10);
		discount.setProduct(product);
		discount.setEnabled(true);
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
		assertThat(orders.size()).isEqualTo(3);

		List<Order> list = new ArrayList<>();
		list.addAll(orders);

		Order order1 = list.get(0);
		assertThat(order1.getQuantity()).isEqualTo(3);
		Order order2 = list.get(1);
		assertThat(order2.getQuantity()).isEqualTo(55);
		Order order3 = list.get(2);
		assertThat(order3.getQuantity()).isEqualTo(7);
	}

	// 4 findOrderById+
	@Test
	@Transactional
	void shouldFindOrderById() {
		Order order1 = this.orderService.findOrderById(1);
		assertThat(order1.getQuantity()).isEqualTo(3);
		Order order2 = this.orderService.findOrderById(2);
		assertThat(order2.getQuantity()).isEqualTo(55);
		Order order3 = this.orderService.findOrderById(3);
		assertThat(order3.getQuantity()).isEqualTo(7);
	}

	// 5 findAllOrdersByDiscountId+
	@Test
	@Transactional
	void shouldFindAllOrdersByDiscountId() {
		Collection<Order> orders = this.orderService.findAllOrdersByDiscountId(1);
		assertThat(orders.size()).isEqualTo(1);
		Order order1 = EntityUtils.getById(orders, Order.class, 1);
		assertThat(order1.getQuantity()).isEqualTo(3);

	}

	// 6 delete+
	@Test
	void shouldDeleteOrder() {

		Collection<Order> orders = this.orderService.findAllOrders();
		int found = orders.size();
		this.orderService.deleteOrder(this.orderService.findOrderById(1));
		assertThat(this.orderService.findAllOrders().size()).isEqualTo(found - 1);
	}

	// 7 delete -
	@Test
	void shouldNotDeleteOrder() {

		Collection<Order> orders = this.orderService.findAllOrders();
		int found = orders.size();
		assertThrows(InvalidDataAccessApiUsageException.class,
				() -> this.orderService.deleteOrder(this.orderService.findOrderById(99)));
		assertThat(this.orderService.findAllOrders().size()).isEqualTo(found);
	}

	// 8 delete -
	@Test
	void shouldNotDeleteWhenSent() {

		Collection<Order> orders = this.orderService.findAllOrders();
		int found = orders.size();
		Order order = this.orderService.findOrderById(99);
		assertThrows(InvalidDataAccessApiUsageException.class, () -> this.orderService.deleteOrder(order));
		assertThat(this.orderService.findAllOrders().size()).isEqualTo(found);
	}

	// 9 update +
	@Test
	@Transactional
	void shouldUpdateOrder() {

		Order order = new Order();

		order.setQuantity(99);
		order.setSent(false);
		order.setOrderDate(LocalDate.of(2013, 1, 1));
		order.setArrivalDate(null);
		order.setDiscount(discount);
		order.setProduct(product);
		order.setProvider(provider);

		orderService.save(order);

		Order orderAct = this.orderService.findAllOrders().stream().filter(x -> x.getQuantity().equals(99))
				.collect(Collectors.toList()).get(0);
		orderAct.setQuantity(98);

		orderService.save(orderAct);

		assertThat(orderService.findOrderById(orderAct.getId()).getQuantity()).isEqualTo(98);
	}

	// 10 update -
	@Test
	@Transactional
	void shouldNotUpdateOrder() {

		Order order = new Order();

		order.setQuantity(99);
		order.setSent(false);
		order.setOrderDate(LocalDate.of(2013, 1, 1));
		order.setArrivalDate(null);
		order.setDiscount(discount);
		order.setProduct(product);
		order.setProvider(provider);

		orderService.save(order);

		Order orderAct = this.orderService.findAllOrders().stream().filter(x -> x.getQuantity().equals(99))
				.collect(Collectors.toList()).get(0);
		orderAct.setQuantity(0);

		orderService.save(orderAct);

		assertThrows(ConstraintViolationException.class,
				() -> this.orderService.findOrderById(orderAct.getId()).getQuantity()).equals(99);
	}

	// 11 update
	@Test
	@Transactional
	void shouldNotUpdateOrderWithArrivalDate() {

		Order order = new Order();

		order.setQuantity(99);
		order.setSent(false);
		order.setOrderDate(LocalDate.of(2013, 1, 1));
		order.setArrivalDate(LocalDate.of(2013, 2, 1));
		order.setDiscount(discount);
		order.setProduct(product);
		order.setProvider(provider);

		orderService.save(order);

		Order orderAct = this.orderService.findAllOrders().stream().filter(x -> x.getQuantity().equals(99))
				.collect(Collectors.toList()).get(0);
		orderAct.setQuantity(98);

		orderService.save(orderAct);

		assertThat(orderService.findOrderById(orderAct.getId()).getQuantity()).isEqualTo(98);
	}

}
