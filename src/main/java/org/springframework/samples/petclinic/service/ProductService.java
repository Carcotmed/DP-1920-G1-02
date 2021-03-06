package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
	@CacheEvict (allEntries = true, cacheNames = "findProducts")
	public void save(@Valid Product product) {
		this.productRepo.save(product);
	}
	
	@Transactional

	@Cacheable (cacheNames = "findProducts")
	public Collection<Product> findProducts(){
		return productRepo.findAllProducts();
	}

	public Collection<Product> findAllByProviderId(int providerId) {
		return this.productRepo.findAllByProviderId(providerId);
	}
	
	@Transactional
	@CacheEvict (allEntries = true, cacheNames = "findProducts")
	public void useOne(@Valid Product product) {
		
		product.setQuantity(product.getQuantity()-1);
		
		this.productRepo.save(product);
		
	}
	
	@Transactional
	@CacheEvict (allEntries = true, cacheNames = "findProducts")
	public void addOne(@Valid Product product) {
		
		product.setQuantity(product.getQuantity()+1);
		
		this.productRepo.save(product);
		
	}
	
	@Transactional
	public Product findProductById(int productId){
		return this.productRepo.findProductById(productId);
	}	
	
	
	@CacheEvict (allEntries = true, cacheNames = "findProducts")
	public void deleteProduct(Product product) {
		this.productRepo.delete(product);
	}
	
	
}
