package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU004 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.png""", """.*.css""", """.*.ico""", """.*.jpg""", """.*.js"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_2 = Map("Origin" -> "http://www.dp2.com")



	val scn = scenario("HU004")
		.exec(http("Home")
			.get("/"))
		.pause(3)
		// Home
		
		.exec(http("request_1")
			.get("/Login"))
		.pause(6)
		// Login
		
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "8ba7ab1d-ddab-427a-a462-debbe1649cd4"))
		.pause(3)
		// Logged
		
		.exec(http("Find_Owners")
			.get("/owners/find"))
		.pause(4)
		// Find_Owners
		
		.exec(http("Show_Owners")
			.get("/owners?lastName="))
		.pause(5)
		// Show_Owners
		
		.exec(http("Show_Owner")
			.get("/owners/1"))
		.pause(5)
		// Show_Owner
		
		.exec(http("Show_Pet")
			.get("/owners/1/pets/1"))
		.pause(11)
		// Show_Pet
		
		.exec(http("Deleted_Intervention")
			.get("/owners/1/pets/1/visits/5/interventions/2/delete"))
		.pause(6)
		// Deleted_Intervention

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}