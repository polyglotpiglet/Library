name := "Library"

version := "1.0"

scalaVersion := "2.11.5"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

libraryDependencies ++= {
  val akkaVersion       = "2.3.10"
  val akkaStreamVersion = "1.0-RC2"
  val scalaTestVersion  = "2.2.4"
  Seq(
    "com.typesafe.akka" %% "akka-actor"                           % akkaVersion,
    "com.typesafe.akka" %% "akka-stream-experimental"             % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-core-experimental"          % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-scala-experimental"         % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental"    % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-testkit-scala-experimental" % akkaStreamVersion,
    "net.databinder.dispatch" %% "dispatch-core" % "0.11.2",
  "org.scalatest"     %% "scalatest"                            % scalaTestVersion % "test"
  )
}

Revolver.settings