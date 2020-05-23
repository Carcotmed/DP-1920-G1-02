package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU007 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.png""", """.*.css""", """.*.ico""", """.*.jpg""", """.*.js"""), WhiteList())
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3",
		"Origin" -> "http://www.dp2.com",
		"Upgrade-Insecure-Requests" -> "1")


	val scn = scenario("HU007")
		.exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(4)
		// Home
		
		.exec(http("Login")
			.get("/login")
			.headers(headers_0))
		.pause(9)
		// Login
		
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "vet1")
			.formParam("password", "v3t")
			.formParam("_csrf", "056431fe-a6da-44ee-91be-139809d04207"))
		.pause(14)
		// Logged
		
		.exec(http("Find_Owners")
			.get("/owners/find")
			.headers(headers_0))
		.pause(5)
		// Find_Owners
		
		.exec(http("Show_Owners")
			.get("/owners?lastName=")
			.headers(headers_0))
		.pause(10)
		// Show_Owners
		
		.exec(http("Show_Owner")
			.get("/owners/7")
			.headers(headers_0))
		.pause(5)
		// Show_Owner
		
		.exec(http("Show_Pet")
			.get("/owners/7/pets/9")
			.headers(headers_0))
		.pause(5)
		// Show_Pet
		
		.exec(http("Urgent_Visit")
			.get("/owners/7/pets/9/visits/addUrgentVisit")
			.headers(headers_0))
		.pause(17)
		// Urgent_Visit
		
		.exec(http("Added_Intervention")
			.post("/owners/7/pets/9/visits/11/interventions/new")
			.headers(headers_2)
			.formParam("name", "Test Intervention")
			.formParam("vet", "2")
			.formParam("requiredProducts", "2")
			.formParam("_requiredProducts", "1")
			.formParam("_csrf", "bd150cb9-5b18-4cdd-949f-c3c84ee8e81a"))
		.pause(5)
		// Added_Intervention

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}