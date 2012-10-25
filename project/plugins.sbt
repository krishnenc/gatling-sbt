resolvers += "Another Typesafe Repo" at "http://repo.akka.io/releases/"

resolvers += Resolver.url("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)

resolvers += Classpaths.typesafeResolver

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.1.0")

addSbtPlugin("com.typesafe.startscript" % "xsbt-start-script-plugin" % "0.5.3")

addSbtPlugin("cc.spray" % "sbt-revolver" % "0.6.1")

resolvers += Resolver.url("sbt-plugin-releases",
  			 new URL("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns)

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.8.4")
