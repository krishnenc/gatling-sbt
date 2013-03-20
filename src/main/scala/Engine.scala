import java.lang.System.currentTimeMillis
import java.util.{ Map => JMap }
import scala.collection.JavaConversions._

import com.excilys.ebi.gatling.app.Gatling
import com.excilys.ebi.gatling.app.CommandLineConstants._
import com.excilys.ebi.gatling.charts.report.ReportsGenerator
import com.excilys.ebi.gatling.core.config.{ GatlingFiles, GatlingPropertiesBuilder }
import com.excilys.ebi.gatling.core.config.GatlingConfiguration
import com.excilys.ebi.gatling.core.config.GatlingConfiguration.configuration
import com.excilys.ebi.gatling.core.runner.{ Runner, Selection }
import com.excilys.ebi.gatling.core.scenario.configuration.Simulation
import com.excilys.ebi.gatling.core.util.FileHelper.formatToFilename

import grizzled.slf4j.Logging
import scopt.OptionParser


object Engine extends Logging {

	/**
	 * Entry point of Application
	 *
	 * @param args Arguments of the main method
	 */
	def main(args: Array[String]) {

		val props = new GatlingPropertiesBuilder
		
		props.dataDirectory(IDEPathHelper.dataFolder.toString)
		props.resultsDirectory(IDEPathHelper.resultsFolder.toString)
		props.requestBodiesDirectory(IDEPathHelper.requestBodiesFolder.toString)
		props.sourcesDirectory(IDEPathHelper.mavenSourcesDir.toString)
		
		val cliOptsParser = new OptionParser("gatling") {
			opt(CLI_NO_REPORTS, CLI_NO_REPORTS_ALIAS, "Runs simulation but does not generate reports", { props.noReports })
			opt(CLI_REPORTS_ONLY, CLI_REPORTS_ONLY_ALIAS, "<directoryName>", "Generates the reports for the simulation in <directoryName>", { v: String => props.reportsOnly(v) })
			opt(CLI_DATA_FOLDER, CLI_DATA_FOLDER_ALIAS, "<directoryPath>", "Uses <directoryPath> as the absolute path of the directory where feeders are stored", { v: String => props.dataDirectory(v) })
			opt(CLI_RESULTS_FOLDER, CLI_RESULTS_FOLDER_ALIAS, "<directoryPath>", "Uses <directoryPath> as the absolute path of the directory where results are stored", { v: String => props.resultsDirectory(v) })
			opt(CLI_REQUEST_BODIES_FOLDER, CLI_REQUEST_BODIES_FOLDER_ALIAS, "<directoryPath>", "Uses <directoryPath> as the absolute path of the directory where request bodies are stored", { v: String => props.requestBodiesDirectory(v) })
			opt(CLI_SIMULATIONS_FOLDER, CLI_SIMULATIONS_FOLDER_ALIAS, "<directoryPath>", "Uses <directoryPath> to discover simulations that could be run", { v: String => props.sourcesDirectory(v) })
			opt(CLI_SIMULATIONS_BINARIES_FOLDER, CLI_SIMULATIONS_BINARIES_FOLDER_ALIAS, "<directoryPath>", "Uses <directoryPath> to discover already compiled simulations", { v: String => props.binariesDirectory(v) })
			opt(CLI_SIMULATION, CLI_SIMULATION_ALIAS, "<className>", "Runs <className> simulation", { v: String => props.clazz(v) })
			opt(CLI_OUTPUT_DIRECTORY_BASE_NAME, CLI_OUTPUT_DIRECTORY_BASE_NAME_ALIAS, "<name>", "Use <name> for the base name of the output directory", { v: String => props.outputDirectoryBaseName(v) })
		}
		
		// if arguments are incorrect, usage message is displayed
		if (cliOptsParser.parse(args)) {
			fromMap(props.build)
		}
	}

	def fromMap(props: JMap[String, Any]) {
		props foreach println
		GatlingConfiguration.setUp(props)
		new Gatling().start
	}
}