package org.springframework.samples.petclinic.api;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.elasticsearch.jest.HttpClientConfigBuilderCustomizer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.api.ImgurResponse;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.security.web.header.Header;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class ImgurAPIService {

	private final static String imageEndpoint = "https://api.imgur.com/3/";
	private final static String imageUploadEndpoint = imageEndpoint + "upload/";

	@Value("${clientID}")
	private static String clientID;

	@Autowired
	private PetService petService;

	@Autowired
	private RestTemplate restTemplate;

	public String uploadImage(File image) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.add("Authorization", "Client-ID " + clientID);

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", image);

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

		// ResponseEntity<ImgurResponse> asd =
		// restTemplate.postForEntity(imageUploadEndpoint, image, ImgurResponse.class);
		ResponseEntity<String> response = restTemplate.postForEntity(imageUploadEndpoint, requestEntity, String.class);

		System.out.println(response);
		
		return response.toString();
		
	}

}
