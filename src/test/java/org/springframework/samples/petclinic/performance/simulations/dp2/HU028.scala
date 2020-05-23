package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU028 extends Simulation {

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



	val scn = scenario("HU028")
		.exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(6)
		// Home
		.exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/favicon.ico")
			.headers(headers_2)))
		.pause(12)
		// Login
		.exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "e8881704-6c56-4951-92c3-2ef6c48004da"))
		.pause(16)
		// Logged
		.exec(http("Events")
			.get("/events")
			.headers(headers_0))
		.pause(43)
		// Events
		.exec(http("Details2")
			.get("/events/2")
			.headers(headers_0))
		.pause(12)
		// Details2
		.exec(http("NewSponsor")
			.get("/events/newSponsor/2")
			.headers(headers_0))
		.pause(11)
		// NewSponsor
		.exec(http("Sponsored")
			.post("/events/newSponsor/2")
			.headers(headers_3)
			.formParam("sponsor", "3")
			.formParam("_csrf", "c4b7afc8-a1db-44e2-8107-e439eb9d693b"))
		.pause(6)
		// Sponsored

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}