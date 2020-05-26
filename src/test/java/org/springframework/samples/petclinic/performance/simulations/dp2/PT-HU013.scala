package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class PT_HU013 extends Simulation {

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

  val headers_2 = Map("Origin" -> "http://www.dp2.com")

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
          .headers(headers_2)
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

  object CreateProvider {
    val createProvider = exec(
      http("CreateProvider")
        .get("/providers/new")
        .check(css("input[name=_csrf]", "value").saveAs("createToken"))
    ).pause(29)
  }

  object SubmitProviderPositive {
    val submitProviderPos = exec(
      http("SubmitProviderPositive")
        .post("/providers/new")
        .headers(headers_2)
        .formParam("name", "Test Provider")
        .formParam("address", "C/Test N20")
        .formParam("phone", "684251476")
        .formParam("email", "test@gmail.com")
        .formParam("_csrf", "${createToken}")
    ).pause(14)
  }

  object SubmitProviderNegative {
    val submitProviderNeg = exec(
      http("SubmitProviderNegative")
        .post("/providers/new")
        .headers(headers_2)
        .formParam("name", "")
        .formParam("address", "")
        .formParam("phone", "123456789654123351")
        .formParam("email", "asd")
        .formParam("_csrf", "${createToken}")
    ).pause(7)
  }

  val createProviderPos = scenario("HU013_P").exec(
    Home.home,
    Login.login,
    ShowProviders.showProviders,
    CreateProvider.createProvider,
    SubmitProviderPositive.submitProviderPos
  )

  val createProviderNeg = scenario("HU013_N").exec(
    Home.home,
    Login.login,
    ShowProviders.showProviders,
    CreateProvider.createProvider,
    SubmitProviderNegative.submitProviderNeg
  )

  setUp(
    createProviderPos.inject(rampUsers(1700) during (10 seconds)),
    createProviderNeg
      .inject(rampUsers(1700) during (10 seconds))
  ).protocols(httpProtocol)
    .assertions(
      global.responseTime.max.lt(5000),
      global.responseTime.mean.lt(1000),
      global.successfulRequests.percent.gt(95)
    )
}