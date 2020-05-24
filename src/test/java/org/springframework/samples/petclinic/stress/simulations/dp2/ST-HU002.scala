package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ST_HU002 extends Simulation {

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
    ).pause(8)
      .exec(
        http("Logged")
          .post("/login")
          .headers(headers_2)
          .formParam("username", "vet1")
          .formParam("password", "v3t")
          .formParam("_csrf", "${loginToken}")
      )
      .pause(4)
  }

  object FindOwners {
    val findOwners = exec(
      http("FindOwners")
        .get("/owners/find")
    ).pause(8)
  }

  object ShowOwners {
    val showOwners = exec(
      http("ShowOwners")
        .get("/owners?lastName=")
    ).pause(6)
  }

  object ShowOwner {
    val showOwner = exec(
      http("ShowOwner")
        .get("/owners/6")
    ).pause(5)
  }

  object ShowPet {
    val showPet = exec(
      http("ShowPet")
        .get("/owners/6/pets/8")
    ).pause(7)
  }

  object CreateIntervention {
    val createInt = exec(
      http("CreateIntervention")
        .get("/owners/6/pets/8/visits/3/interventions/new")
        .check(css("input[name=_csrf]", "value").saveAs("positiveToken"))
    ).pause(19)
      .exec(
        http("SubmitIntervention")
          .post("/owners/6/pets/8/visits/3/interventions/new")
          .headers(headers_2)
          .formParam("name", "Test Intervention")
          .formParam("vet", "3")
          .formParam("requiredProducts", "3")
          .formParam("_requiredProducts", "1")
          .formParam("_csrf", "${positiveToken}")
      )
      .pause(11)
  }

  object CreateWrongIntervention {
    val createWrongInt = exec(
      http("CreateWrongIntervention")
        .get("/owners/6/pets/8/visits/2/interventions/new")
        .check(css("input[name=_csrf]", "value").saveAs("negativeToken"))
    ).pause(13)
      .exec(
        http("SubmitWrongIntervention")
          .post("/owners/6/pets/8/visits/2/interventions/new")
          .headers(headers_2)
          .formParam("name", "")
          .formParam("_requiredProducts", "1")
          .formParam("_csrf", "${negativeToken}")
      )
      .pause(20)
  }

  val newInterventionPositive = scenario("HU002_P").exec(
    Home.home,
    Login.login,
    FindOwners.findOwners,
    ShowOwners.showOwners,
    ShowOwner.showOwner,
    ShowPet.showPet,
    CreateIntervention.createInt
  )

  val newInterventionNegative = scenario("HU002_N").exec(
    Home.home,
    Login.login,
    FindOwners.findOwners,
    ShowOwners.showOwners,
    ShowOwner.showOwner,
    ShowPet.showPet,
    CreateWrongIntervention.createWrongInt
  )

  setUp(
    newInterventionPositive.inject(atOnceUsers(1)),
    newInterventionNegative.inject(atOnceUsers(1))
  ).protocols(httpProtocol)
}