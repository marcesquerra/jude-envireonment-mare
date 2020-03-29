package sbtcrossproject

import scala.reflect.macros.whitebox.Context

object JudeWrapper {

  import CrossProjectMacros._

  private val all = List(
    ("scalajscrossproject", scalajscrossproject.JSPlatform),
    ("sbtcrossproject", sbtcrossproject.JVMPlatform),
    ("scalanativecrossproject", scalanativecrossproject.NativePlatform)
  )

  def judeProjectEmpty_impl(
      c: Context
  ): c.Expr[CrossProject.Builder] =
    judeProject_impl(c)()

  def judeProject_impl(
      c: Context
  )(platforms: c.Expr[Platform]*): c.Expr[CrossProject.Builder] = {
    import c.universe._

    def platform(p: (String, Platform)): c.Expr[Platform] =
      c.Expr[Platform](
        Select(
          Select(
            Ident(TermName("_root_")),
            TermName(p._1)
          ),
          TermName(p._2.toString)
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
