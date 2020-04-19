package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Order;
import org.springframework.samples.petclinic.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepo;
	
	@Transactional
	public Iterable<Order> findAll(){
		return this.orderRepo.findAll();
	}
	
	@Transactional
	public Collection<Order> findAllOrders(){
		return this.orderRepo.findAllOrders();
	}
	
	
	@Transactional
	public void save(@Valid Order order) {
		orderRepo.save(order);
	}

	
	public void deleteOrder(Order order) {
		this.orderRepo.delete(order);
	}
	
	@Transactional
	public Order findOrderById(int orderId) throws DataAccessException {
		return this.orderRepo.findOrderById(orderId);
	}
	
	@Transactional
	public Collection<Order> findAllOrdersByDiscountId(int discountId){
		return this.orderRepo.findAllOrdersByDiscountId(discountId);
	}

}
