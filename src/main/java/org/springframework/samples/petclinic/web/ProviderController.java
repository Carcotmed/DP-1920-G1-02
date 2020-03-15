package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.service.ProviderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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

}
