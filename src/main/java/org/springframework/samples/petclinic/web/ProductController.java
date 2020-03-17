package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Provider;
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
	public String createProduct(ModelMap modelMap) {
		String view = "products/editProduct";
		modelMap.addAttribute("product", new Product());
		return view;
	}
	
	@PostMapping(path="/save")
	public String saveProduct(@Valid Product product, BindingResult result, ModelMap modelMap) {
		String view = "products/productsList";

		if(result.hasErrors()) {
			modelMap.addAttribute("product", product);
			return "products/editProduct";
			
		}else {
			productService.save(product);
			modelMap.addAttribute("message", "Product saved successfully!");
			view = productsList(modelMap);
		}
		return view;
	}
	
}
