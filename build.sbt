import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.5",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "IFS",

    libraryDependencies ++= Seq(
      scalaTest,
      "org.scala-lang.modules" %% "scala-swing" % "2.0.0-M2"
    ),

    fork in run := false
  )
