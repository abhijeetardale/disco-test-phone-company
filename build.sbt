import scoverage.ScoverageKeys

lazy val phoneCompany = (project in file(".")).settings(
  Seq(
    name := "disco-test-phone-company",
    version := "1.0",
    scalaVersion := "2.12.3",
    ScoverageKeys.coverageExcludedFiles := ".*com.phone.Main*;"
  )
)

mainClass in (Compile, run) := Some("com.phone.Main")

libraryDependencies ++= Seq(
  "com.google.inject"   % "guice"                    % "4.2.2",
  "com.typesafe.play"  %% "play-json-joda"           % "2.7.4",
  "org.scalatest"      %% "scalatest"                % "3.0.0"  % "test",
  "org.scalacheck"     %% "scalacheck"               % "1.14.0" % "test"
)
