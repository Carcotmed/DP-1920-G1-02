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
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.samples.petclinic.service.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.InterventionService;
import org.springframework.samples.petclinic.service.ProductService;

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

		model.put("vets", this.interventionService.getAvailableVets(visit.getDate()));

		Intervention intervention = new Intervention();
		visit.setIntervention(intervention);
		model.put("intervention", intervention);
		return VIEWS_INTERVENTIONS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/interventions/new")
	public String processCreationForm(Visit visit, @Valid Intervention intervention, BindingResult result,
			ModelMap model) {
		model.put("vets", this.interventionService.getAvailableVets(visit.getDate()));

		intervention.setVisit(visit);
		
		/////////////////////		
		if (intervention.getVet() == null) {
			result.rejectValue("vet", "noVetError", "You must choose a vet");
			model.put("intervention", intervention);
		}

		Boolean allAvailable = intervention.getRequiredProducts().stream().allMatch(p -> p.getQuantity() != 0);

		if (allAvailable)
			intervention.getRequiredProducts().stream().forEach(p -> productService.useOne(p));

		else {

			List<String> productsUnavailable = intervention.getRequiredProducts().stream()
					.filter(p -> p.getQuantity() == 0).map(p -> p.getName()).collect(Collectors.toList());

			result.rejectValue("requiredProducts", "notEnoughProduct", "There aren't enough of " + productsUnavailable);
			model.put("intervention", intervention);
			
		}
		/////////////////////

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
		Intervention intervention = this.interventionService.findInterventionWithVisitAndProductsById(interventionId);
		model.put("vets", this.interventionService.getAvailableVets(intervention.getVisit().getDate()));
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
			model.put("vets", this.interventionService.getAvailableVets(visit.getDate()));
			model.put("intervention", intervention);
			return VIEWS_INTERVENTIONS_CREATE_OR_UPDATE_FORM;
		} else {

			Intervention interventionToUpdate = this.interventionService.findInterventionWithVisitAndProductsById(interventionId);

			List<Product> oldProducts = interventionToUpdate.getRequiredProducts();
			oldProducts.stream().forEach(p -> productService.addOne(p));

			BeanUtils.copyProperties(intervention, interventionToUpdate, "id", "visit");

			if (interventionToUpdate.getVet() == null) {
				model.put("noVetError", "You must choose a vet.");
				model.put("intervention", intervention);
				return VIEWS_INTERVENTIONS_CREATE_OR_UPDATE_FORM;
			}

			Boolean allAvailable = interventionToUpdate.getRequiredProducts().stream()
					.allMatch(p -> p.getQuantity() != 0);

			if (allAvailable)
				interventionToUpdate.getRequiredProducts().stream().forEach(p -> productService.useOne(p));

			else {

				List<String> productsUnavailable = intervention.getRequiredProducts().stream()
						.filter(p -> p.getQuantity() == 0).map(p -> p.getName()).collect(Collectors.toList());

				model.put("notEnoughError", "There aren't enough of " + productsUnavailable);
				model.put("intervention", intervention);
				return VIEWS_INTERVENTIONS_CREATE_OR_UPDATE_FORM;

			}

			this.interventionService.saveIntervention(interventionToUpdate);

			return "redirect:/owners/{ownerId}/pets/{petId}";
		}
	}

	@GetMapping(value = "interventions/{interventionId}/delete")
	public String deleteIntervention(@PathVariable("interventionId") int interventionId,
			@PathVariable("visitId") int visitId, ModelMap model) {

		Visit visit = visitService.findVisitById(visitId);
		visit.setIntervention(null);
		visitService.saveVisit(visit);

		Intervention intervention = interventionService.findInterventionWithProductsById(interventionId);

		for (Product p : intervention.getRequiredProducts())
			productService.addOne(p);
		interventionService.deleteIntervention(intervention);

		return "redirect:/owners/{ownerId}/pets/{petId}";
	}

	

}
