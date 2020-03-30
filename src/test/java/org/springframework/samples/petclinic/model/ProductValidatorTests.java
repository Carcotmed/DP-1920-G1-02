package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Transactional;

public class ProductValidatorTests extends ValidatorTests {
	

	Provider provider = new Provider();

	@BeforeEach
	void init() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		provider.setAddress("pipo");
		provider.setEmail("pipo@gmail.com");
		provider.setName("ProvPrueba");
		provider.setPhone("123456789");
	}


	// =============== Create: [1 - 12] ( 11- | 1+ ) =======================

		// 1 +
		@Test
		@Transactional
		public void shouldInsertDBAndGenerateId() {
			Product product = new Product();

			product.setAllAvailable(true);
			product.setName("TestP");
			product.setPrice(99.9);
			product.setProvider(provider);
			product.setQuantity(1);

			Validator validator = createValidator();
			Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
			
			assertThat(constraintViolations.size()).isEqualTo(0);
		}

		// 2 -
		@Test
		public void shouldNotInsertDBWhenAllAvailableNull() {
			Product product = new Product();

			product.setAllAvailable(null); // Fail
			product.setName("TestP");
			product.setPrice(99.9);
			product.setProvider(provider);
			product.setQuantity(1);

			Validator validator = createValidator();
			Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);

			assertThat(constraintViolations.size()).isEqualTo(1);
			ConstraintViolation<Product> violation = constraintViolations.iterator().next();
			assertThat(violation.getPropertyPath().toString()).isEqualTo("allAvailable");
			assertThat(violation.getMessage()).isEqualTo("must not be null");
		}

		// 3 -
		@Test
		public void shouldNotInsertDBWhenNameNull() {
			Product product = new Product();

			product.setAllAvailable(false);
			product.setName(null); // Fail
			product.setPrice(99.9);
			product.setProvider(provider);
			product.setQuantity(1);

			Validator validator = createValidator();
			Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);

			assertThat(constraintViolations.size()).isEqualTo(1);
			ConstraintViolation<Product> violation = constraintViolations.iterator().next();
			assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
			assertThat(violation.getMessage()).isEqualTo("must not be blank");		}

		// 4 -
		@Test
		public void shouldNotInsertDBWhenNameBlank() {
			Product product = new Product();

			product.setAllAvailable(false);
			product.setName("    "); // Fail
			product.setPrice(99.9);
			product.setProvider(provider);
			product.setQuantity(1);

			Validator validator = createValidator();
			Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);

			assertThat(constraintViolations.size()).isEqualTo(1);
			ConstraintViolation<Product> violation = constraintViolations.iterator().next();
			assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
			assertThat(violation.getMessage()).isEqualTo("must not be blank");		}

		// 5 -
		@Test
		public void shouldNotInsertDBWhenNameShort() {
			Product product = new Product();

			product.setAllAvailable(false);
			product.setName("a"); // Fail
			product.setPrice(99.9);
			product.setProvider(provider);
			product.setQuantity(1);

			Validator validator = createValidator();
			Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);

			assertThat(constraintViolations.size()).isEqualTo(1);
			ConstraintViolation<Product> violation = constraintViolations.iterator().next();
			assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
			assertThat(violation.getMessage()).isEqualTo("size must be between 3 and 50");		}

		
		// 6 -
		@Test
		public void shouldNotInsertDBWhenNameLong() {
			Product product = new Product();

			product.setAllAvailable(false);
			product.setName("ThisIsANameWithMoreThan50CharactersAAAAAAAAAAAAAAAA"); // Fail
			product.setPrice(99.9);
			product.setProvider(provider);
			product.setQuantity(1);

			Validator validator = createValidator();
			Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);

			assertThat(constraintViolations.size()).isEqualTo(1);
			ConstraintViolation<Product> violation = constraintViolations.iterator().next();
			assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
			assertThat(violation.getMessage()).isEqualTo("size must be between 3 and 50");		}

		// 7 -
		@Test
		public void shouldNotInsertDBWhenPriceNull() {
			Product product = new Product();

			product.setAllAvailable(false);
			product.setName("Peter");
			product.setPrice(null); // Fail
			product.setProvider(provider);
			product.setQuantity(1);

			Validator validator = createValidator();
			Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);

			assertThat(constraintViolations.size()).isEqualTo(1);
			ConstraintViolation<Product> violation = constraintViolations.iterator().next();
			assertThat(violation.getPropertyPath().toString()).isEqualTo("price");
			assertThat(violation.getMessage()).isEqualTo("must not be null");		}

		// 8 -
		@Test
		public void shouldNotInsertDBWhenPriceIntegerDigitsAreMoreThanTwo() {
			Product product = new Product();

			product.setAllAvailable(false);
			product.setName("Peter");
			product.setPrice(111.11); // Fail
			product.setProvider(provider);
			product.setQuantity(1);

			Validator validator = createValidator();
			Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);

			assertThat(constraintViolations.size()).isEqualTo(1);
			ConstraintViolation<Product> violation = constraintViolations.iterator().next();
			assertThat(violation.getPropertyPath().toString()).isEqualTo("price");
			assertThat(violation.getMessage()).isEqualTo("numeric value out of bounds (<2 digits>.<2 digits> expected)");		}

		// 9 -
		@Test
		public void shouldNotInsertDBWhenPriceFractionDigitsAreMoreThanTwo() {
			Product product = new Product();

			product.setAllAvailable(false);
			product.setName("Peter");
			product.setPrice(11.111); // Fail
			product.setProvider(provider);
			product.setQuantity(1);

			Validator validator = createValidator();
			Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);

			assertThat(constraintViolations.size()).isEqualTo(1);
			ConstraintViolation<Product> violation = constraintViolations.iterator().next();
			assertThat(violation.getPropertyPath().toString()).isEqualTo("price");
			assertThat(violation.getMessage()).isEqualTo("numeric value out of bounds (<2 digits>.<2 digits> expected)");		}

		// 10 -
		@Test
		public void shouldNotInsertDBWhenPriceIsNegative() {
			Product product = new Product();

			product.setAllAvailable(false);
			product.setName("Peter");
			product.setPrice(-1.1); // Fail
			product.setProvider(provider);
			product.setQuantity(1);

			Validator validator = createValidator();
			Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);

			assertThat(constraintViolations.size()).isEqualTo(1);
			ConstraintViolation<Product> violation = constraintViolations.iterator().next();
			assertThat(violation.getPropertyPath().toString()).isEqualTo("price");
			assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 0");		}

		// 11 -
		@Test
		public void shouldNotInsertDBWhenProviderNull() {
			Product product = new Product();

			product.setAllAvailable(false);
			product.setName("Peter");
			product.setPrice(1.1);
			product.setProvider(null); // Fail
			product.setQuantity(1);

			Validator validator = createValidator();
			Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);

			assertThat(constraintViolations.size()).isEqualTo(1);
			ConstraintViolation<Product> violation = constraintViolations.iterator().next();
			assertThat(violation.getPropertyPath().toString()).isEqualTo("provider");
			assertThat(violation.getMessage()).isEqualTo("must not be null");		}

		// 12 -
		@Test
		public void shouldNotInsertDBWhenQuantityNull() {
			Product product = new Product();

			product.setAllAvailable(false);
			product.setName("Peter");
			product.setPrice(1.1);
			product.setProvider(provider);
			product.setQuantity(null); // Fail

			Validator validator = createValidator();
			Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);

			assertThat(constraintViolations.size()).isEqualTo(1);
			ConstraintViolation<Product> violation = constraintViolations.iterator().next();
			assertThat(violation.getPropertyPath().toString()).isEqualTo("quantity");
			assertThat(violation.getMessage()).isEqualTo("must not be null");		}

		// 13 -
		@Test
		public void shouldNotInsertDBWhenQuantityNegative() {
			Product product = new Product();

			product.setAllAvailable(false);
			product.setName("Peter");
			product.setPrice(1.1);
			product.setProvider(provider);
			product.setQuantity(-1); // Fail

			Validator validator = createValidator();
			Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);

			assertThat(constraintViolations.size()).isEqualTo(1);
			ConstraintViolation<Product> violation = constraintViolations.iterator().next();
			assertThat(violation.getPropertyPath().toString()).isEqualTo("quantity");
			assertThat(violation.getMessage()).isEqualTo("must be greater than or equal to 0");		}
	
	
}
