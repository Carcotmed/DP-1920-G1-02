package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Provider;

public interface ProviderRepository extends CrudRepository<Provider, Integer>{
	
	@Query("SELECT p FROM Provider p WHERE p.id = ?1")
	Provider findProviderById(int providerId) throws DataAccessException;
	
}
