package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Discount;

public interface DiscountRepository extends CrudRepository<Discount, Integer> {

	@Query("SELECT d FROM Discount d WHERE d.percentage = ?1")
	Discount findDiscountByPercentage(double percentage) throws DataAccessException;
	
	@Query("SELECT d FROM Discount d WHERE d.id = ?1")
	Discount findDiscountById(int discountId) throws DataAccessException;

	@Query ("SELECT d FROM Discount d WHERE d.product.id = ?1")
	Collection<Discount> findAllByProductId(int productId) throws DataAccessException;
	
	@Query ("SELECT d FROM Discount d WHERE d.provider.id = ?1")
	Collection<Discount> findAllByProviderId(int providerId) throws DataAccessException;

}
