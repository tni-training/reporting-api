ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "Job"
  )

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.scalatest" %% "scalatest" % "3.2.8" % Test,
  "mysql" % "mysql-connector-java" % "8.0.26"

)