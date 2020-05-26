import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class PT_HU028 extends Simulation {

  val httpProtocol = http
    .baseUrl("http://www.dp2.com")
    .inferHtmlResources(
      BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""),
      WhiteList()
    )
    .acceptHeader(
      "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"
    )
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
    .userAgentHeader(
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36"
    )

  val headers_0 =
    Map("Proxy-Connection" -> "keep-alive", "Upgrade-Insecure-Requests" -> "1")

  val headers_2 = Map(
    "Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
    "Proxy-Connection" -> "keep-alive"
  )

  val headers_3 = Map(
    "Origin" -> "http://www.dp2.com",
    "Proxy-Connection" -> "keep-alive",
    "Upgrade-Insecure-Requests" -> "1"
  )

  object Home {
    val home = exec(
      http("Home")
        .get("/")
        .headers(headers_0)
    ).pause(7)
  }

  object Login {
    val login = exec(
      http("Login")
        .get("/login")
        .headers(headers_0)
        .check(css("input[name=_csrf]", "value").saveAs("stoken"))
    ).pause(11)
      .exec(
        http("Logged")
          .post("/login")
          .headers(headers_3)
          .formParam("username", "admin1")
          .formParam("password", "4dm1n")
          .formParam("_csrf", "${stoken}")
      )
      .pause(6)
  }
  object Events {
    val events = exec(
      http("Events")
        .get("/events")
        .headers(headers_0)
    ).pause(8)
  }
  object Details2 {
    val details2 = exec(
      http("Details2")
        .get("/events/2")
        .headers(headers_0)
    ).pause(6)
  }

  object SelectSponsor {
    val selectSponsor = exec(
      http("SelectSponsor")
        .get("/events/newSponsor/2")
        .headers(headers_0)
        .check(css("input[name=_csrf]", "value").saveAs("stoken"))
    ).pause(8)
      .exec(
        http("SponsorSelected")
          .post("/events/newSponsor/2")
          .headers(headers_3)
          .formParam("sponsor", "4")
          .formParam("_csrf", "${stoken}")
      )
      .pause(6)
  }
  object Details1 {
    val details1 = exec(
      http("Details1")
        .get("/events/1")
        .headers(headers_0)
    ).pause(12)
  }

  object SelectSponsorNeg {
    val selectSponsorNeg = exec(
      http("SelectSponsorNeg")
        .get("/events/newSponsor/1")
        .headers(headers_0)
    ).pause(8)
  }

  val SponsorSelectedPositive = scenario("HU028Pos").exec(
    Home.home,
    Login.login,
    Events.events,
    Details2.details2,
    SelectSponsor.selectSponsor
  )

  val SponsorSelectedNegative = scenario("HU028Neg").exec(
    Home.home,
    Login.login,
    Events.events,
    Details1.details1,
    SelectSponsorNeg.selectSponsorNeg
  )

  setUp(
    SponsorSelectedPositive.inject(rampUsers(200) during (10 seconds)),
    SponsorSelectedNegative.inject(rampUsers(200) during (10 seconds))
  ).protocols(httpProtocol)
    .assertions(
      global.responseTime.max.lt(5000),
      global.responseTime.mean.lt(1000),
      global.successfulRequests.percent.gt(95)
    )
}
