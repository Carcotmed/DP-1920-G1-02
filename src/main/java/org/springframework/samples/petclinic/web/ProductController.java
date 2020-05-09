package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Discount;
import org.springframework.samples.petclinic.model.Order;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Provider;
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
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProviderService providerService;

	@ModelAttribute("providers")
	public Collection<Provider> populateProviders(){
		return this.providerService.findProviders();
	}
	
	@RequestMapping()
	public String productsList(ModelMap modelMap) {
		String vista = "products/productsList";
		Iterable<Product> products = productService.findAll();
		modelMap.addAttribute("products", products);
		return vista;
	}
	
	@GetMapping("/new")
	public String initCreateProduct(ModelMap modelMap) {
		String view = "products/editProduct";
		Product product = new Product();
		product.setEnabled(true);
		modelMap.addAttribute("product", product);
		return view;
	}
	
	@PostMapping("/new")
	public String processCreateForm(@Valid Product product, BindingResult result, ModelMap modelMap) {	
		if (result.hasErrors()) {
			if(product.getQuantity() != null) {
				product.setQuantity(null);
			}
			modelMap.put("product", product);
			return "products/editProduct";
		} else {
			this.productService.save(product);
		}
		return "redirect:/products";
	}
	
	@GetMapping("/edit/{productId}")
	public String initUpdateForm(@PathVariable("productId") int productId, ModelMap modelMap) {
		String view = "products/editProduct";
		Product product = this.productService.findProductById(productId);
		modelMap.put("product", product);
		return view;
	}
	
	@PostMapping("/edit/{productId}")
	public String processUpdateForm(@Valid Product product, BindingResult result,
			@PathVariable("productId") int productId, ModelMap modelMap) {
		if (result.hasErrors()) {
			modelMap.put("product", product);
			return "products/editProduct";
		} else {
			Product productToUpdate = this.productService.findProductById(productId);
			BeanUtils.copyProperties(product, productToUpdate, "id");

			this.productService.save(productToUpdate);
		}
		return "redirect:/products";
	}
	
	
	@GetMapping("/delete/{productId}")
	public String deleteProduct(@PathVariable("productId") int productId, ModelMap modelMap) {
		Product product = productService.findProductById(productId);
		modelMap.addAttribute("enabled",false);
		modelMap.addAttribute("product", product);
		return productsList(modelMap);

	}
	
}
