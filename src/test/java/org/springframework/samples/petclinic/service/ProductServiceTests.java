package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.util.EntityUtils;
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
		provider.setPhone("123456789");
		this.providerService.saveProvider(provider);
		
		
	}

	// 1 Save+
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

	// 2 Save-
	@Test
	public void shouldNotInsertDBWhenAllAvailableNull() {
		Product product = new Product();

		product.setAllAvailable(null);
		product.setName("TestP");
		product.setPrice(99.9);
		product.setProvider(provider);
		product.setQuantity(1);
				
		assertThrows(ConstraintViolationException.class, () -> productService.save(product));
	}

	//3 findProducts+
	@Test
	public void shouldFindProducts() {
		Collection<Product> products = this.productService.findProducts();

		Product product1 = EntityUtils.getById(products, Product.class, 1);
		assertThat(product1.getName()).isEqualTo("Pomadita");
		Product product2 = EntityUtils.getById(products, Product.class, 2);
		assertThat(product2.getName()).isEqualTo("Collar");
		Product product3 = EntityUtils.getById(products, Product.class, 3);
		assertThat(product3.getName()).isEqualTo("Juguete");
	}

	//4 findProductsByProviderId+
		@Test
		public void shouldFindProductsByProviderId() {
			Collection<Product> products = this.productService.findAllByProviderId(1);
			assertThat(products.size() == 1);
			assertThat(products.stream().map(x -> x.getName()).equals("Pomadita"));
		}


}

