import scala.tools.nsc.io.File
import scala.tools.nsc.io.Path
object IDEPathHelper {

	val gatlingConfUrl = getClass.getClassLoader.getResource("gatling.conf").getPath
	val projectRootDir = File(gatlingConfUrl).parents(0)
	
	val projectSourceDir = File(gatlingConfUrl).parents(3)
	
	val mavenSourcesDir = projectSourceDir / "src" / "main" / "scala"
	val mavenResourcesDir = projectSourceDir / "src" / "main" / "resources"
	val mavenTargetDir = projectSourceDir / "target"
	
	val mavenBinariesDir = projectRootDir

	val dataFolder = projectSourceDir / "userfiles/data"
	val requestBodiesFolder = projectSourceDir / "userfiles/request-bodies"

	val recorderOutputFolder = mavenSourcesDir
	val resultsFolder = projectSourceDir / "results"
}