package org.springframework.samples.petclinic.web;

import java.security.Provider;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Discount;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.service.DiscountService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.service.ProviderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/discounts")
public class DiscountController {

	@Autowired
	private DiscountService discountService;
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private ProductService productService;
	
	@ModelAttribute("providers")
	public Collection<Provider> populateProviders() {
		return this.providerService.findProviders();
	}

	@ModelAttribute("products")
	public Collection<Product> populateProducts() {
		return this.productService.findProducts();
	}
	
	@RequestMapping()
	public String discountsList(ModelMap modelMap) {
		String view= "discounts/discountsList";
		Iterable<Discount> discounts = discountService.findAll();
		modelMap.addAttribute("discounts", discounts);
		return view;
	}
	
	@GetMapping("/new")
	public String createDiscount(ModelMap modelMap) {
		String view = "discounts/editDiscount";
		modelMap.addAttribute("discount", new Discount());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveDiscount(@Valid Discount discount, BindingResult result, ModelMap modelMap) {
		String view = "discounts/discountsList";
		if(result.hasErrors()) {
			modelMap.addAttribute("discount", discount);
			return "discounts/editDiscount";
		}else {
			discountService.save(discount);
			modelMap.addAttribute("message", "Discount saved successfully!");
			view = discountsList(modelMap);
		}
		return view;
	}
	
}
