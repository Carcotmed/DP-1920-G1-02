package org.springframework.samples.petclinic.api;

import groovy.util.logging.Log;
import io.restassured.http.Header;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.samples.petclinic.model.api.ImgurResponse;

@Log
@TestMethodOrder(OrderAnnotation.class)
class ImgurAPITest {
	
	/*
	@Value("${imgurAPI.clientID}")
	private String clientID;
	*/
	private String clientID = "50dec69051074b2";
	
	private static String deleteHash;

	@Test
	@Order(1)
	void testUploadImage () {
		
		String trueClientID = "Client-ID " + clientID;
		Header header = new Header("Authorization", trueClientID);
		String url = "https://api.imgur.com/3/upload/";
		String imageName = "Test Image";
		
		ImgurResponse response =
				
		given()
			.request()
		.with()
			.header(header)
			.param("title", imageName)
			.param("image", getTestImage())
			.param("type", "base64")
		.when()
			.post(url)
		.then()
			.statusCode(200)
			.assertThat()
				.body("data.title", equalTo(imageName))
				.body("data.link", not(emptyOrNullString()))
				.body("data.deletehash", not(emptyOrNullString()))
				.extract().as(ImgurResponse.class);
		
		deleteHash = response.getData().getDeletehash();
		
	}

	@Test
	@Order(2)
	void testDeleteImage() {
		
		String trueClientID = "Client-ID " + clientID;
		Header header = new Header("Authorization", trueClientID);
		String url = "https://api.imgur.com/3/image/"+deleteHash;
				
		given()
			.request()
		.with()
			.header(header)
		.when()
			.delete(url)
		.then()
			.statusCode(200);
		
	}
	

	private static String getTestImage() {
		return "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"
				+ "v9TGU43+eP2/oVulgAbT8C2AABAf/Z";
	}

}
