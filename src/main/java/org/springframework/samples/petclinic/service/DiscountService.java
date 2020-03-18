package org.springframework.samples.petclinic.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Discount;
import org.springframework.samples.petclinic.repository.DiscountRepository;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

	@Autowired
	private DiscountRepository discountRepo;
	
	@Transactional
	public Iterable<Discount> findAll(){
		return discountRepo.findAll();
	}
	
	@Transactional
	public void save(Discount discount) {
		discountRepo.save(discount);
	}

	public void deleteDiscount(Discount discount) {
		this.discountRepo.delete(discount);
	}
	
	@Transactional
	public Discount findDiscountById(int discountId) throws DataAccessException {
		return this.discountRepo.findDiscountById(discountId);
	}
	
}
