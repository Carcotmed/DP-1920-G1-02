package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU002 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.png""", """.*.css""", """.*.ico""", """.*.jpg""", """.*.js"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_2 = Map("Origin" -> "http://www.dp2.com")



	val scn = scenario("HU002")
		.exec(http("Home")
			.get("/"))
		.pause(5)
		// Home
		
		.exec(http("Login")
			.get("/login"))
		.pause(11)
		// Login
		
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "vet1")
			.formParam("password", "v3t")
			.formParam("_csrf", "91520dd8-dd9c-4481-9f72-16fd1eeb6092"))
		.pause(10)
		// Logged
		
		.exec(http("Find_Owners")
			.get("/owners/find"))
		.pause(12)
		// Find_Owners
		
		.exec(http("Show_Owners")
			.get("/owners?lastName="))
		.pause(8)
		// Show_Owners
		
		.exec(http("Show_Owner")
			.get("/owners/6"))
		.pause(7)
		// Show_Owner
		
		.exec(http("Show_Pet")
			.get("/owners/6/pets/8"))
		.pause(18)
		// Show_Pet
		
		.exec(http("New_Intervention")
			.get("/owners/6/pets/8/visits/3/interventions/new"))
		.pause(29)
		// New_Intervention
		
		.exec(http("Added_Intervention")
			.post("/owners/6/pets/8/visits/3/interventions/new")
			.headers(headers_2)
			.formParam("name", "Test Intervention")
			.formParam("vet", "1")
			.formParam("requiredProducts", "1")
			.formParam("_requiredProducts", "1")
			.formParam("_csrf", "72fca0cf-3efb-4c63-969d-9041a070d43e"))
		.pause(8)
		// Added_Intervention

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}