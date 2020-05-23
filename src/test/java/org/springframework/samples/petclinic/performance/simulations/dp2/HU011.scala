package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU011 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources()
		.acceptEncodingHeader("gzip, deflate")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map(
		"A-IM" -> "x-bm,gzip",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_4 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

    val uri1 = "http://clientservices.googleapis.com/chrome-variations/seed"

	val scn = scenario("HU011")
		.exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(3)
		// Home
		.exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_3")
			.get("/favicon.ico")
			.headers(headers_3)))
		.pause(11)
		// Login
		.exec(http("Logged")
			.post("/login")
			.headers(headers_4)
			.formParam("username", "Vet1")
			.formParam("password", "v3t")
			.formParam("_csrf", "0cced408-af8e-43cf-af36-28976914907d"))
		.pause(7)
		// Logged
		.exec(http("Adoptions")
			.get("/adoptions")
			.headers(headers_0))
		.pause(9)
		// Adoptions
		.exec(http("AllAdoptions")
			.get("/adoptions/allAdoptions")
			.headers(headers_0))
		.pause(7)
		// AllAdoptions

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}