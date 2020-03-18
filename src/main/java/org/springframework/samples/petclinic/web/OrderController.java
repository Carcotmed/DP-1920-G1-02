package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Discount;
import org.springframework.samples.petclinic.model.Order;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.service.DiscountService;
import org.springframework.samples.petclinic.service.OrderService;
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
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private DiscountService discountService;
	
	@ModelAttribute("products")
	public Collection<Product> populateProducts(){
		return this.productService.findProducts();
	}
	
	@ModelAttribute("providers")
	public Collection<Provider> populateProviders(){
		return this.providerService.findProviders();
	}
	
	@ModelAttribute("discounts")
	public Collection<Discount> populateDiscounts(){
		return this.discountService.findDiscounts();
	}
	
	@RequestMapping()
	public String ordersList(ModelMap modelMap) {
		String view= "orders/ordersList";
		Iterable<Order> orders = orderService.findAll();
		modelMap.addAttribute("orders", orders);
		return view;
	}
	
	@GetMapping("/new")
	public String createOrder(ModelMap modelMap) {
		String view = "orders/editOrder";
		modelMap.addAttribute("order", new Order());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveOrder(@Valid Order order, BindingResult result, ModelMap modelMap) {
		String view = "orders/ordersList";
		if(result.hasErrors()) {
			modelMap.addAttribute("order", order);
			return "orders/editOrder";
		}else {
			orderService.save(order);
			modelMap.addAttribute("message", "Order saved successfully!");
			view = ordersList(modelMap);
		}
		return view;
	}
	
}
