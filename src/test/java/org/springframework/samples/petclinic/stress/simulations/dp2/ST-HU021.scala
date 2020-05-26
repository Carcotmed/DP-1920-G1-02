package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class STHU021 extends Simulation {

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
	
	object LoginAd {
		val loginAd = exec(
			http("LoginAd")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]","value").saveAs("stoken"))
		).pause(13)
		.exec(
		http("LoggedAd")
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
	
	object DeleteDiscount {
		val deleteDiscount = exec(http("DeleteDiscount")
			.get("/discounts/delete/1")
			.headers(headers_0))
		.pause(6)
	}

	val deleteDiscountPositive = scenario("HU021Pos").exec(Home.home,
															LoginAd.loginAd,
															ProvidersList.providersList,
															DiscountsList.discountsList,
															DeleteDiscount.deleteDiscount)
															
	

	setUp(
		deleteDiscountPositive.inject(atOnceUsers(1))
		).protocols(httpProtocol)
		

}