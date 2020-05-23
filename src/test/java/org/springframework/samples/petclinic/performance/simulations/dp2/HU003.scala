package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU003 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.png""", """.*.css""", """.*.ico""", """.*.jpg""", """.*.js"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_2 = Map("Origin" -> "http://www.dp2.com")



	val scn = scenario("HU003")
		.exec(http("request_0")
			.get("/"))
		.pause(3)
		// Home
		.exec(http("request_1")
			.get("/login"))
		.pause(7)
		// Login
		.exec(http("request_2")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "vet1")
			.formParam("password", "v3t")
			.formParam("_csrf", "4174e483-0ca4-4596-9327-df40b53af345"))
		.pause(4)
		// Logged
		.exec(http("request_3")
			.get("/owners/find"))
		.pause(7)
		// Find_Owners
		.exec(http("request_4")
			.get("/owners?lastName="))
		.pause(6)
		// Show_Owners
		.exec(http("request_5")
			.get("/owners/1"))
		.pause(5)
		// Show_Owner
		.exec(http("request_6")
			.get("/owners/1/pets/1"))
		.pause(14)
		// Show_Pet
		.exec(http("request_7")
			.get("/owners/1/pets/1/visits/11/interventions/7/edit"))
		.pause(24)
		// Edit_Intervention
		.exec(http("request_8")
			.post("/owners/1/pets/1/visits/11/interventions/7/edit")
			.headers(headers_2)
			.formParam("name", "Edited Intervention")
			.formParam("vet", "5")
			.formParam("requiredProducts", "3")
			.formParam("_requiredProducts", "1")
			.formParam("_csrf", "81dc1293-b4f4-4640-a7e7-5c931594efdc"))
		.pause(6)
		// Edited_Intervention

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}