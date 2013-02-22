package advanced

import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import com.excilys.ebi.gatling.jdbc.Predef._
import com.excilys.ebi.gatling.http.Headers.Names._
import scala.concurrent.duration._

class AdvancedExampleSimulation extends Simulation {

	val httpConf = httpConfig.baseURL("http://excilys-bank-web.cloudfoundry.com").disableFollowRedirect

	setUp(
		SomeScenario.scn.users(10).ramp(10).protocolConfig(httpConf),
		SomeOtherScenario.otherScn.users(5).ramp(20).delay(30).protocolConfig(httpConf))
}