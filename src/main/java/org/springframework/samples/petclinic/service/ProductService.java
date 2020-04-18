package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Discount;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepo;
	
	@Transactional
	public Iterable<Product> findAll(){
		return productRepo.findAll();
	}
	
	@Transactional
	public void save(@Valid Product product) {
		this.productRepo.save(product);
	}
	
	@Transactional
	public Collection<Product> findProducts() throws DataAccessException{
		return productRepo.findAllProducts();
	}

	public Collection<Product> findAllByProviderId(int providerId) {
		return this.productRepo.findAllByProviderId(providerId);
	}	
	
	
	@Transactional
	public Product findProductById(int productId) throws DataAccessException {
		return this.productRepo.findProductById(productId);
	}
	
}
