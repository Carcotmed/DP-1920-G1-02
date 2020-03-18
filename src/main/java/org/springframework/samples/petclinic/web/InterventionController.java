/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Intervention;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.samples.petclinic.service.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.InterventionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}/visits/{visitId}")
public class InterventionController {

	private static final String VIEWS_INTERVENTIONS_CREATE_OR_UPDATE_FORM = "interventions/createOrUpdateInterventionForm";

	private final VetService vetService;
	private final VisitService visitService;
	private final InterventionService interventionService;
	private final ProductService productService;


	@Autowired
	public InterventionController(VetService vetService, VisitService visitService,
			InterventionService interventionService, ProductService productService) {
		this.vetService = vetService;
		this.visitService = visitService;
		this.interventionService = interventionService;
		this.productService = productService;
	}

	@ModelAttribute("vets")
	public Collection<Vet> populateVets() {
		return this.vetService.findVets();
	}
	
	@ModelAttribute("products")
	public Collection<Product> populateProducts() {
		return this.productService.findProducts();
	}

	@ModelAttribute("visit")
	public Visit findVisit(@PathVariable("visitId") int visitId) {
		return this.visitService.findVisitById(visitId);
	}

	/*
	 * @ModelAttribute("pet") public Pet findPet(@PathVariable("petId") Integer
	 * petId) { Pet result=null; if(petId!=null)
	 * result=this.clinicService.findPetById(petId); else result=new Pet(); return
	 * result; }
	 */

	@GetMapping(value = "/interventions/new")
	public String initCreationForm(Visit visit, ModelMap model) {
		Intervention intervention = new Intervention();
		visit.setIntervention(intervention);
		model.put("intervention", intervention);
		return VIEWS_INTERVENTIONS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/interventions/new")
	public String processCreationForm(Visit visit, @Valid Intervention intervention, BindingResult result,
			ModelMap model) {
		
		System.out.println("Name: "+model.getAttribute("name"));
		
		if (result.hasErrors()) {
			model.put("intervention", intervention);
			return VIEWS_INTERVENTIONS_CREATE_OR_UPDATE_FORM;
		} else {
			visit.setIntervention(intervention);
			this.interventionService.saveIntervention(intervention);
			return "redirect:/owners/{ownerId}/pets/{petId}";
		}
	}

	@GetMapping(value = "/interventions/{interventionId}/edit")
	public String initUpdateForm(@PathVariable("interventionId") int interventionId, ModelMap model) {
		Intervention intervention = this.interventionService.findInterventionById(interventionId);
		model.put("intervention", intervention);
		return VIEWS_INTERVENTIONS_CREATE_OR_UPDATE_FORM;
	}

	/**
	 *
	 * @param pet
	 * @param result
	 * @param petId
	 * @param model
	 * @param owner
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/interventions/{interventionId}/edit")
	public String processUpdateForm(@Valid Intervention intervention, BindingResult result, Visit visit,
			@PathVariable("interventionId") int interventionId, ModelMap model) {
		if (result.hasErrors()) {
			model.put("intervention", intervention);
			return VIEWS_INTERVENTIONS_CREATE_OR_UPDATE_FORM;
		} else {
			Intervention interventionToUpdate = this.interventionService.findInterventionById(interventionId);
			BeanUtils.copyProperties(intervention, interventionToUpdate, "id", "visit", "vets", "requiredProducts");

			this.interventionService.saveIntervention(interventionToUpdate);

			return "redirect:/owners/{ownerId}/pets/{petId}";
		}
	}

}
