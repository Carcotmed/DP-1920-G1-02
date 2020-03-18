package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Order;

public interface OrderRepository extends CrudRepository<Order, Integer>{

	@Query("SELECT o FROM Order o WHERE o.id = ?1")
	Order findOrderById(int orderId) throws DataAccessException;
	
}
