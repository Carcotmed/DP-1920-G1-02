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
import org.springframework.samples.petclinic.model.api.Data;
import org.springframework.samples.petclinic.model.api.ImgurResponse;
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
	public ImgurAPIController(ImgurAPIService apiService, PetService petService) {
		this.apiService = apiService;
		this.petService = petService;
	}

	@GetMapping("/uploadImage")
	public String test(ModelMap model) {

		return VIEWS_IMAGE_CREATE_FORM;

	}

	@PostMapping(value = "/uploadImage")
	public String processCreationForm(@PathVariable("petID") int petId, @RequestParam("image") MultipartFile image,
			ModelMap model) throws Exception {

		InputStream inputStream = new BufferedInputStream(image.getInputStream());

		byte[] bytesArray = new byte[(int) image.getSize()];

		inputStream.read(bytesArray);

		String imageBase64 = Base64.getEncoder().encodeToString(bytesArray);

		ImgurResponse response = this.apiService.uploadImage(imageBase64, "Test");

		Data responseData = response.getData();

		String imageUrl = responseData.getLink();
		String deleteHash = responseData.getDeletehash();

		Pet pet = petService.findPetById(petId);

		pet.setImageURL(imageUrl);
		pet.setImageDeleteHash(deleteHash);

		petService.savePet(pet);

		return "redirect:/owners/{ownerID}/pets/{petID}";
	}

	@GetMapping(value = "/deleteImage")
	public String processCreationForm(@PathVariable("petID") int petId, ModelMap model) throws Exception {

		Pet pet = petService.findPetById(petId);

		this.apiService.deleteImage(pet.getImageDeleteHash());

		pet.setImageURL(null);
		pet.setImageDeleteHash(null);

		petService.savePet(pet);

		return "redirect:/owners/{ownerID}/pets/{petID}";
	}

}
