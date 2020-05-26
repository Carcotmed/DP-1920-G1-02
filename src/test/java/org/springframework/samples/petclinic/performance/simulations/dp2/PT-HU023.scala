import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class PT_HU023 extends Simulation {

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
          .formParam("username", "vet1")
          .formParam("password", "v3t")
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
  object Details4 {
    val details4 = exec(
      http("Details4")
        .get("/events/4")
        .headers(headers_0)
    ).pause(6)
  }
  object EditForm {
    val editForm = exec(
      http("EditForm")
        .get("/events/edit/4")
        .headers(headers_0)
        .check(css("input[name=_csrf]", "value").saveAs("stoken"))
    ).pause(13)
      .exec(
        http("Created")
          .post("/events/new")
          .headers(headers_3)
          .formParam("date", "2020/05/27")
          .formParam("description", "DescripcionPerformance")
          .formParam("capacity", "8")
          .formParam("place", "PlacePerformance")
          .formParam("_csrf", "${stoken}")
      )
      .pause(4)
      .exec(
        http("Updated")
          .post("/events/edit/4")
          .headers(headers_3)
          .formParam("date", "2031/03/09")
          .formParam("description", "DescripcionPerformance2")
          .formParam("capacity", "40")
          .formParam("place", "Place4")
          .formParam("_csrf", "${stoken}")
      )
  }
  object EditFormNeg {
    val editFormNeg = exec(
      http("EditForm")
        .get("/events/edit/4")
        .headers(headers_0)
        .check(css("input[name=_csrf]", "value").saveAs("stoken"))
    ).pause(13)
      .exec(
        http("Created")
          .post("/events/new")
          .headers(headers_3)
          .formParam("date", "2020/05/27")
          .formParam("description", "DescripcionPerformance")
          .formParam("capacity", "8")
          .formParam("place", "PlacePerformance")
          .formParam("_csrf", "${stoken}")
      )
      .pause(4)
      .exec(
        http("Updated")
          .post("/events/edit/4")
          .headers(headers_3)
          .formParam("date", "2000/03/08")
          .formParam("description", "DescripcionPerformance2")
          .formParam("capacity", "40")
          .formParam("place", "Place4")
          .formParam("_csrf", "${stoken}")
      )
  }

  val EditEventPositive = scenario("HU023Pos").exec(
    Home.home,
    Login.login,
    Events.events,
    Details4.details4,
    EditForm.editForm
  )

  val EditEventNegative = scenario("HU023Neg").exec(
    Home.home,
    Login.login,
    Events.events,
    Details4.details4,
    EditFormNeg.editFormNeg
  )

  setUp(
    EditEventPositive.inject(rampUsers(200) during (10 seconds)),
    EditEventNegative.inject(rampUsers(200) during (10 seconds))
  ).protocols(httpProtocol)
    .assertions(
      global.responseTime.max.lt(5000),
      global.responseTime.mean.lt(1000),
      global.successfulRequests.percent.gt(95)
    )
}
