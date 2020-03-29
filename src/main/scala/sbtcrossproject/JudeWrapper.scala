package sbtcrossproject

import scala.reflect.macros.whitebox.Context

object JudeWrapper {

  import CrossProjectMacros._

  private val all = List("JSPlatform", "JVMPlatform", "NativePlatform")

  def judeProject_impl(
      c: Context
  )(platforms: c.Expr[Platform]*): c.Expr[CrossProject.Builder] = {
    import c.universe._

    def platform(p: String): c.Expr[Platform] =
      c.Expr[Platform](
        Select(
          Select(
            Ident(TermName("_root_")),
            TermName("sbtcrossproject")
          ),
          TermName(p)
        )
      )

    platforms.toList match {
      case Nil =>
        val allExprs =
          all.map(platform(_))

        crossProject_impl(c)(allExprs)

      case other =>
        crossProject_impl(c)(other)
    }

  }

}
