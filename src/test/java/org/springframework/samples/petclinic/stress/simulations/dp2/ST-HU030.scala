package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class STHU030 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.png""", """.*.js""", """.*.ico""", """.*.css"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
		.upgradeInsecureRequestsHeader("1")
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
	
	
	object LoginOw {
		val loginOw = exec(
			http("LoginOw")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]","value").saveAs("stoken"))
		).pause(13)
		.exec(
		http("LoggedOw")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "${stoken}")
		).pause(11)
	}
	
	
	object LoginVet {
		val loginVet = exec(
			http("LoginVet")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]","value").saveAs("stoken"))
		).pause(13)
		.exec(
		http("LoggedVet")
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
	
	object DeleteProduct {
		val deleteProduct = exec(http("DeleteProduct")
			.get("/products/delete/1")
			.headers(headers_0))
		.pause(3)
	}
	
	val deleteProductPositive = scenario("HU030Pos").exec(Home.home,
									LoginVet.loginVet,
									ProductsList.productsList,
									DeleteProduct.deleteProduct)			
									
	val deleteProductNegative = scenario("HU030Neg").exec(Home.home,
									LoginOw.loginOw,
									ProductsList.productsList,
									DeleteProduct.deleteProduct)	
									
	
	setUp(
		deleteProductPositive.inject(atOnceUsers(1)),
		deleteProductNegative.inject(atOnceUsers(1))
		).protocols(httpProtocol)

}