package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU026 extends Simulation {

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



	val scn = scenario("HU026")
		.exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(7)
		// Home
		.exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/favicon.ico")
			.headers(headers_2)))
		.pause(13)
		// Login
		.exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "1ac25503-31b3-4a35-b1d3-90ca48f0fe88"))
		.pause(13)
		// Logged
		.exec(http("Events")
			.get("/events")
			.headers(headers_0))
		.pause(9)
		// Events
		.exec(http("Details3")
			.get("/events/3")
			.headers(headers_0))
		.pause(8)
		// Details3
		.exec(http("Participate3")
			.get("/events/newParticipation/3")
			.headers(headers_0))
		.pause(14)
		// Participate3
		.exec(http("ParticipationDone")
			.post("/events/newParticipation/3")
			.headers(headers_3)
			.formParam("pets", "1")
			.formParam("_pets", "1")
			.formParam("_csrf", "5cc035e5-9110-4863-9c25-18e82391a073"))
		.pause(17)
		// ParticipationDone

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}