package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class STHU017 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.jpg""", """.*.jsp""", """.*.ico""", """.*.png"""), WhiteList())
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
		var login = exec(
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
	
	object OrdersList {
		var ordersList = exec(http("OrdersList")
			.get("/orders")
			.headers(headers_0)
			.check(css("input[name=_csrf]","value").saveAs("stoken"))
			).pause(11)
	}

	object EditOrderPos{
		var editOrderPos = exec(http("EditOrderPos")
			.get("/orders/edit/1")
			.headers(headers_0))
		.pause(14)
		.exec(http("OrderEdited")
			.post("/orders/edit/1")
			.headers(headers_3)
			.formParam("product", "1")
			.formParam("quantity", "40")
			.formParam("provider", "1")
			.formParam("discount", "1")
			.formParam("orderDate", "2013/01/01")
			.formParam("arrivalDate", "")
			.formParam("sent", "true")
			.formParam("_sent", "on")
			.formParam("_csrf", "${stoken}")
			).pause(5)
	}
	
	
	object EditOrderNeg{
		var editOrderNeg = exec(http("EditOrderNeg")
			.get("/orders/edit/1")
			.headers(headers_0))
		.pause(14)
		.exec(http("OrderEdited")
			.post("/orders/edit/1")
			.headers(headers_3)
			.formParam("product", "1")
			.formParam("quantity", "-40")
			.formParam("provider", "1")
			.formParam("discount", "1")
			.formParam("orderDate", "2013/01/01")
			.formParam("arrivalDate", "")
			.formParam("sent", "true")
			.formParam("_sent", "on")
			.formParam("_csrf", "${stoken}")
			).pause(5)
	}


	val editOrderPositive = scenario("HU017Pos").exec(Home.home,
												Login.login,
												OrdersList.ordersList,
												EditOrderPos.editOrderPos)
												
	val editOrderNegative = scenario("HU017Neg").exec(Home.home,
												Login.login,
												OrdersList.ordersList,
												EditOrderNeg.editOrderNeg)
		


setUp(
		editOrderPositive.inject(atOnceUsers(1)),
		editOrderNegative.inject(atOnceUsers(1))
		).protocols(httpProtocol)
		 
}