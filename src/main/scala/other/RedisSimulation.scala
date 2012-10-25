package other

import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import com.excilys.ebi.gatling.jdbc.Predef._
import com.excilys.ebi.gatling.http.Headers.Names._
import com.excilys.ebi.gatling.redis.Predef.redisFeeder
import akka.util.duration._
import bootstrap._
import com.redis._
import serialization._

class RedisSimulation extends Simulation {
  val redisPool = new RedisClientPool("localhost", 6379)
  
  def apply = {
    val urlBase = "http://192.168.1.132:88"
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
		List(scn.configure.users(10).ramp(10).protocolConfig(httpConf))
	}
}