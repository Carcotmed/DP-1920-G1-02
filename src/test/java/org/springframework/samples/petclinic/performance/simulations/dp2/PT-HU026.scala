import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class PT_HU026 extends Simulation {

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
          .formParam("username", "owner1")
          .formParam("password", "0wn3r")
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
  object Details3 {
    val details3 = exec(
      http("Details3")
        .get("/events/3")
        .headers(headers_0)
    ).pause(5)
  }

  object ParticipationForm {
    val participationForm = exec(
      http("ParticipationForm")
        .get("/events/newParticipation/3")
        .headers(headers_0)
        .check(css("input[name=_csrf]", "value").saveAs("stoken"))
    ).pause(11)
      .exec(
        http("ParticipationDone")
          .post("/events/newParticipation/3")
          .headers(headers_3)
          .formParam("pets", "16")
          .formParam("_pets", "1")
          .formParam("_csrf", "${stoken}")
      )
      .pause(7)
  }

  val NewParticipation = scenario("HU026").exec(
    Home.home,
    Login.login,
    Events.events,
    Details3.details3,
    ParticipationForm.participationForm
  )

  setUp(
    NewParticipation.inject(rampUsers(200) during (10 seconds))
  ).protocols(httpProtocol)
    .assertions(
      global.responseTime.max.lt(5000),
      global.responseTime.mean.lt(1000),
      global.successfulRequests.percent.gt(95)
    )
}
