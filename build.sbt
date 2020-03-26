
lazy val mare = (project in file("."))
 .settings(
    name := "mare",
    organization := "com.bryghts.jude",
    version := "0.1-SNAPSHOT",
    sbtPlugin := true,
    addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject"      % "1.0.0"),
    addSbtPlugin("org.portable-scala" % "sbt-scala-native-crossproject" % "1.0.0"),
    addSbtPlugin("org.scala-js"       % "sbt-scalajs"                   % "1.0.1"),
    addSbtPlugin("org.scala-native"   % "sbt-scala-native"              % "0.4.0-M2")
 )
