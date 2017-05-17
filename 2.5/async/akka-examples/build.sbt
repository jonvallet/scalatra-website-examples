organization := "com.example"
name := "Akka Examples"
version := "0.1.0-SNAPSHOT"
scalaVersion := "2.11.8"

val ScalatraVersion = "2.5.+"

libraryDependencies ++= Seq(
  "com.typesafe.akka"       %% "akka-actor"        % "2.4.12",
  "net.databinder.dispatch" %% "dispatch-core"     % "0.11.3",
  "org.scalatra"            %% "scalatra"          % ScalatraVersion,
  "org.scalatra"            %% "scalatra-scalate"  % ScalatraVersion,
  "org.scalatra"            %% "scalatra-specs2"   % ScalatraVersion    % "test",
  "org.eclipse.jetty"       %  "jetty-webapp"      % "9.2.19.v20160908" % "provided",
  "javax.servlet"           %  "javax.servlet-api" % "3.1.0"            % "provided"
)

enablePlugins(JettyPlugin)