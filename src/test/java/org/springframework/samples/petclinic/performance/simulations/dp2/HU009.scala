package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU009 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources()
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")



	val scn = scenario("HU009")
		.exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(11)
		// Home
		.exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/favicon.ico")
			.headers(headers_2)))
		.pause(15)
		// Login
		.exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "e12211ec-21d2-4554-8c92-3fa80000485b"))
		.pause(8)
		// Logged
		.exec(http("Adoptions")
			.get("/adoptions")
			.headers(headers_0))
		.pause(11)
		// Adoptions
		.exec(http("Adoptable1")
			.get("/owners/11/pets/14")
			.headers(headers_0))
		.pause(19)
		// Adoptable1
		.exec(http("Adopt")
			.get("/adoptions/new/14")
			.headers(headers_0))
		.pause(8)
		// Adopt
		.exec(http("Adoptions2")
			.post("/adoptions/new/14")
			.headers(headers_3)
			.formParam("end", "")
			.formParam("_csrf", "8f8fd095-15f1-49ae-8a00-841f86dd8170"))
		.pause(11)
		// Adoptions2

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}