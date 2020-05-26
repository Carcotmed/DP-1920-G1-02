package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class STHU029 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.png""", """.*.js""", """.*.ico""", """.*.css"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.136 Safari/537.36")

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


	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(21)
	}
	
	object Login {
		val login = exec(
			http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]","value").saveAs("stoken"))
		).pause(13)
		.exec(
		http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "vet1")
			.formParam("password", "v3t")
			.formParam("_csrf", "${stoken}")
		).pause(11)
	}

	object ProductsList {
		val productsList = exec(http("ProductsList")
			.get("/products")
			.headers(headers_0))
		.pause(8)
	}

	object NewProductPos {
		val newProductPos = exec(http("NewProductPos")
					.get("/products/new")

			.headers(headers_0)

			.check(css("input[name=_csrf]","value").saveAs("stoken"))
		).pause(23)
		.exec(http("ProductCreated")
			.post("/products/new")
			.headers(headers_3)
			.formParam("name", "Pelota")
			.formParam("price", "8.00")
			.formParam("quantity", "27")
			.formParam("allAvailable", "true")
			.formParam("_allAvailable", "on")
			.formParam("provider", "1")
			.formParam("enabled", "true")
			.formParam("_csrf", "${stoken}"))
		.pause(4)
	}
	
	object NewProductNeg {
		val newProductNeg = exec(http("NewProductNeg")
				.get("/products/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]","value").saveAs("stoken"))
		).pause(23)
		.exec(http("ProductCreated")
			.post("/products/new")
			.headers(headers_3)
			.formParam("name", "Pelota")
			.formParam("price", "8.00")
			.formParam("quantity", "-27")
			.formParam("allAvailable", "true")
			.formParam("_allAvailable", "on")
			.formParam("provider", "1")
			.formParam("enabled", "true")
			.formParam("_csrf", "${stoken}"))
		.pause(4)
	}

	val newProductPositive = scenario("HU029Pos").exec(Home.home,
									Login.login,
									ProductsList.productsList,
									NewProductPos.newProductPos)								
	
	val newProductNegative = scenario("HU029Neg").exec(Home.home,
									Login.login,
									ProductsList.productsList,
									NewProductNeg.newProductNeg)							
	
	
	
	setUp(
		newProductPositive.inject(atOnceUsers(1)),
		newProductNegative.inject(atOnceUsers(1))
		).protocols(httpProtocol)
}