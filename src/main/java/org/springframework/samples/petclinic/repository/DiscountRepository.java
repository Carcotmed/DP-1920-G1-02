package org.springframework.samples.petclinic.repository;

import java.security.Provider;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Discount;

public interface DiscountRepository extends CrudRepository<Discount, Integer> {

	@Query("SELECT d FROM Discount d WHERE d.percentage = ?1")
	Discount findDiscountByPercentage(double percentage) throws DataAccessException;
	
	@Query("SELECT d FROM Discount d WHERE d.id = ?1")
	Discount findDiscountById(int discountId) throws DataAccessException;
}
