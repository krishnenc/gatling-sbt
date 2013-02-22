package other

import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import com.excilys.ebi.gatling.jdbc.Predef._
import com.excilys.ebi.gatling.redis.Predef.redisFeeder
import com.excilys.ebi.gatling.http.Headers.Names._
import scala.concurrent.duration._
import bootstrap._
import assertions._
import com.redis._

class RedisSimulation extends Simulation {
  	val redisPool = new RedisClientPool("localhost", 6379)
  	val urlBase = "http://localhost:8080"
  	val httpConf = httpConfig.baseURL(urlBase)

  	val scn = scenario("Scenario name")
			.feed(redisFeeder(redisPool, "URLS"))
			.repeat(10) { 
    				exec((session: Session) => {
					  http("request_redis")
					  .get("/inject_mo?${URLS}")
					  session
					})
					.pause(7, 8)
			}
	setUp(scn.users(1).ramp(10).protocolConfig(httpConf))
}