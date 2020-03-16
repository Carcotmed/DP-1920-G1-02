package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
	public void save(Product product) {
		productRepo.save(product);
	}
	
	@Transactional
	public Collection<Product> findProducts(){
		return (Collection<Product>) productRepo.findAll();
	}
}
