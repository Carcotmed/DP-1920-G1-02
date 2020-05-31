package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Order;

public interface OrderRepository extends CrudRepository<Order, Integer>{

	@Query("SELECT o FROM Order o WHERE o.id = ?1")
	Order findOrderById(int orderId) throws DataAccessException;
	
	@Query("SELECT o FROM Order o")
	Collection<Order> findAllOrders() throws DataAccessException;

	@Query("SELECT o FROM Order o WHERE o.discount.id = ?1")
	Collection<Order> findAllOrdersByDiscountId(int discountId) throws DataAccessException;
	
	@Query("SELECT o FROM Order o WHERE o.product.id = ?1")
	Collection<Order> findAllOrdersByProductId(int productId) throws DataAccessException;
	
	@Query ("SELECT o FROM Order o WHERE o.provider.id = ?1")
	Collection<Order> findAllOrdersByProviderId(int providerId);
}
