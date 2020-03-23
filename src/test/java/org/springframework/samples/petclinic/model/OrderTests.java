package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

public class OrderTests extends ValidatorTests{

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
	
	//=============== Create: [1 - 12] ( 9- | 3+ ) =======================

	
		// 1 +
		@Test
		@Transactional
		void shouldInsertDBAndGenerateId() {
			Order order = new Order();
			order.setQuantity(1);
			order.setArrivalDate(LocalDate.of(2020, 01, 01));
			order.setOrderDate(LocalDate.of(2019, 01, 01));
			order.setDiscount(discount);
			order.setProduct(product);
			order.setProvider(provider);
			order.setSent(false);

			Validator validator = createValidator();
			Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
			
			assertThat(constraintViolations.size()).isEqualTo(0);
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

			Validator validator = createValidator();
			Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
			
			assertThat(constraintViolations.size()).isEqualTo(0);

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

			Validator validator = createValidator();
			Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);

			assertThat(constraintViolations.size()).isEqualTo(1);
			ConstraintViolation<Order> violation = constraintViolations.iterator().next();
			assertThat(violation.getPropertyPath().toString()).isEqualTo("quantity");
			assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 0");
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

				Validator validator = createValidator();
				Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);

				assertThat(constraintViolations.size()).isEqualTo(1);
				ConstraintViolation<Order> violation = constraintViolations.iterator().next();
				assertThat(violation.getPropertyPath().toString()).isEqualTo("quantity");
				assertThat(violation.getMessage()).isEqualTo("must not be null");
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

			Validator validator = createValidator();
			Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);

			assertThat(constraintViolations.size()).isEqualTo(1);
			ConstraintViolation<Order> violation = constraintViolations.iterator().next();
			assertThat(violation.getPropertyPath().toString()).isEqualTo("validDate");
			assertThat(violation.getMessage()).isEqualTo("must be true");	
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

			Validator validator = createValidator();
			Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);

			assertThat(constraintViolations.size()).isEqualTo(1);
			ConstraintViolation<Order> violation = constraintViolations.iterator().next();
			assertThat(violation.getPropertyPath().toString()).isEqualTo("validDate");
			assertThat(violation.getMessage()).isEqualTo("must be true");
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

				Validator validator = createValidator();
				Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);

				assertThat(constraintViolations.size()).isEqualTo(2);
				ConstraintViolation<Order> violation = constraintViolations.iterator().next();
				assertThat(violation.getPropertyPath().toString()).isEqualTo("arrivalDate");
				assertThat(violation.getMessage()).isEqualTo("must not be null");			}
			
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

				Validator validator = createValidator();
				Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);

				assertThat(constraintViolations.size()).isEqualTo(2);
				ConstraintViolation<Order> violation = constraintViolations.iterator().next();
				assertThat(violation.getPropertyPath().toString()).isEqualTo("orderDate");
				assertThat(violation.getMessage()).isEqualTo("must not be null");
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

			Validator validator = createValidator();
			Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
			
			assertThat(constraintViolations.size()).isEqualTo(0);

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

			Validator validator = createValidator();
			Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);

			assertThat(constraintViolations.size()).isEqualTo(1);
			ConstraintViolation<Order> violation = constraintViolations.iterator().next();
			assertThat(violation.getPropertyPath().toString()).isEqualTo("provider");
			assertThat(violation.getMessage()).isEqualTo("must not be null");
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

			Validator validator = createValidator();
			Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);

			assertThat(constraintViolations.size()).isEqualTo(1);
			ConstraintViolation<Order> violation = constraintViolations.iterator().next();
			assertThat(violation.getPropertyPath().toString()).isEqualTo("product");
			assertThat(violation.getMessage()).isEqualTo("must not be null");		}
		
		// 12 -
			@Test
			void shouldNotInsertIntoDBWhenSentNull() {
				Order order = new Order();
				order.setQuantity(1);
				order.setArrivalDate(LocalDate.of(2020, 01, 01));
				order.setOrderDate(LocalDate.of(2019, 01, 01));
				order.setDiscount(discount);
				order.setProduct(product);
				order.setProvider(provider);
				order.setSent(null); //Fail

				Validator validator = createValidator();
				Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);

				assertThat(constraintViolations.size()).isEqualTo(1);
				ConstraintViolation<Order> violation = constraintViolations.iterator().next();
				assertThat(violation.getPropertyPath().toString()).isEqualTo("sent");
				assertThat(violation.getMessage()).isEqualTo("must not be null");			}
}
