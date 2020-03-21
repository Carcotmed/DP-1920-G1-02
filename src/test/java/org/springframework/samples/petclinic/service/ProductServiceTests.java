package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

public class ProductServiceTests {

	@Autowired
	public ProductService productService;

	@Autowired
	public ProviderService providerService;

	Provider provider = new Provider();

	@BeforeEach
	void init() {
		provider.setAddress("pipo");
		provider.setEmail("pipo@gmail.com");
		provider.setName("ProvPrueba");
		provider.setPhone(123456789);
		this.providerService.saveProvider(provider);
	}

	// =============== Create: [1 - 11] ( 10- | 1+ ) =======================

	// 1 +
	@Test
	@Transactional
	public void shouldInsertDBAndGenerateId() {
		Integer found = this.productService.findProducts().size();
		Product product = new Product();

		product.setAllAvailable(true);
		product.setName("TestP");
		product.setPrice(99.9);
		product.setProvider(provider);
		product.setQuantity(1);

		productService.save(product);
		
		assertThat(product.getId().longValue()).isNotEqualTo(0);
		assertThat(this.productService.findProducts().size()).isEqualTo(found + 1);
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

		assertThrows(ConstraintViolationException.class, () -> productService.save(product));
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

		assertThrows(ConstraintViolationException.class, () -> productService.save(product));
	}

	// 4 -
	@Test
	public void shouldNotInsertDBWhenNameBlank() {
		Product product = new Product();

		product.setAllAvailable(false);
		product.setName(""); // Fail
		product.setPrice(99.9);
		product.setProvider(provider);
		product.setQuantity(1);

		assertThrows(ConstraintViolationException.class, () -> productService.save(product));
	}

	// 5 -
	@Test
	public void shouldNotInsertDBWhenNameShort() {
		Product product = new Product();

		product.setAllAvailable(false);
		product.setName("a"); // Fail
		product.setPrice(99.9);
		product.setProvider(provider);
		product.setQuantity(1);

		assertThrows(ConstraintViolationException.class, () -> productService.save(product));
	}

	// 6 -
	@Test
	public void shouldNotInsertDBWhenNameLong() {
		Product product = new Product();

		product.setAllAvailable(false);
		product.setName("ThisIsANameWithMoreThan50CharactersAAAAAAAAAAAAAAAA"); // Fail
		product.setPrice(99.9);
		product.setProvider(provider);
		product.setQuantity(1);

		assertThrows(ConstraintViolationException.class, () -> productService.save(product));
	}

	// 7 -
	@Test
	public void shouldNotInsertDBWhenPriceNull() {
		Product product = new Product();

		product.setAllAvailable(false);
		product.setName("Peter");
		product.setPrice(null); // Fail
		product.setProvider(provider);
		product.setQuantity(1);

		assertThrows(ConstraintViolationException.class, () -> productService.save(product));
	}

	// 8 -
	@Test
	public void shouldNotInsertDBWhenPriceIntegerDigitsAreMoreThanTwo() {
		Product product = new Product();

		product.setAllAvailable(false);
		product.setName("Peter");
		product.setPrice(111.11); // Fail
		product.setProvider(provider);
		product.setQuantity(1);

		assertThrows(ConstraintViolationException.class, () -> productService.save(product));
	}

	// 9 -
	@Test
	public void shouldNotInsertDBWhenPriceFractionDigitsAreMoreThanTwo() {
		Product product = new Product();

		product.setAllAvailable(false);
		product.setName("Peter");
		product.setPrice(11.111); // Fail
		product.setProvider(provider);
		product.setQuantity(1);

		assertThrows(ConstraintViolationException.class, () -> productService.save(product));
	}

	// 9 -
	@Test
	public void shouldNotInsertDBWhenPriceIsNegative() {
		Product product = new Product();

		product.setAllAvailable(false);
		product.setName("Peter");
		product.setPrice(-1.1); // Fail
		product.setProvider(provider);
		product.setQuantity(1);

		assertThrows(ConstraintViolationException.class, () -> productService.save(product));
	}

	// 10 -
	@Test
	public void shouldNotInsertDBWhenProviderNull() {
		Product product = new Product();

		product.setAllAvailable(false);
		product.setName("Peter");
		product.setPrice(1.1);
		product.setProvider(null); // Fail
		product.setQuantity(1);

		assertThrows(DataIntegrityViolationException.class, () -> productService.save(product));
	}

	// 11 -
	@Test
	public void shouldNotInsertDBWhenQuantityNull() {
		Product product = new Product();

		product.setAllAvailable(false);
		product.setName("Peter");
		product.setPrice(1.1);
		product.setProvider(provider);
		product.setQuantity(null); // Fail

		assertThrows(ConstraintViolationException.class, () -> productService.save(product));
	}

	// 11 -
	@Test
	public void shouldNotInsertDBWhenQuantityNegative() {
		Product product = new Product();

		product.setAllAvailable(false);
		product.setName("Peter");
		product.setPrice(1.1);
		product.setProvider(provider);
		product.setQuantity(-1); // Fail

		assertThrows(ConstraintViolationException.class, () -> productService.save(product));
	}
}
