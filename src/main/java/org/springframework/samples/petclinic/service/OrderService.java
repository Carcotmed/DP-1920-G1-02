package org.springframework.samples.petclinic.service;

import javax.transaction.Transactional;

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
	public void save(Order order) {
		orderRepo.save(order);
	}

	public void deleteDiscount(Order order) {
		this.orderRepo.delete(order);
	}
	
	@Transactional
	public Order findOrderById(int orderId) throws DataAccessException {
		return this.orderRepo.findOrderById(orderId);
	}
}
