package org.springframework.samples.petclinic.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Base64;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.service.ImgurAPIService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/owners/{ownerID}/pets/{petID}/images")
public class ImgurAPIController {

	private ImgurAPIService apiService;
	private PetService petService;

	private static final String VIEWS_IMAGE_CREATE_FORM = "images/createImageForm";

	@Autowired
	private ImgurAPIController(ImgurAPIService apiService, PetService petService) {
		this.apiService = apiService;
		this.petService = petService;
	}

	@GetMapping("/uploadImage")
	public String test(ModelMap model) {

		return VIEWS_IMAGE_CREATE_FORM;

	}

	@PostMapping(value = "/uploadImage")
	public String processCreationForm(@PathVariable("petID") int petId, @RequestParam("image") MultipartFile image, ModelMap model) throws Exception {

		System.out.println(image.getName());
		
		InputStream inputStream =  new BufferedInputStream(image.getInputStream());
		
		byte[] bytesArray = new byte[(int) image.getSize()];

		inputStream.read(bytesArray);

		String imageBase64 = Base64.getEncoder().encodeToString(bytesArray);

		System.out.println("Base64: "+imageBase64);
		
		String imageURL = this.apiService.uploadImage(imageBase64, "Test");
		
		Pet pet = petService.findPetById(petId);
		
		pet.setImageURL(imageURL);
		
		petService.savePet(pet);
		
		return "redirect:/owners/{ownerID}/pets/{petID}";
	}

}
