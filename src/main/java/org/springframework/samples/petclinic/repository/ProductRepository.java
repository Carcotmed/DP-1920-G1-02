package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
	
	@Query("SELECT FROM Product p WHERE p.product_id == ?1")
	Product findProductById(int productId) throws DataAccessException;
	
}
