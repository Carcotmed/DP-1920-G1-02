package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Discount;
import org.springframework.samples.petclinic.model.Order;
import org.springframework.samples.petclinic.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
	
	@Query("SELECT p FROM Product p WHERE p.id = ?1")
	Product findProductById(int productId) throws DataAccessException;
	
	@Query ("SELECT p FROM Product p WHERE p.provider.id = ?1")
	Collection<Product> findAllByProviderId(int providerId);

	@Query ("SELECT p FROM Product p") 
	Collection<Product> findAllProducts() throws DataAccessException;

	@Query ("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.provider pr")
	Collection<Product> findAllWithProvider();

	@Query ("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.provider pr WHERE p.id = ?1")
	Product findProductWithProviderById(int productId);

	@Query ("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.provider pr WHERE pr.id = ?1")
	Collection<Product> findAllWithProviderByProviderId();
}
