package org.springframework.samples.petclinic.service;


import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.repository.ProductRepository;
import org.springframework.samples.petclinic.repository.ProviderRepository;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {

	@Autowired
	private ProviderRepository providerRepo;
	
	@Autowired
	private ProductRepository productRepo;



	@Transactional
	@Cacheable (cacheNames = "findProviders")
	public Collection<Provider> findProviders() throws DataAccessException{
		return (Collection<Provider>) providerRepo.findAll();
	}
	
	@Transactional
	@CacheEvict (allEntries = true, cacheNames = "findProviders")
	public void saveProvider(@Valid Provider provider) {
		providerRepo.save(provider);

	}

	@Transactional(readOnly = true)
	public Provider findProviderById(int id) throws DataAccessException {
		return providerRepo.findProviderById(id);
	}
	

	@Transactional
	@CacheEvict (allEntries = true, cacheNames = "findProviders")
	public void deleteProvider(Provider provider) {
		providerRepo.delete(provider);
		
	}

}
