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

package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/adoptions")
public class AdoptionController {

	private final AdoptionService	adoptionService;
	private final PetService		petService;
	private final OwnerService		ownerService;


	@Autowired
	public AdoptionController(final OwnerService ownerService, final AdoptionService adoptionService, final PetService petService) {
		this.adoptionService = adoptionService;
		this.petService = petService;
		this.ownerService = ownerService;
	}

	@GetMapping()
	public String showAdoptions(final ModelMap model) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user.getAuthorities().contains(new SimpleGrantedAuthority("owner"))) {
				model.put("isOwner", true);
			} else {
				model.put("isOwner", false);
			}
		} catch (Exception e) {
			model.put("isOwner", false);
		}
		model.put("pets", this.petService.findAdoptablePets());
		return "adoptions/adoptionsList";
	}

	@GetMapping("/myAdoptions")
	public String showMyAdoptions(final ModelMap model) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user.getAuthorities().contains(new SimpleGrantedAuthority("owner"))) {
				model.put("adoptions", this.adoptionService.findAdoptionsByOwner(this.ownerService.findOwnerByUsername(user.getUsername()).getId()));
				return "adoptions/myAdoptionsList";
			} else {
				model.put("error", "Only owners can access to this feature");
				return this.showAdoptions(model);
			}
		} catch (Exception e) {
			model.put("error", "Only owners can access to this feature");
			return this.showAdoptions(model);
		}
	}

	@GetMapping(value = "/new/{petId}")
	public String initCreationForm(@PathVariable("petId") final int petId, final ModelMap model) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user.getAuthorities().contains(new SimpleGrantedAuthority("owner"))) {
				Adoption adoption = new Adoption();
				model.put("adoption", adoption);
				return "adoptions/createOrUpdateAdoptionForm";
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			model.put("error", "You can't adopt a pet if you are not an owner");
			return this.showAdoptions(model);
		}
	}

	@PostMapping(value = "/new/{petId}")
	public String processCreationForm(@PathVariable("petId") final int petId, @Valid final Adoption adoption, final BindingResult result, final ModelMap model) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user.getAuthorities().contains(new SimpleGrantedAuthority("owner"))) {
				adoption.setOwner(this.ownerService.findOwnerByUsername(user.getUsername()));
				adoption.setPet(this.petService.findPetById(petId));
				adoption.setDate(LocalDate.now().plusDays(1));
				if (adoption.getEnd() != null && adoption.getEnd().isBefore(adoption.getDate())) {
					model.put("adoption", adoption);
					model.put("error", "You can't adopt a pet for less than 1 days");
					return "adoptions/createOrUpdateAdoptionForm";
				}
				this.adoptionService.save(adoption);
				Pet pet = this.petService.findPetById(petId);
				Owner owner1 = pet.getOwner();
				owner1.removePet(pet);
				this.ownerService.saveOwner(owner1);
				Owner owner2 = this.ownerService.findOwnerByUsername(user.getUsername());
				owner2.addPet(pet);
				this.ownerService.saveOwner(owner2);
				pet.setOwner(owner2);
				this.petService.savePet(pet);
				return this.showAdoptions(model);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			model.put("error", "You can't adopt a pet if you are not an owner");
		}
		return this.showAdoptions(model);
	}

	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.petService.findPetTypes();
	}

	@GetMapping(value = "/newAdoptable")
	public String initCreationForm(final ModelMap model) {
		Pet pet = new Pet();
		Owner owner = this.ownerService.findOwnerByFirstName("Vet");
		pet.setOwner(owner);
		model.put("ownerId", owner.getId());
		model.put("pet", pet);
		return "pets/createOrUpdatePetForm";
	}

	@PostMapping(value = "/newAdoptable")
	public String processCreationForm(@Valid final Pet pet, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("pet", pet);
			return "pets/createOrUpdatePetForm";
		} else {
			try {
				Owner owner = this.ownerService.findOwnerByFirstName("Vet");
				owner.addPet(pet);
				this.petService.savePet(pet);
			} catch (DuplicatedPetNameException ex) {
				result.rejectValue("name", "duplicate", "already exists");
				return "pets/createOrUpdatePetForm";
			}
			return this.showAdoptions(model);
		}
	}
}
