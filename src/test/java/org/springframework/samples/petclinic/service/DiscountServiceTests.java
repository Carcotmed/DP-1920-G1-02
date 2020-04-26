package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.samples.petclinic.model.Discount;
import org.springframework.samples.petclinic.model.Order;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

public class DiscountServiceTests {

	@Autowired
	public DiscountService discountService;

	@Autowired
	public ProductService productService;

	@Autowired
	public ProviderService providerService;
	
	@Autowired
	public OrderService orderService;

	Product product = new Product();
	Provider provider = new Provider();

	@BeforeEach
	void init() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

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

	}

	// 1 Save+
	@Test
	@Transactional
	public void shouldInsertDBAndGenerateId() {
		Integer found = this.discountService.findDiscounts().size();
		Discount discount = new Discount();

		discount.setPercentage(80.0);
		discount.setProduct(product);
		discount.setProvider(provider);
		discount.setQuantity(1);
		discount.setEnabled(true);
		
		discountService.save(discount);

		assertThat(discount.getId().longValue()).isNotEqualTo(0);
		assertThat(this.discountService.findDiscounts().size()).isEqualTo(found + 1);
	}

	// 2 Save-
	@Test
	@Transactional
	public void shouldNotInsertDBWhenQuantityNull() {
		Discount discount = new Discount();

		discount.setPercentage(80.0);
		discount.setProduct(product);
		discount.setProvider(provider);
		discount.setQuantity(null);
		discount.setEnabled(true);

		assertThrows(ConstraintViolationException.class, () -> discountService.save(discount));

	}

	// 3 findDiscounts+
	@Test
	public void shouldFindDiscounts() {
		Collection<Discount> discounts = this.discountService.findDiscounts();

		Discount discount1 = EntityUtils.getById(discounts, Discount.class, 1);
		assertThat(discount1.getPercentage()).isEqualTo(45.0);
		Discount discount2 = EntityUtils.getById(discounts, Discount.class, 2);
		assertThat(discount2.getPercentage()).isEqualTo(55.0);
		Discount discount3 = EntityUtils.getById(discounts, Discount.class, 3);
		assertThat(discount3.getPercentage()).isEqualTo(65.0);
	}

	// 4 findDiscountByProviderId
	@Test
	public void shouldFindDiscountByProviderId() {
		Collection<Discount> discounts = this.discountService.findAllByProviderId(3);
		assertThat(discounts.size() == 1);
		assertThat(discounts.stream().map(x -> x.getPercentage()).equals(65.0));
	}

	// 5 findDiscountByProductId
	@Test
	public void shouldFindDiscountByProductId() {
		Collection<Discount> discounts = this.discountService.findAllByProductId(2);
		assertThat(discounts.size() == 1);
		assertThat(discounts.stream().map(x -> x.getPercentage()).equals(55.0));
	}

	// 6 findDiscountById
	@Test
	public void shouldFindDiscountById() {
		Discount discount = this.discountService.findDiscountById(3);
		assertThat(discount.getPercentage().equals(65.0));
	}

	// 7 delete+
	@Test
	public void shouldDeleteDiscount() {
		Collection<Discount> discounts = this.discountService.findDiscounts();
		int found = discounts.size();
		Collection<Order> orders = this.orderService.findAllOrdersByDiscountId(1);
		orders.stream().forEach(x -> this.orderService.deleteOrder(x));
		
		this.discountService.deleteDiscount(this.discountService.findDiscountById(1));
		assertThat(this.discountService.findDiscounts().size()).isEqualTo(found - 1);
	}

	// 8 delete-
	@Test
	public void shouldNotDeleteDiscount() {
		Collection<Discount> discounts = this.discountService.findDiscounts();
		int found = discounts.size();
		Collection<Order> orders = this.orderService.findAllOrdersByDiscountId(50);
		assertTrue(orders.isEmpty());
		assertThrows(InvalidDataAccessApiUsageException.class, () -> this.discountService.deleteDiscount(this.discountService.findDiscountById(50)));
		assertThat(this.discountService.findDiscounts().size()).isEqualTo(found);
	}

  //9 update+
	@Test
	@Transactional
	public void shouldUpdateDiscount() {
		Discount discount = new Discount();

		discount.setPercentage(80.0);
		discount.setProduct(product);
		discount.setProvider(provider);
		discount.setQuantity(1);
		discount.setEnabled(true);


		discountService.save(discount);
		
		Discount discountAct = this.discountService.findDiscounts().stream().filter(x -> x.getPercentage().equals(80.0)).collect(Collectors.toList()).get(0);
		discountAct.setPercentage(30.01);
		
		discountService.save(discountAct);

		assertThat(discountService.findDiscountById(discountAct.getId()).getPercentage()).isEqualTo(30.01);
	}
	
	 //10 update-
		@Test
		@Transactional
		public void shouldNotUpdateDiscount() {
			Discount discount = new Discount();

			discount.setPercentage(80.0);
			discount.setProduct(product);
			discount.setProvider(provider);
			discount.setQuantity(1);
			discount.setEnabled(true);


			this.discountService.save(discount);
			
			Discount discountAct = this.discountService.findDiscounts().stream().filter(x -> x.getPercentage().equals(80.0)).collect(Collectors.toList()).get(0);
			
			discountAct.setPercentage(300.01);
			this.discountService.save(discountAct);
			
			assertThrows(ConstraintViolationException.class, () -> this.discountService.findDiscountById(discountAct.getId()).getPercentage().equals(80.0));
		}



}
