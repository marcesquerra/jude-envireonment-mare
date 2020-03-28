
ThisBuild / organization := "com.bryghts.jude"
ThisBuild / description := "jude - mare"

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

lazy val mare = (project in file("."))
 .enablePlugins(GitVersioning)
 .settings(
      name := "mare"
    , sbtPlugin := true
    , publishMavenStyle := true
    , bintrayRepository := "jude"
    , bintrayOrganization := Some("bryghts")
    , git.useGitDescribe := true
    , git.formattedShaVersion := git.gitHeadCommit.value map { sha => s"v${sha.take(5)}" }
    , addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject"      % "1.0.0")
    , addSbtPlugin("org.portable-scala" % "sbt-scala-native-crossproject" % "1.0.0")
    , addSbtPlugin("org.scala-js"       % "sbt-scalajs"                   % "1.0.1")
    , addSbtPlugin("org.scala-native"   % "sbt-scala-native"              % "0.4.0-M2")
 )


