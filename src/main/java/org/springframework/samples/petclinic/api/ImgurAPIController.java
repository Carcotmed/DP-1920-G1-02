package org.springframework.samples.petclinic.api;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class ImgurAPIController {
	
	private ImgurAPIService apiService;
	
	@Autowired
	private ImgurAPIController(ImgurAPIService apiService) {
		this.apiService = apiService;
	}
	
	
	@GetMapping ("/testUpload")
	public String testUpload () {
		
		File image = new File ("/DP-1920-G1-02/src/test/java/org/springframework/samples/petclinic/api/testImage.jpg");
		
		return apiService.uploadImage(image);
		
	}

}
