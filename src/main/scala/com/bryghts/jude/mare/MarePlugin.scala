// format: off

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

  val silencerVersion = "1.4.4"

  override lazy val projectSettings: Seq[Setting[_]] = Seq(
    scalaVersion := "2.11.12",
    libraryDependencies += "io.estatico" %%% "newtype" % "0.4.3-15-g418ac6d",
    libraryDependencies += "org.typelevel" %%% "simulacrum" % "1.0.0-54-g2fcd9a1",
    libraryDependencies += "com.bryghts" %%% "high-priority" % "v4C646",
    libraryDependencies += "com.github.ghik" % "silencer-lib" % silencerVersion % Provided cross CrossVersion.full,
    scalacOptions ++= Seq(
      "-Yno-imports",
      "-deprecation",                      // Emit warning and location for usages of deprecated APIs.
      "-encoding", "utf-8",                // Specify character encoding used by source files.
      "-feature",                          // Emit warning and location for usages of features that should be imported explicitly.
      "-language:higherKinds",             // Allow higher-kinded types
      "-unchecked",                        // Enable additional warnings where generated code depends on assumptions.
      "-Xcheckinit",                       // Wrap field accessors to throw an exception on uninitialized access.
      "-Xlint:adapted-args",               // Warn if an argument list is modified to match the receiver.
      "-Yno-adapted-args",                 // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
      "-Xlint:delayedinit-select",         // Selecting member of DelayedInit.
      "-Xlint:doc-detached",               // A Scaladoc comment appears to be detached from its element.
      "-Xlint:inaccessible",               // Warn about inaccessible types in method signatures.
      "-Xlint:infer-any",                  // Warn when a type argument is inferred to be `Any`.
      "-Xlint:nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
      "-Xlint:nullary-unit",               // Warn when nullary methods return Unit.
      "-Xlint:private-shadow",             // A private field (or class parameter) shadows a superclass field.
      "-Xlint:type-parameter-shadow",      // A local type parameter shadows a type already in scope.
      "-Xlint:unsound-match",              // Pattern match may not be typesafe.
      "-Ypartial-unification",             // Enable partial unification in type constructor inference
      "-Ywarn-dead-code",                  // Warn when dead code is identified.
      "-Ywarn-inaccessible",               // Warn about inaccessible types in method signatures.
      "-Ywarn-infer-any",                  // Warn when a type argument is inferred to be `Any`.
      "-Ywarn-nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
      "-Ywarn-nullary-unit",               // Warn when nullary methods return Unit.
      "-Ywarn-value-discard",              // Warn when non-Unit expression results are unused.
      "-Xlint:stars-align",                // Pattern sequence wildcard must align with sequence component.
      "-Ywarn-unused-import"
    ),
    resolvers += Resolver.bintrayRepo("bryghts", "jude"),
    addCompilerPlugin(
      "org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full
    ),
    addCompilerPlugin("com.bryghts.jude" %% "jude-importer" % "vA9DE7"),
    addCompilerPlugin("com.bryghts.jude" %% "jude-renamer" % "vB9A4B"),
    addCompilerPlugin("com.bryghts.jude" %% "jude-literals" % "v4B9A3"),
    addCompilerPlugin(
      "com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full
    ),
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
