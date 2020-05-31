package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

public class ProductServiceTests {

	@Autowired
	public ProductService productService;

	@Autowired
	public ProviderService providerService;
	
	@Autowired
	public OrderService orderService;
	
	@Autowired
	public DiscountService discountService;

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
		product.setEnabled(true);

		
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
		product.setEnabled(true);

				
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
		
		
		// 5 delete+
		@Test
		public void shouldDeleteProduct() {
			Collection<Product> products = this.productService.findProducts();
			int found = products.size();
			
			Collection<Order> orders = this.orderService.findAllOrdersByProductId(1);
			orders.stream().forEach(x -> this.orderService.deleteOrder(x)); //borra los pedidos relacionados con el producto
			
			
			Collection<Discount> discounts = this.discountService.findAllByProductId(1);
			Collection<Order> orders2 = new ArrayList<Order>();
			discounts.stream().forEach(x -> orders2.addAll(this.orderService.findAllOrdersByDiscountId(x.getId())));
			orders2.stream().forEach(x -> this.orderService.deleteOrder(x));//borra los pedidos relacionados con los descuentos relacionados con el producto

			discounts.stream().forEach(x -> this.discountService.deleteDiscount(x)); //Borra los descuentos relacionados con el producto
			
			this.productService.deleteProduct(this.productService.findProductById(1));
			assertThat(this.productService.findProducts().size()).isEqualTo(found - 1);
		}

		// 6 delete-
		@Test
		public void shouldNotDeleteProduct() {
			Collection<Product> products = this.productService.findProducts();
			int found = products.size();
			assertThrows(InvalidDataAccessApiUsageException.class, () -> this.productService
					.deleteProduct(this.productService.findProductById(50)));
			assertThat(this.productService.findProducts().size()).isEqualTo(found);
		}

	  //7 update+
		@Test
		@Transactional
		public void shouldUpdateProduct() {
			Product product = new Product();


			product.setAllAvailable(true);
			product.setName("TestP");
			product.setPrice(99.9);
			product.setProvider(provider);
			product.setQuantity(1);
			product.setEnabled(true);

			productService.save(product);
			Product productAct = this.productService.findProducts().stream()
					.filter(x -> x.getPrice().equals(99.9)).collect(Collectors.toList()).get(0);
			productAct.setQuantity(30);
			productService.save(productAct);

			assertThat(productService.findProductById(productAct.getId()).getQuantity().equals(30));
		}
		
		 //8 update-
			@Test
			@Transactional
			public void shouldNotUpdateProduct() {
				Product product = new Product();

				product.setAllAvailable(true);
				product.setName("TestP");
				product.setPrice(99.9);
				product.setProvider(provider);
				product.setQuantity(1);
				product.setEnabled(true);

				this.productService.save(product);
				
				Product productAct = this.productService.findProducts().stream()
						.filter(x -> x.getPrice().equals(99.9)).collect(Collectors.toList()).get(0);
				
				productAct.setQuantity(-300);
				this.productService.save(productAct);
				
				assertThrows(ConstraintViolationException.class, () -> this.productService
						.findProductById(productAct.getId()).getPrice().equals(99.9));
			}


}

