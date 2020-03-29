package com.bryhts.jude.mare

import java.io.File

import sbt.Keys._
import sbt._
import sbt.io.{IO, Path}
import scala.language.experimental.macros

object MarePlugin extends AutoPlugin {

  override val trigger: PluginTrigger = allRequirements

  override val requires: Plugins = empty

  override lazy val projectSettings: Seq[Setting[_]] = Seq(
    scalaVersion := "2.11.12",
    libraryDependencies += "io.estatico" %% "newtype" % "0.4.3",
    scalacOptions ++= Seq(
      "-encoding",
      "utf8",
      "-deprecation",
      "-unchecked",
      "-language:higherKinds",
      "-Yno-imports" //,
      // "-Yimports jude"
    ),
    resolvers += Resolver.bintrayRepo("bryghts", "jude"),
    addCompilerPlugin(
      "org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full
    ),
    addCompilerPlugin("com.bryghts.jude" %% "jude-importer" % "v51d59")
  )

  object autoImport {

    def judeProject(
        platforms: sbtcrossproject.Platform*
    ): sbtcrossproject.CrossProject.Builder =
      macro sbtcrossproject.JudeWrapper.judeProject_impl

  }
}
