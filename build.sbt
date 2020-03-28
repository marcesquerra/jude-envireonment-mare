ThisBuild / organization := "com.bryghts.jude"
ThisBuild / description := "jude - mare"

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

lazy val mare = (project in file("."))
 .enablePlugins(GitVersioning)
 .settings(
    sbtPlugin := true,
    name := "mare",
    publishMavenStyle := true,
    bintrayRepository := "jude",
    bintrayOrganization := Some("bryghts"),
    git.useGitDescribe := true,
    addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject"      % "1.0.0"),
    addSbtPlugin("org.portable-scala" % "sbt-scala-native-crossproject" % "1.0.0"),
    addSbtPlugin("org.scala-js"       % "sbt-scalajs"                   % "1.0.1"),
    addSbtPlugin("org.scala-native"   % "sbt-scala-native"              % "0.4.0-M2"),
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)
 )


