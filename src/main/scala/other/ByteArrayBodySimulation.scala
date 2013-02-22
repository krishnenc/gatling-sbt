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


class ByteArrayBodySimulation extends Simulation {
  val redisPool = new RedisClientPool("localhost", 6379)

  val urlBase = "http://localhost:8080"
  val httpConf = httpConfig.baseURL(urlBase)

  val header = Map(
      "Content-Type" -> "application/octet-stream")

  val scn = scenario("Scenario name")
			.repeat(10) { 
    				exec((session: Session) => {
					  http("Protocol Buffer - hola").post("/hola")
					  .headers(header)
					  .byteArrayBody(getByteArrayBody(session))
					  session
					})
					.pause(7, 8)
			}
  
  setUp(scn.users(1).ramp(10).protocolConfig(httpConf))

  val getByteArrayBody = (session: Session) => {
    import Parse.Implicits.parseByteArray
    //LOAD your binary data onto this list beforehand
    redisPool.withClient(client =>
      client.lpop("TEST")).getOrElse {
      println("There are not enough records in the redis list: '" +
        "TEST" + "'.\nPlease add records or use another feeder strategy.\nStopping simulation here...")
      redisPool.close
      sys.exit
    }
  }
}

