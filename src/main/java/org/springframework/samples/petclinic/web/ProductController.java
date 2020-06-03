package org.springframework.samples.petclinic.web;

import java.util.Collection;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {
	
	private String product = "product";
	private String editURL= "products/editProduct";

	
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
		String view = editURL;
		Product product = new Product();
		product.setEnabled(true);
		modelMap.addAttribute(this.product, product);
		return view;
	}
	
	@PostMapping("/new")
	public String processCreateForm(@Valid Product product, BindingResult result, ModelMap modelMap) {	
		if (result.hasErrors()) {
			if(product.getQuantity() != null) {
				product.setQuantity(null);
			}
			modelMap.put(this.product, product);
			return editURL;
		} else {
			this.productService.save(product);
		}
		return "redirect:/products";
	}
	
	@GetMapping("/edit/{productId}")
	public String initUpdateForm(@PathVariable("productId") int productId, ModelMap modelMap) {
		String view = editURL;
		Product product = this.productService.findProductById(productId);
		modelMap.put(this.product, product);
		return view;
	}
	
	@PostMapping("/edit/{productId}")
	public String processUpdateForm(@Valid Product product, BindingResult result,
			@PathVariable("productId") int productId, ModelMap modelMap) {
		if (result.hasErrors()) {
			modelMap.put(this.product, product);
			return editURL;
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
		product.setEnabled(false);
		productService.save(product);
		return productsList(modelMap);

	}
	
}
