ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "Job"
  )


val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.9"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.scalatest" %% "scalatest" % "3.2.8" % Test,
  "mysql" % "mysql-connector-java" % "8.0.26",
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "org.json4s" %% "json4s-native" % "3.6.11",
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion

)


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion,
)