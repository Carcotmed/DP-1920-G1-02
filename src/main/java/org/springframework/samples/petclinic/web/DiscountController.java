package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	
	@GetMapping("/edit/{discountId}")
	public String initUpdateForm(@PathVariable("discountId") int discountId, ModelMap modelMap) {
		String view = "discounts/editDiscount";
		Discount discount = this.discountService.findDiscountById(discountId);
		modelMap.put("discount", discount);
		return view;
	}
	
	@PostMapping("/edit/{discountId}")
	public String processUpdateForm(@Valid Discount discount, BindingResult result,
			@PathVariable("discountId") int discountId, ModelMap modelMap) {
		if (result.hasErrors()) {
			return "discounts/editDiscount";
		} else {
			discount.setId(discountId);
//			this.discountService.save(discount);
		}
		return "/discounts/discountsList";
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

	@GetMapping("/delete/{discountId}")
	public String deleteDiscount(@PathVariable("discountId") int discountId, ModelMap modelMap) {
		Discount discount = discountService.findDiscountById(discountId);
		discountService.deleteDiscount(discount);
		return discountsList(modelMap);

	}
	
}
