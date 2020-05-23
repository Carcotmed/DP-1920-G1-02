package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU022 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources()
		.acceptHeader("image/webp,image/apng,image/*,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map("Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")



	val scn = scenario("HU022")
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
		.exec(http("AddEventForm")
			.get("/events/new")
			.headers(headers_0)
			.resources(http("request_6")
			.get("/webjars/jquery-ui/1.11.4/images/ui-bg_highlight-soft_100_eeeeee_1x100.png")
			.headers(headers_2)))
		.pause(14)
		// AddEventForm
		.exec(http("request_7")
			.get("/webjars/jquery-ui/1.11.4/images/ui-bg_gloss-wave_35_f6a828_500x100.png")
			.headers(headers_2)
			.resources(http("request_8")
			.get("/webjars/jquery-ui/1.11.4/images/ui-icons_ffffff_256x240.png")
			.headers(headers_2),
            http("request_9")
			.get("/webjars/jquery-ui/1.11.4/images/ui-bg_glass_100_f6f6f6_1x400.png")
			.headers(headers_2),
            http("request_10")
			.get("/webjars/jquery-ui/1.11.4/images/ui-bg_highlight-soft_75_ffe45c_1x100.png")
			.headers(headers_2),
            http("request_11")
			.get("/webjars/jquery-ui/1.11.4/images/ui-bg_glass_65_ffffff_1x400.png")
			.headers(headers_2),
            http("request_12")
			.get("/webjars/jquery-ui/1.11.4/images/ui-bg_glass_100_fdf5ce_1x400.png")
			.headers(headers_2),
            http("request_13")
			.get("/webjars/jquery-ui/1.11.4/images/ui-icons_ef8c08_256x240.png")
			.headers(headers_2)))
		.pause(40)
		.exec(http("Events2")
			.post("/events/new")
			.headers(headers_3)
			.formParam("date", "2020/08/22")
			.formParam("description", "Description")
			.formParam("capacity", "7")
			.formParam("place", "PlaceTest")
			.formParam("_csrf", "0773951f-3cc8-4b1e-bde0-4cef8b8f35a5"))
		.pause(41)
		// Events2

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}