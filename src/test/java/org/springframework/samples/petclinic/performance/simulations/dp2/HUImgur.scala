package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HUImgur extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.png""", """.*.css""", """.*.ico""", """.*.jpg""", """.*.js"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_2 = Map("Origin" -> "http://www.dp2.com")

	val headers_8 = Map(
		"Content-Type" -> "multipart/form-data; boundary=---------------------------9058632306571096123661528748",
		"Origin" -> "http://www.dp2.com")



	val scn = scenario("HUImgur")
		.exec(http("Home")
			.get("/"))
		.pause(10)
		// Home
		
		.exec(http("Login")
			.get("/login"))
		.pause(10)
		// Login
		
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "8b19a906-d7ad-4e93-8d57-3d0aaf7e69f1"))
		.pause(4)
		// Logged
		
		.exec(http("Find_Owners")
			.get("/owners/find"))
		.pause(7)
		// Find_Owners
		
		.exec(http("Show_Owners")
			.get("/owners?lastName="))
		.pause(15)
		// Show_Owners
		
		.exec(http("Show_Owner")
			.get("/owners/1"))
		.pause(5)
		// Show_Owner
		
		.exec(http("Show_Pet")
			.get("/owners/1/pets/1"))
		.pause(5)
		// Show_Pet
		
		.exec(http("Add_Image")
			.get("/owners/1/pets/1/images/uploadImage"))
		.pause(13)
		// Add_Image
		
		.exec(http("Uploaded_Image")
			.post("/owners/1/pets/1/images/uploadImage")
			.headers(headers_8)
			.body(RawFileBody("dp2/huimgur/0008_request.dat")))
		.pause(10)
		// Uploaded_Image
		
		.exec(http("Deleted_Image")
			.get("/owners/1/pets/1/images/deleteImage"))
		.pause(7)
		// Deleted_Image

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}