package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU001 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.png""", """.*.css""", """.*.ico""", """.*.jpg""", """.*.js"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_2 = Map("Origin" -> "http://www.dp2.com")



	val scn = scenario("HU001")
		.exec(http("Home")
			.get("/"))
		.pause(5)
		// Home
		
		.exec(http("Login")
			.get("/login"))
		.pause(22)
		// Login
		
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "67cd5501-4c75-4fd7-820f-63c9c986756e"))
		.pause(7)
		// Logged
		
		.exec(http("Find_Owners")
			.get("/owners/find"))
		.pause(7)
		// Find Owners
		
		.exec(http("Show_Owners")
			.get("/owners?lastName="))
		.pause(7)
		// Show Owners
		
		.exec(http("Show_Owner")
			.get("/owners/1"))
		.pause(5)
		// Show Owner
		
		.exec(http("Show_Pet")
			.get("/owners/1/pets/1"))
		.pause(5)
		// Show Pet

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}