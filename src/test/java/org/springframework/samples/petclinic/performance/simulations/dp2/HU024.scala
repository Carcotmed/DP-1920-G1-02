package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU024 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources()
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map("Proxy-Connection" -> "keep-alive")



	val scn = scenario("HU024")
		.exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(8)
		// Home
		.exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/favicon.ico")
			.headers(headers_2)))
		.pause(72)
		// Login
		.exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "vet1")
			.formParam("password", "v3t")
			.formParam("_csrf", "2884323a-8ecc-4309-91ce-72dc1f9a408b"))
		.pause(12)
		// Logged
		.exec(http("Events")
			.get("/events")
			.headers(headers_0))
		.pause(7)
		// Events
		.exec(http("Details4")
			.get("/events/4")
			.headers(headers_0))
		.pause(10)
		// Details4
		.exec(http("Details4Published")
			.get("/events/publish/4")
			.headers(headers_0))
		.pause(15)
		// Details4Published

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}