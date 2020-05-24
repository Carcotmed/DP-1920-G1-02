package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ST_HU014 extends Simulation {

  val httpProtocol = http
    .baseUrl("http://www.dp2.com")
    .inferHtmlResources(
      BlackList(
        """.*.png""",
        """.*.css""",
        """.*.ico""",
        """.*.jpg""",
        """.*.js"""
      ),
      WhiteList()
    )
    .acceptHeader(
      "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"
    )
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
    .upgradeInsecureRequestsHeader("1")
    .userAgentHeader(
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0"
    )

  val headers_1 = Map("Origin" -> "http://www.dp2.com")

  object Home {
    val home = exec(
      http("Home")
        .get("/")
    ).pause(3)
  }

  object Login {
    val login = exec(
      http("Login")
        .get("/login")
        .check(css("input[name=_csrf]", "value").saveAs("loginToken"))
    ).pause(7)
      .exec(
        http("Logged")
          .post("/login")
          .headers(headers_1)
          .formParam("username", "admin1")
          .formParam("password", "4dm1n")
          .formParam("_csrf", "${loginToken}")
      )
      .pause(4)
  }

  object ShowProviders {
    val showProviders = exec(
      http("ShowProviders")
        .get("/providers")
        
    ).pause(6)
  }

  object EditProvider {
    val editProvider = exec(
      http("EditProvider")
        .get("/providers/1/edit")
		.check(css("input[name=_csrf]", "value").saveAs("editToken"))
    ).pause(22)
  }

  object SubmitUpdatePositive {
    val submitProviderPositive = exec(
      http("SubmitUpdatePositive")
        .post("/providers/1/edit")
        .headers(headers_1)
        .formParam("name", "Pipo1 Updated")
        .formParam("address", "Calle Pipo N25")
        .formParam("phone", "123456789")
        .formParam("email", "pipo1Updated@gmail.com")
        .formParam("_csrf", "${editToken}")
    ).pause(14)
  }

  object SubmitUpdateNegative {
    val submitProviderNegative = exec(
      http("SubmitUpdateNegative")
        .post("/providers/1/edit")
        .headers(headers_1)
        .formParam("name", "")
        .formParam("address", "")
        .formParam("phone", "asd")
        .formParam("email", "pipo1Updated")
        .formParam("_csrf", "${editToken}")
    ).pause(9)
  }

  val updateProviderPositive = scenario("HU014_P").exec(
    Home.home,
    Login.login,
    ShowProviders.showProviders,
    EditProvider.editProvider,
    SubmitUpdatePositive.submitProviderPositive
  )

  val updateProviderNegative = scenario("HU014_N").exec(
    Home.home,
    Login.login,
    ShowProviders.showProviders,
    EditProvider.editProvider,
    SubmitUpdateNegative.submitProviderNegative
  )

  setUp(
    updateProviderPositive.inject(atOnceUsers(1)),
    updateProviderNegative.inject(atOnceUsers(1))
  ).protocols(httpProtocol)
}