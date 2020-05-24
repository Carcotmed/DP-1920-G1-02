package dp2
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ST_HU001 extends Simulation {

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
    ).pause(8)
  }
  object Login {
    val login = exec(
      http("Login")
        .get("/login")
        .check(css("input[name=_csrf]", "value").saveAs("stoken"))
    ).pause(9)
      .exec(
        http("Logged")
          .post("/login")
          .headers(headers_2)
          .formParam("username", "owner1")
          .formParam("password", "0wn3r")
          .formParam("_csrf", "${stoken}")
      )
      .pause(3)
  }
  object FindOwners {
    val findOwners = exec(
      http("FindOwners")
        .get("/owners/find")
    ).pause(5)
  }
  object ShowOwners {
    val showOwners = exec(
      http("ShowOwners")
        .get("/owners?lastName=")
    ).pause(5)
  }
  object ShowOwner {
    val showOwner = exec(
      http("ShowOwner")
        .get("/owners/1")
    ).pause(4)
  }
  object ShowPet {
    val showPet = exec(
      http("ShowPet")
        .get("/owners/1/pets/1")
    ).pause(3)
  }

  val scn = scenario("HU001").exec(
    Home.home,
    Login.login,
    FindOwners.findOwners,
    ShowOwners.showOwners,
    ShowOwner.showOwner,
    ShowPet.showPet
  )

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpProtocol)
}
