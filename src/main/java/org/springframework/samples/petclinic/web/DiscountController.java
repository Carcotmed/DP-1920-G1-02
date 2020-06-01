package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Discount;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.service.DiscountService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.service.ProviderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/discounts")
public class DiscountController {

	private String editURL = "discounts/editDiscount";
	private String discount = "discount";
	
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
	public String initCreateDiscount(ModelMap modelMap) {
		String view = editURL;
		Discount discount = new Discount();
		discount.setEnabled(true);
		modelMap.addAttribute(this.discount, discount);
		return view;
	}
	
	@PostMapping("/new")
	public String processCreateForm(@Valid Discount discount, BindingResult result, ModelMap modelMap) {	
		if (result.hasErrors()) {
			modelMap.put(this.discount, discount);
			return editURL;
		} else {
			this.discountService.save(discount);
		}
		return "redirect:/discounts";
	}
	
	@GetMapping("/edit/{discountId}")
	public String initUpdateForm(@PathVariable("discountId") int discountId, ModelMap modelMap) {
		String view = editURL;
		Discount discount = this.discountService.findDiscountById(discountId);
		modelMap.put(this.discount, discount);
		return view;
	}
	
	@PostMapping("/edit/{discountId}")
	public String processUpdateForm(@Valid Discount discount, BindingResult result,
			@PathVariable("discountId") int discountId, ModelMap modelMap) {
		if (result.hasErrors()) {
			modelMap.put(this.discount, discount);
			return editURL;
		} else {
			Discount discountToUpdate = this.discountService.findDiscountById(discountId);
			BeanUtils.copyProperties(discount, discountToUpdate, "id");

			this.discountService.save(discountToUpdate);
		}
		return "redirect:/discounts";
	}
	

	@GetMapping("/delete/{discountId}")
	public String deleteDiscount(@PathVariable("discountId") int discountId, ModelMap modelMap) {
		Discount discount = discountService.findDiscountById(discountId);
		discount.setEnabled(false);
		discountService.save(discount);
		return discountsList(modelMap);

	}
	
}
