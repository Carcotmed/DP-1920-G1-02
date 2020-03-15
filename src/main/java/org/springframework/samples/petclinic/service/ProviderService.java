package org.springframework.samples.petclinic.service;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.repository.ProviderRepository;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {
	
	@Autowired
	private ProviderRepository providerRepo;
	
	@Transactional
	public Iterable<Provider> findAll(){
		return providerRepo.findAll();
	}

	@Transactional
	public void saveProvider(@Valid Provider provider) {
		providerRepo.save(provider);
		
	}

}
