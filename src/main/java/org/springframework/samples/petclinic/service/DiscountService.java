package org.springframework.samples.petclinic.service;


import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	public void save(@Valid Discount discount) {
		discountRepo.save(discount);
	}

	public void deleteDiscount(Discount discount) {
		this.discountRepo.delete(discount);
	}
	
	@Transactional
	public Discount findDiscountById(int discountId){
		return this.discountRepo.findDiscountById(discountId);
	}

	public Collection<Discount> findDiscounts() {
		return this.discountRepo.findDiscounts();
	}
	
	public Collection<Discount> findAllByProductId(int productId) {
		return this.discountRepo.findAllByProductId(productId);
	}
	
	public Collection<Discount> findAllByProviderId(int providerId) {
		return this.discountRepo.findAllByProviderId(providerId);
	}


	
}
