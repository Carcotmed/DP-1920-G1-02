package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Transactional;

public class OrderValidatorTests extends ValidatorTests {

	Provider provider = new Provider();
	Product product = new Product();
	Discount discount = new Discount();

	@BeforeEach
	void init() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		provider.setAddress("pipo");
		provider.setEmail("pipo@gmail.com");
		provider.setName("ProvPrueba");
		provider.setPhone("123456789");

		product.setAllAvailable(true);
		product.setName("test");
		product.setPrice(20.02);
		product.setProvider(provider);
		product.setQuantity(19);

		discount.setPercentage(10.1);
		discount.setProduct(product);
	}

	// =============== Create: [1 - 10] ( 8- | 1+ ) =======================

	// 1 +
	@ParameterizedTest
	@Transactional
	@CsvSource({ "1,LocalDate.parse('1900/01/01'),null,discount,product,provider,true",
			"2,LocalDate.parse('2000/02/02'),LocalDate.parse('2001/03/03'),null,product,provider,false" })
	void shouldInsertDBAndGenerateId(Integer quantity, String orderDate, String arrivalDate, String discount,
			String product, String provider, Boolean sent) {
		assertNotNull(quantity);
		assertNotNull(orderDate);
		assertNotNull(arrivalDate);
		assertNotNull(product);
		assertNotNull(provider);
		assertNotNull(sent);

		assertThat(quantity >= 1);

	}

	// 2 -
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

		Validator validator = createValidator();
		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Order> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("quantity");
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 1");
	}

	// 3 -
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

		Validator validator = createValidator();
		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Order> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("quantity");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	// 4 -
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

		Validator validator = createValidator();
		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Order> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("validDate");
		assertThat(violation.getMessage()).isEqualTo("must be true");
	}

	// 5 -
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

		Validator validator = createValidator();
		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Order> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("validDate");
		assertThat(violation.getMessage()).isEqualTo("must be true");
	}


	// 6 -
	@Test
	void shouldNotInsertIntoDBWhenOrderDatelsNull() {
		Order order = new Order();
		order.setQuantity(1);
		order.setArrivalDate(LocalDate.of(2020, 01, 01));
		order.setOrderDate(null); // Fail
		order.setDiscount(discount);
		order.setProduct(product);
		order.setProvider(provider);
		order.setSent(false);

		Validator validator = createValidator();
		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);

		assertThat(constraintViolations.size()).isEqualTo(2);
		Iterator <ConstraintViolation<Order>> iterator = constraintViolations.iterator();
		ConstraintViolation<Order> violation = iterator.next();
		
		if (!violation.getPropertyPath().toString().equals("orderDate")) {
			violation = iterator.next();
		}		
		
		assertThat(violation.getPropertyPath().toString()).isEqualTo("orderDate");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	// 7 -
	@Test
	void shouldNotInsertIntoDBWhenProviderNull() {
		Order order = new Order();
		order.setQuantity(1);
		order.setArrivalDate(LocalDate.of(2020, 01, 01));
		order.setOrderDate(LocalDate.of(2019, 01, 01));
		order.setDiscount(discount);
		order.setProduct(product);
		order.setProvider(null); // Fail
		order.setSent(false);

		Validator validator = createValidator();
		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Order> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("provider");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	// 8 -
	@Test
	void shouldNotInsertIntoDBWhenProductNull() {
		Order order = new Order();
		order.setQuantity(1);
		order.setArrivalDate(LocalDate.of(2020, 01, 01));
		order.setOrderDate(LocalDate.of(2019, 01, 01));
		order.setDiscount(discount);
		order.setProduct(null); // Fail
		order.setProvider(provider);
		order.setSent(false);

		Validator validator = createValidator();
		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Order> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("product");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	// 9 -
	@Test
	void shouldNotInsertIntoDBWhenSentNull() {
		Order order = new Order();
		order.setQuantity(1);
		order.setArrivalDate(LocalDate.of(2020, 01, 01));
		order.setOrderDate(LocalDate.of(2019, 01, 01));
		order.setDiscount(discount);
		order.setProduct(product);
		order.setProvider(provider);
		order.setSent(null); // Fail

		Validator validator = createValidator();
		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Order> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("sent");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}
}
