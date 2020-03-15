package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Discount;
import org.springframework.samples.petclinic.service.DiscountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/discounts")
public class DiscountController {

	@Autowired
	private DiscountService discountService;
	
	@RequestMapping()
	public String discountList(ModelMap modelMap) {
		String view= "discounts/discountsList";
		Iterable<Discount> discounts = discountService.findAll();
		modelMap.addAttribute("discounts", discounts);
		return view;
	}
	
}
