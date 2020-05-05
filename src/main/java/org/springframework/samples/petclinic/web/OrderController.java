package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.List;
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

		if (result.hasErrors() || orderValidator(order, result, modelMap) != null) {
			modelMap.addAllAttributes(orderValidator(order, result, modelMap));
			modelMap.put("order", order);
			view = "orders/editOrder";
		} else if (order.getArrivalDate() != null) {
			if (!order.getSent()) { // Si hay fecha de llegada y sent esta a false, lo pone a true
				order.setSent(true);
				modelMap.put("order", order);
			}
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
		if (result.hasErrors() || orderValidator(order, result, modelMap) != null) {
			modelMap.addAllAttributes(orderValidator(order, result, modelMap));
			modelMap.put("order", order);
			return "orders/editOrder";
		} else if (order.getArrivalDate() != null) {
			if (!order.getSent()) { // Si hay fecha de llegada y sent esta a false, lo pone a true
				order.setSent(true);
				modelMap.put("order", order);
			}
		} else {
			Order orderToUpdate = this.orderService.findOrderById(orderId);
			BeanUtils.copyProperties(order, orderToUpdate, "id");

			this.orderService.save(orderToUpdate);
		}
		return "redirect:/orders";
	}

	public ModelMap orderValidator(@Valid Order order, BindingResult result, ModelMap modelMap) {
		boolean hasAnyError = false;
		// Fallos
		int providerId = order.getProvider().getId();
		int productId = order.getProduct().getId();
		int discountId = order.getDiscount().getId();

		Collection<Product> productsByProvider = this.productService.findAllByProviderId(providerId);
		List<Integer> productsIdByProvider = productsByProvider.stream().map(x -> x.getId())
				.collect(Collectors.toList());

		Collection<Discount> discountsByProduct = this.discountService.findAllByProductId(productId);
		List<Integer> discountsIdByProduct = discountsByProduct.stream().map(x -> x.getId())
				.collect(Collectors.toList());

		Collection<Discount> discountsByProvider = this.discountService.findAllByProviderId(providerId);
		List<Integer> discountsIdByProvider = discountsByProvider.stream().map(x -> x.getId())
				.collect(Collectors.toList());

		boolean error1 = productsIdByProvider.contains(productId); /* el provider no tiene el producto elegido */
		boolean error2 = discountsIdByProduct
				.contains(discountId); /* el descuento elegido no es aplicable al producto */
		boolean error3 = discountsIdByProvider
				.contains(discountId); /* el descuento elegido no es aplicable al proveedor */

		if (!error1) {
			modelMap.addAttribute("createError", "The selected provider doesn't provide the selected product");
			hasAnyError = true;
		} else if (!error2) {
			modelMap.addAttribute("createError", "The selected discount doesn't apply to the selected product");
			hasAnyError = true;
		} else if (!error3) {
			modelMap.addAttribute("createError", "The selected provider doesn't provide the selected discount");
			hasAnyError = true;
		} else if (order.getArrivalDate() != null) {
			if (order.getArrivalDate().isBefore(order.getOrderDate())) { // Arrival date is after order date
				modelMap.addAttribute("createError", "The arrival date can't be before the order date");
				hasAnyError = true;
			}

		}
		if (!hasAnyError) {
				return null;
		}
		return modelMap;
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
