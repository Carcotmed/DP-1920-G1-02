/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class OwnerService {

	@Autowired
	private OwnerRepository		ownerRepository;

	@Autowired
	private PetRepository		petRepository;

	@Autowired
	private UserService			userService;

	@Autowired
	private AuthoritiesService	authoritiesService;


	@Autowired
	public OwnerService(final OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}

	@Transactional(readOnly = true)
	public Owner findOwnerById(final int id) throws DataAccessException {
		return this.ownerRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Owner findOwnerByUsername(final String username) throws DataAccessException {
		return this.ownerRepository.findByUsername(username);
	}

	@Cacheable (cacheNames = "ownersByLastName")
	@Transactional(readOnly = true)
	public Collection<Owner> findOwnerByLastName(final String lastName) throws DataAccessException {
		return this.ownerRepository.findByLastName(lastName);
	}

	@Transactional
	@CacheEvict (allEntries = true, cacheNames = "ownersByLastName")
	public void saveOwner(final Owner owner) throws DataAccessException {
		//creating owner
		this.ownerRepository.save(owner);

		if (owner.getUser() != null) {

			//creating user
			this.userService.saveUser(owner.getUser());
			//creating authorities
			this.authoritiesService.saveAuthorities(owner.getUser().getUsername(), "owner");

		}

	}

	public Owner findOwnerByFirstName(final String name) {
		return this.ownerRepository.findByFirstName(name);
	}

	public void delete(final Owner owner) {
		if (owner.getPets() != null) {
			for (Pet pet : owner.getPets()) {
				pet.setOwner(this.ownerRepository.findByFirstName("vet"));
				this.petRepository.save(pet);
			}
			this.ownerRepository.delete(owner);
		}

	}

}
