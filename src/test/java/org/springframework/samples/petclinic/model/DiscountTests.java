package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Transactional;

public class DiscountTests extends ValidatorTests {

	Product product = new Product();
	Provider provider = new Provider();

	@BeforeEach
	void init() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		provider.setAddress("pipo");
		provider.setEmail("pipo@gmail.com");
		provider.setName("ProvPrueba");
		provider.setPhone(123456789);

		product.setAllAvailable(true);
		product.setName("test");
		product.setPrice(20.02);
		product.setProvider(provider);
		product.setQuantity(19);

	}

	// =============== Create: [1 - 10] ( 7- | 3+ ) =======================

	// 1 +
	@Test
	@Transactional
	void shouldInsertDBAndGenerateId() {
		Discount discount = new Discount();
		discount.setPercentage(80.0);
		discount.setProduct(product);
		discount.setProvider(provider);
		discount.setQuantity(1);

		Validator validator = createValidator();
		Set<ConstraintViolation<Discount>> constraintViolations = validator.validate(discount);

		assertThat(constraintViolations.size()).isEqualTo(0);
	}

	// 2 +
	@ParameterizedTest
	@ValueSource(doubles = { 1.0, 01.0, 1.00, 10.0, 20.00, 30.00, 99.99 })
	void shouldInsertDBWhenValidPercentage(double percentage) {
		Discount discount = new Discount();

		discount.setPercentage(percentage); // Works
		discount.setProduct(product);
		discount.setProvider(provider);
		discount.setQuantity(1);

		Validator validator = createValidator();
		Set<ConstraintViolation<Discount>> constraintViolations = validator.validate(discount);

		assertThat(constraintViolations.size()).isEqualTo(0);

	}

	// 3 -
	@ParameterizedTest
	@ValueSource(doubles = { 0.0, 00.0, 0.00, 00.00, -12.92, 00.99 })
	void shouldNotInsertDBWhenInvalidPerdcentageRange(double percentage) {
		Discount discount = new Discount();

		discount.setPercentage(percentage); // Fail
		discount.setProduct(product);
		discount.setProvider(provider);
		discount.setQuantity(1);

		Validator validator = createValidator();
		Set<ConstraintViolation<Discount>> constraintViolations = validator.validate(discount);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Discount> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("percentage");
		assertThat(violation.getMessage()).isEqualTo("size must be between 1 and 100");
	}

	// 4 -
	@ParameterizedTest
	@ValueSource(doubles = { 100.0, 100.00, 90.001 })
	void shouldNotInsertDBWhenInvalidPerdcentageDigits(double percentage) {
		Discount discount = new Discount();

		discount.setPercentage(percentage); // Fail
		discount.setProduct(product);
		discount.setProvider(provider);
		discount.setQuantity(1);

		Validator validator = createValidator();
		Set<ConstraintViolation<Discount>> constraintViolations = validator.validate(discount);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Discount> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("percentage");
		assertThat(violation.getMessage()).isEqualTo("numeric value out of bounds (<2 digits>.<2 digits> expected)");
	}

	// 5 -
	@Test
	void shouldNotInsertDBWhenPercentageNull() {
		Discount discount = new Discount();

		discount.setPercentage(null); // Fail
		discount.setProduct(product);
		discount.setProvider(provider);
		discount.setQuantity(1);

		Validator validator = createValidator();
		Set<ConstraintViolation<Discount>> constraintViolations = validator.validate(discount);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Discount> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("percentage");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	// 6 -
	@Test
	void shouldNotInsertDBWhenProductNull() {
		Discount discount = new Discount();

		discount.setPercentage(20.0);
		discount.setProduct(null); // Fail
		discount.setProvider(provider);
		discount.setQuantity(1);

		Validator validator = createValidator();
		Set<ConstraintViolation<Discount>> constraintViolations = validator.validate(discount);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Discount> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("product");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	// 7 -
	@Test
	void shouldNotInsertDBWhenProviderNull() {
		Discount discount = new Discount();

		discount.setPercentage(20.0);
		discount.setProduct(product);
		discount.setProvider(null); // Fail
		discount.setQuantity(1);

		Validator validator = createValidator();
		Set<ConstraintViolation<Discount>> constraintViolations = validator.validate(discount);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Discount> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("provider");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	// 8 -
	@Test
	void shouldNotInsertDBWhenQuantityNull() {
		Discount discount = new Discount();

		discount.setPercentage(20.0);
		discount.setProduct(product);
		discount.setProvider(provider);
		discount.setQuantity(null); // Fail

		Validator validator = createValidator();
		Set<ConstraintViolation<Discount>> constraintViolations = validator.validate(discount);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Discount> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("quantity");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	// 9 +
	@ParameterizedTest
	@ValueSource(ints = { 0, 1, 2, 3, 4, 5, 10, 100, 1000 })
	void shouldInsertDBWhenValidQuantity(int quantity) {
		Discount discount = new Discount();

		discount.setPercentage(20.9);
		discount.setProduct(product);
		discount.setProvider(provider);
		discount.setQuantity(quantity); // Works

		Validator validator = createValidator();
		Set<ConstraintViolation<Discount>> constraintViolations = validator.validate(discount);

		assertThat(constraintViolations.size()).isEqualTo(0);

	}

	// 10 -
	@ParameterizedTest
	@ValueSource(ints = { -1, -2, -3, -10, -100, -1000 })
	void shouldInsertDBWhenInvalidQuantityRange(int quantity) {
		Discount discount = new Discount();

		discount.setPercentage(20.9);
		discount.setProduct(product);
		discount.setProvider(provider);
		discount.setQuantity(quantity); // Fails

		Validator validator = createValidator();
		Set<ConstraintViolation<Discount>> constraintViolations = validator.validate(discount);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Discount> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("quantity");
		assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 0");

	}
}
