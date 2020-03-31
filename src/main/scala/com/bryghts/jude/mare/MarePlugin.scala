package com.bryhts.jude.mare

import java.io.File

import sbt.Keys._
import sbt._
import sbt.io.{IO, Path}
import scala.language.experimental.macros
import org.portablescala.sbtplatformdeps.PlatformDepsGroupArtifactID
import org.portablescala.sbtplatformdeps.JudeDepsSupport._
import scala.reflect.macros.Context
import scala.language.experimental.macros

object MarePlugin extends AutoPlugin {

  override val trigger: PluginTrigger = allRequirements

  override val requires: Plugins = empty

  override lazy val projectSettings: Seq[Setting[_]] = Seq(
    scalaVersion := "2.11.12",
    libraryDependencies += "io.estatico" %% "newtype" % "0.4.3",
    libraryDependencies += "org.typelevel" %% "simulacrum" % "1.0.0",
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
    addCompilerPlugin("com.bryghts.jude" %% "jude-importer" % "v51d59"),
    addCompilerPlugin("com.bryghts.jude" %% "jude-equals" % "v461d4"),
    addCompilerPlugin("com.bryghts.jude" %% "jude-literals" % "v1cafa")
  )

  object autoImport {

    def judeProject(
        platforms: sbtcrossproject.Platform*
    ): sbtcrossproject.CrossProject.Builder =
      macro sbtcrossproject.JudeWrapper.judeProject_impl

    def judeProject: sbtcrossproject.CrossProject.Builder =
      macro sbtcrossproject.JudeWrapper.judeProjectEmpty_impl

    implicit def StringExtensionToBuildDeps(
        groupID: String
    ): DepsGroupID = {
      require(groupID.nonEmpty, "Group ID may not be empty")
      new DepsGroupID(groupID)
    }

    implicit class PlatformDepsGroupArtifactIDExtension(
        val gna: PlatformDepsGroupArtifactID
    ) extends AnyVal {
      def /(revision: String): ModuleID =
        gna % revision
    }

  }
}
