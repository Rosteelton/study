name := "study"

version := "1.0"

scalaVersion := "2.11.8"

lazy val doobieVersion = "0.3.1-M1"

lazy val doobieCore = "org.tpolecat" %% "doobie-core-cats" % doobieVersion
lazy val doobiePostgresql = "org.tpolecat" %% "doobie-postgres-cats" % doobieVersion

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.9",
  "com.typesafe.akka" %% "akka-http-jackson-experimental" % "2.4.9",
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.9",
  "com.typesafe.akka" %% "akka-stream" % "2.4.9",
  doobieCore,
  doobiePostgresql
)

libraryDependencies ++= Seq(
  compilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
)