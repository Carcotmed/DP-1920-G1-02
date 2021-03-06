package org.springframework.samples.petclinic.web;

import java.util.Collection;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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
import org.springframework.web.bind.annotation.PathVariable;
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
	public Collection<Product> populateProducts() {
		return this.productService.findProducts();
	}

	@ModelAttribute("providers")
	public Collection<Provider> populateProviders() {
		return this.providerService.findProviders();
	}

	@ModelAttribute("discounts")
	public Collection<Discount> populateDiscounts() {
		return this.discountService.findDiscounts();
	}

	@RequestMapping()
	public String ordersList(ModelMap modelMap) {
		String view = "orders/ordersList";
		Iterable<Order> orders = orderService.findAll();
		modelMap.addAttribute("orders", orders);
		return view;
	}

	@GetMapping("/new")
	public String initCreateOrder(ModelMap modelMap) {
		String view = "orders/editOrder";
		modelMap.addAttribute("order", new Order());
		return view;
	}

	@PostMapping("/new")
	public String processCreateForm(@Valid Order order, BindingResult result, ModelMap modelMap) {
		String view = "redirect:/orders";

		result = orderValidator(order, result, modelMap);

		if (result.hasErrors()) {
			modelMap.put("order", order);
			view = "orders/editOrder";
		} else if (order.getArrivalDate() != null && !order.getSent()) {
			order.setSent(true); // Si hay fecha de llegada y sent esta a false, lo pone a true
			this.orderService.save(order);
		} else {
			this.orderService.save(order);
		}
		return view;
	}

	@GetMapping("/edit/{orderId}")
	public String initUpdateForm(@PathVariable("orderId") int orderId, ModelMap modelMap) {
		String view = "orders/editOrder";
		Order order = this.orderService.findOrderById(orderId);
		if (order.getArrivalDate() != null) {
			view = "orders";
		} else {
			modelMap.put("order", order);
		}
		return view;
	}

	@PostMapping("/edit/{orderId}")
	public String processUpdateForm(@Valid Order order, BindingResult result, @PathVariable("orderId") int orderId,
			ModelMap modelMap) {

		result = orderValidator(order, result, modelMap);

		if (result.hasErrors()) {
			modelMap.put("order", order);
			return "orders/editOrder";
		} else if (order.getArrivalDate() != null && !order.getSent()) {
			order.setSent(true); // Si hay fecha de llegada y sent esta a false, lo pone a true
			this.orderService.save(order);
		} else {
			Order orderToUpdate = this.orderService.findOrderById(orderId);
			BeanUtils.copyProperties(order, orderToUpdate, "id");

			this.orderService.save(orderToUpdate);
		}
		return "redirect:/orders";
	}

	public BindingResult orderValidator(Order order, BindingResult result, ModelMap modelMap) {

		if (order.getArrivalDate() != null && order.getArrivalDate().isBefore(order.getOrderDate())) {
			result.rejectValue("arrivalDate", "arrivalDateBeforeError",
					"The arrival date can't be before the order date");
		}

		if (!this.productService.findAllByProviderId(order.getProvider().getId()).contains(order.getProduct())) {
			result.rejectValue("provider", "productNotProvidedError",
					"The selected provider doesn't provide the selected product");
		}

		if (!this.discountService.findAllByProductId(order.getProduct().getId()).contains(order.getDiscount())) {
			result.rejectValue("discount", "discountDoesntApplyProductError",
					"The selected discount doesn't apply to the selected product");

		}

		if (!this.discountService.findAllByProviderId(order.getProvider().getId()).contains(order.getDiscount())) {
			result.rejectValue("discount", "discountDoesntApplyProductError",
					"The selected discount doesn't apply for the selected provider");
			
		}
		
		return result;
	}

	@GetMapping("/delete/{orderId}")
	public String deleteOrder(@PathVariable("orderId") int orderId, ModelMap modelMap) {
		Order order = orderService.findOrderById(orderId);
		if (order.getSent() == false) {
			orderService.deleteOrder(order);
		}
		return ordersList(modelMap);

	}

}
