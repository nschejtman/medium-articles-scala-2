import sbt.Keys.scalacOptions

lazy val partialUnification =
  (project in file("partial-unification"))
    .settings(
      name := "partial-unification",
      scalaVersion := "2.12.13",
      scalacOptions += "-Ypartial-unification"
    )
