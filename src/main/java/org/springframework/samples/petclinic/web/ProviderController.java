package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.service.ProductService;
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

	@Autowired
	private ProductService productService;

	private static final String VIEWS_PROVIDERS_CREATE_OR_UPDATE_FORM = "providers/createOrUpdateProviderForm";

	@RequestMapping()
	public String ProvidersList(ModelMap modelMap) {

		String vista = "providers/providersList";
		Collection<Provider> providers = providerService.findProviders();
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

	@PostMapping(value = "{providerId}/edit")
	public String processUpdateForm(@Valid Provider provider, BindingResult result,
			@PathVariable("providerId") int providerId, ModelMap model) {
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

	@GetMapping(value = "{providerId}/delete")
	public String deleteProvider(@PathVariable("providerId") int providerId, ModelMap model) {

		List<Product> productsOfProvider = new ArrayList<Product>(productService.findAllByProviderId(providerId));

		if (productsOfProvider.isEmpty()) {

			Provider provider = providerService.findProviderById(providerId);
			providerService.deleteProvider(provider);

		} else {

			model.addAttribute("deleteError", "Hay productos relacionados con el proveedor");

		}

		return ProvidersList(model);
	}

}
