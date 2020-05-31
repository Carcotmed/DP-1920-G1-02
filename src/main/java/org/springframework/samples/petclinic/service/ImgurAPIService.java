package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.api.ImgurResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class ImgurAPIService {

	private final static String imageEndpoint = "https://api.imgur.com/3/";
	private final static String imageUploadEndpoint = imageEndpoint + "upload/";
	private final static String imageDeleteEndpoint = imageEndpoint + "image/";
	
	@Value("${imgurAPI.clientID}")
	private String clientID;

	@Autowired
	private PetService petService;

	@Autowired
	private RestTemplate restTemplate;

	public ImgurResponse uploadImage(String imageBase64, String imageName) throws Exception {
				
		HttpHeaders headers = new HttpHeaders();
		String trueClientID = "Client-ID " + clientID;
		headers.add("Authorization", trueClientID);

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("name", imageName);
		body.add("image", imageBase64);
		body.add("type", "base64");
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		
		ResponseEntity<ImgurResponse> response = restTemplate.postForEntity(imageUploadEndpoint, requestEntity, ImgurResponse.class);
		
		ImgurResponse imgurResponse = response.getBody();
				
		return imgurResponse;
		
	}
	
	public void deleteImage(String deleteHash) throws Exception {
								
		String deleteUrl = imageDeleteEndpoint+deleteHash;
						
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		String trueClientID = "Client-ID " + clientID;
		System.out.println(trueClientID);
		headers.add("Authorization", trueClientID);
		
		HttpEntity<?> request = new HttpEntity<Object>(headers);
		restTemplate.exchange(deleteUrl, HttpMethod.DELETE, request, String.class);
								
	}

}
