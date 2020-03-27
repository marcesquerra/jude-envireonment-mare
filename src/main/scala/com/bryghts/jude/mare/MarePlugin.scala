package com.bryhts.jude.mare

import java.io.File

import sbt.Keys._
import sbt._
import sbt.io.{IO, Path}

object MarePlugin extends AutoPlugin {

  override val trigger: PluginTrigger = noTrigger

  override val requires: Plugins = plugins.JvmPlugin

  override lazy val projectSettings: Seq[Setting[_]] = Seq(
    scalacOptions ++= Seq(
      "-encoding",
      "utf8", // Option and arguments on same line
      "-Xfatal-warnings", // New lines for each options
      "-deprecation",
      "-unchecked",
      // "-language:implicitConversions",
      "-language:higherKinds",
      "-potato"
      // "-language:existentials",
      // "-language:postfixOps"
    )
  )

}
