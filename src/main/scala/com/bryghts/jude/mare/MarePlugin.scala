package com.bryhts.jude.mare

import java.io.File

import sbt.Keys._
import sbt._
import sbt.io.{IO, Path}
import scala.language.experimental.macros
import org.portablescala.sbtplatformdeps.PlatformDepsGroupArtifactID
import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import org.portablescala.sbtplatformdeps.JudeDepsSupport._
import scala.reflect.macros.Context
import scala.language.experimental.macros
import sbtcrossproject._
import com.softwaremill.clippy.ClippySbtPlugin
import com.softwaremill.clippy.ClippySbtPlugin.autoImport._

object MarePlugin extends AutoPlugin {

  override val trigger: PluginTrigger = allRequirements

  override val requires: Plugins = ClippySbtPlugin

  override lazy val projectSettings: Seq[Setting[_]] = Seq(
    scalaVersion := "2.11.12",
    libraryDependencies += "io.estatico" %%% "newtype" % "0.4.3-15-g418ac6d",
    libraryDependencies += "org.typelevel" %%% "simulacrum" % "1.0.0-54-g2fcd9a1",
    libraryDependencies += "com.bryghts" %%% "high-priority" % "v4C646",
    scalacOptions ++= Seq(
      "-encoding",
      "utf8",
      "-deprecation",
      "-unchecked",
      "-language:higherKinds",
      "-Yno-imports"
    ),
    resolvers += Resolver.bintrayRepo("bryghts", "jude"),
    addCompilerPlugin(
      "org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full
    ),
    addCompilerPlugin("com.bryghts.jude" %% "jude-importer" % "vA9DE7"),
    addCompilerPlugin("com.bryghts.jude" %% "jude-renamer" % "vB878B"),
    addCompilerPlugin("com.bryghts.jude" %% "jude-literals" % "vD3788"),
    clippyFatalWarnings ++= List(NonExhaustiveMatch)
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
