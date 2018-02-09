name := "study"

version := "1.0"

scalaVersion := "2.11.8"

lazy val doobieVersion = "0.3.1-M1"

lazy val doobieCore = "org.tpolecat" %% "doobie-core-cats" % doobieVersion
lazy val doobiePostgresql = "org.tpolecat" %% "doobie-postgres-cats" % doobieVersion
lazy val scalaCheckVersion = "1.13.4"
lazy val scalaTestVersion = "3.0.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.9",
  "com.typesafe.akka" %% "akka-http-jackson-experimental" % "2.4.9",
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.9",
  "com.typesafe.akka" %% "akka-stream" % "2.4.9",
  "org.typelevel" %% "cats" % "0.8.1",
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.1.0",
  "io.getquill" %% "quill-cassandra" % "2.3.2",
  doobieCore,
  doobiePostgresql,
  "org.scalacheck" %% "scalacheck" % scalaCheckVersion % Test,
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
  "com.dimafeng" %% "testcontainers-scala" % "0.13.0" % Test,
  "org.scalamock" %% "scalamock" % "4.0.0" % Test
)

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots"),
  "bintray-backline-open-source-releases" at "https://dl.bintray.com/backline/open-source",
  "evotor-releases" at "http://nexus.market.local/repository/maven-releases/"
)

libraryDependencies ++= Seq(
  compilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
)