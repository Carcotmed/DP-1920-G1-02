package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.service.ProviderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/providers")
public class ProviderController {

	@Autowired
	private ProviderService providerService;

	private static final String VIEWS_PROVIDERS_CREATE_OR_UPDATE_FORM = "providers/createOrUpdateProviderForm";

	@RequestMapping()
	public String ProductsList(ModelMap modelMap) {
		String vista = "providers/providersList";
		Iterable<Provider> providers = providerService.findAll();
		modelMap.addAttribute("providers", providers);
		return vista;
	}

	@GetMapping(value = "/new")
	public String initCreationForm(ModelMap model) {
		Provider provider = new Provider();
		model.put("provider", provider);
		return VIEWS_PROVIDERS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid Provider provider, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.put("provider", provider);
			return VIEWS_PROVIDERS_CREATE_OR_UPDATE_FORM;
		} else {
			this.providerService.saveProvider(provider);
			return "redirect:/providers";
		}
	}
	
	
	
	@GetMapping(value = "{providerId}/edit")
	public String initUpdateForm(@PathVariable("providerId") int providerId, ModelMap model) {
		Provider provider = this.providerService.findProviderById(providerId);
		model.put("provider", provider);
		return VIEWS_PROVIDERS_CREATE_OR_UPDATE_FORM;
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
	@PostMapping(value = "{providerId}/edit")
	public String processUpdateForm(@Valid Provider provider, BindingResult result, @PathVariable("providerId") int providerId,
			ModelMap model) {
		if (result.hasErrors()) {
			model.put("provider", provider);
			return VIEWS_PROVIDERS_CREATE_OR_UPDATE_FORM;
		} else {
			Provider providerToUpdate = this.providerService.findProviderById(providerId);
			BeanUtils.copyProperties(provider, providerToUpdate, "id");
			
				this.providerService.saveProvider(providerToUpdate);
			
			return "redirect:/providers";
		}
	}

}
