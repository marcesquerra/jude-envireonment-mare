package org.portablescala.sbtplatformdeps

import scala.language.experimental.macros
import scala.reflect.macros.whitebox.Context

object JudeDepsSupport {

  def buildPlatformDepsGroupID(groupID: String): PlatformDepsGroupID =
    new PlatformDepsGroupID(groupID)

  final class DepsGroupID(val groupID: String) {
    def /(artifactID: String): PlatformDepsGroupArtifactID =
      macro impl
  }

  def impl(c: Context { type PrefixType = DepsGroupID })(
      artifactID: c.Expr[String]
  ): c.Expr[PlatformDepsGroupArtifactID] = {
    import c.universe._
    reify {
      PlatformDepsGroupID.withCross(
        buildPlatformDepsGroupID(c.prefix.splice.groupID),
        artifactID.splice,
        PlatformDepsGroupID.platformDepsCrossVersion.value
      )
    }
  }
}
