package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class PTHU019 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
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
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "${stoken}")
		).pause(11)
	}

	object ProvidersList {
		val providersList = exec(http("ProvidersList")
			.get("/providers")
			.headers(headers_0))
		.pause(7)
	}
	
	object DiscountsList {
	val discountsList = exec(http("DiscountsList")
			.get("/discounts")
			.headers(headers_0))
		.pause(8)
	}
	
	object NewDiscountPos{
		val newDiscountPos = exec(http("NewDiscountPos")
			.get("/discounts/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]","value").saveAs("stoken"))
			).pause(18)
			.exec(http("NewDiscountCreated")
			.post("/discounts/new")
			.headers(headers_3)
			.formParam("percentage", "46.8")
			.formParam("quantity", "11")
			.formParam("provider", "1")
			.formParam("product", "1")
			.formParam("enabled", "true")
			.formParam("_csrf", "${stoken}")
			).pause(4)
	}
	
		object NewDiscountNeg{
		val newDiscountNeg = exec(http("NewDiscountNeg")
			.get("/discounts/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]","value").saveAs("stoken"))
			).pause(18)
			.exec(http("NewDiscountCreated")
			.post("/discounts/new")
			.headers(headers_3)
			.formParam("percentage", "46.8")
			.formParam("quantity", "-11")
			.formParam("provider", "1")
			.formParam("product", "1")
			.formParam("enabled", "true")
			.formParam("_csrf", "${stoken}")
			).pause(4)
	}


	val newDiscountPositive = scenario("HU019Pos").exec(Home.home,
															Login.login,
															ProvidersList.providersList,
															DiscountsList.discountsList,
															NewDiscountPos.newDiscountPos)
															
	val newDiscountNegative = scenario("HU019Neg").exec(Home.home,
															Login.login,
															ProvidersList.providersList,
															DiscountsList.discountsList,
															NewDiscountNeg.newDiscountNeg)

	setUp(
		newDiscountPositive.inject(rampUsers(200) during (10 seconds)),
		newDiscountNegative.inject(rampUsers(200) during (10 seconds))
		).protocols(httpProtocol)
		 .assertions(
		 	global.responseTime.max.lt(5000),
		 	global.responseTime.mean.lt(1000),
		 	global.successfulRequests.percent.gt(95)
		 )
}